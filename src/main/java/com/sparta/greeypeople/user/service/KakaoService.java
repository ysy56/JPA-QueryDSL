package com.sparta.greeypeople.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.greeypeople.user.dto.KakaoUserInfoDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Slf4j(topic = "KAKAO Login")
@Service
@RequiredArgsConstructor
public class KakaoService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	// 스프링 부트에서는 RestTemplate 바로 빈으로 등록하는게 아니라 RestTemplate빌더를 통해 생성할 수 있게 유도함
	// 생성자에서 만드는게 아니라 따로 빈 수동으로 등록해서 관리
	private final RestTemplate restTemplate;

	private final JwtUtil jwtUtil;

	public String kakaoLogin(String code) throws JsonProcessingException {
		// 1. "인가 코드"로 "액세스 토큰" 요청
		String accessToken = getToken(code);

		// 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
		KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

		// 3. 필요 시에 회원가입
		User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);

		// 4. JWT 반환
		String createToken = jwtUtil.createToken(kakaoUser.getUsername(), kakaoUser.getRole());

		return createToken;
	}


	// 1. "인가 코드"로 "액세스 토큰" 요청
	private String getToken(String code) throws JsonProcessingException {
		log.info("인가코드 : " + code);

		// 요청 URL 만들기
		URI uri = UriComponentsBuilder
			.fromUriString("https://kauth.kakao.com")
			.path("/oauth/token")
			.encode()
			.build()
			.toUri();

		// HTTP Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HTTP Body 생성
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", "ef438dbe0defc1349a3642769132358b");
		body.add("redirect_uri", "http://localhost:8080/api/user/kakao/callback");
		body.add("code", code);

		RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
			.post(uri)
			.headers(headers)
			.body(body);

		// HTTP 요청 보내기
		ResponseEntity<String> response = restTemplate.exchange(
			requestEntity,
			String.class
		);  //반환되는 String이 토큰 형태로 되어 있음

		// HTTP 응답 (JSON) -> 액세스 토큰 파싱
		JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
		return jsonNode.get("access_token").asText();
	}


	// 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
	private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
		log.info("accessToken : " + accessToken);

		// 요청 URL 만들기
		URI uri = UriComponentsBuilder
			.fromUriString("https://kapi.kakao.com")
			.path("/v2/user/me")
			.encode()
			.build()
			.toUri();

		// HTTP Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
			.post(uri)
			.headers(headers)
			.body(new LinkedMultiValueMap<>());  //body에 따로 보내줄 필요 없어서 이렇게!

		// HTTP 요청 보내기
		ResponseEntity<String> response = restTemplate.exchange(
			requestEntity,
			String.class
		);

		JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
		Long id = jsonNode.get("id").asLong();
		String nickname = jsonNode.get("properties")  //properties에서 nickname 값 가져옴
			.get("nickname").asText();
		String email = jsonNode.get("kakao_account")  //kakao_account에서 email 값 가져옴
			.get("email").asText();

		log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
		return new KakaoUserInfoDto(id, nickname, email);
	}

	// 3. 필요 시에 회원가입
	private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
		// DB 에 중복된 Kakao Id 가 있는지 확인
		Long kakaoId = kakaoUserInfo.getId();
		User kakaoUser = userRepository.findByKakaoId(kakaoId).orElse(null);

		if (kakaoUser == null) {  //DB에 해당 카카오 아이디 없다면 회원가입 진행
			// 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
			String kakaoEmail = kakaoUserInfo.getEmail();
			User sameEmailUser = userRepository.findByEmail(kakaoEmail).orElse(null);
			if (sameEmailUser != null) {  //DB에 이미 존재하는 이메일을 가진 회원이 있다면
				kakaoUser = sameEmailUser;  //같은 회원이라고 덮어씌우기
				kakaoUser = kakaoUser.kakaoIdUpdate(kakaoId);  //기존 회원정보에 카카오 Id 추가
			} else {  // 신규 회원가입
				// password: random UUID
				String password = UUID.randomUUID().toString();  //password는 UUID를 사용해서 랜덤으로 생성
				String encodedPassword = passwordEncoder.encode(password);  //암호화

				// email: kakao email
				String email = kakaoUserInfo.getEmail();

				kakaoUser = new User(kakaoUserInfo.getNickname(), encodedPassword, email, UserRoleEnum.USER, kakaoId);
			}

			userRepository.save(kakaoUser);
		}

		return kakaoUser;
	}
}