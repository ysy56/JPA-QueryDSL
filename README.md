# 🪄 욕심쟁이
### *"욕심 내서 일한 자 욕심 내서 먹어라!"*
####
### 🍔 🍖 🍱 🍦 🍨 🍕 🍣 🥟 🥗 🌮 🌭 🥐
> __음식 주문 및 리뷰 작성이 가능한 음식 배달 어플리케이션__

<br>

## 📆 프로젝트 기간
### 2024.06.19 ~ 2024.06.25
<br>


## 🛠️ 개발 환경
*  __Tech__ : <img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white">  <img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white"/>
*  __IDE__ : IntelliJ
*  __JDK__ : 17
*  __DB__ : MySQL
<br>
<br>



## 🎩 팀원
![img.png](img.png)
* #### 김기석 [@kiseokkm](https://github.com/kiseokkm)
* #### 최영주 [@ysy56](https://github.com/ysy56)
* #### 박시현 [@sihyun615](https://github.com/sihyun615)
* #### 정연주 [@jdusw](https://github.com/jdusw)

<br>

## 🥁 프로젝트 내용
### 🔎 ERD
> ![img_2.png](img_2.png)


<details>
<summary style="font-size: 1.5em; font-weight: bold;"> ⚙ 기능 명세서 </summary>
<br>
  <details style="margin-left: 20px;">
  <summary style="font-size: 1.4em; font-weight: bold;"> 1. 일반 사용자 기능 </summary>
  
  * __사용자 인증 기능__
  
    ✔ 회원가입 → 회원권한 부여 (사용자/관리자)
  
    ✔ 로그인
  
    ✔ 회원탈퇴
  
    ✔ 로그아웃
  
  
  
  * __프로필 관리 기능__
  
    ✔ 프로필 조회
  
    ✔ 프로필 수정
  
    ✔ 비밀번호 수정 → 최근 사용한 세 개의 비밀번호와 달라야 수정 가능
  
  
  
  * __가게 및 메뉴 조회 기능__
  
    ✔ 가게 조회
  
    ✔ 가게 메뉴 조회
  
  
  
  * __주문 CRUD 기능__
  
    ✔ 주문 작성
  
    ✔ 주문 조회 (생성일자 기준으로 최신순) ➕ 페이지네이션
  
    ✔ 주문 수정
  
    ✔ 주문 삭제
  
  
  
  
  * __리뷰 CRUD 기능__
  
    ✔ 리뷰 작성
  
    ✔ 리뷰 조회
  
    ✔ 리뷰 수정
  
    ✔ 리뷰 삭제
  
  
  
  * __좋아요 기능__
  
    ✔ 메뉴 좋아요 등록
  
    ✔ 메뉴 좋아요 취소
  
    ✔ 리뷰 좋아요 등록
  
    ✔ 리뷰 좋아요 취소
  
  
  
  * __팔로우 기능__
  
    ✔ 가게 팔로우 하기 (가게 찜하기)
  
    ✔ 가게 팔로우 끊기
  
    ✔ 팔로우한 가게들이 등록한 메뉴 조회 (최신등록순)
  
  
  </details>
<br>
  <details style="margin-left: 20px;">
  <summary style="font-size: 1.4em; font-weight: bold;"> 2. 관리자 기능 (백오피스) </summary>

* __회원 관리 기능__

  ✔ 전체 회원 조회

  ✔ 특정 회원 프로필 수정

  ✔ 특정 회원 삭제

  ✔ 특정 회원 권한을 관리자로 변경

  ✔ 특정 회원 차단



* __가게 관리 기능__

  ✔ 가게 등록

  ✔ 가게 정보 수정

  ✔ 가게 삭제

  ✔ 가게 팔로우한 사용자 조회



* __메뉴 관리 기능__

  ✔ 가게 메뉴 등록

  ✔ 가게 메뉴 수정

  ✔ 가게 메뉴 삭제



  </details>
</details>
<br>
<br>



## 🧩 Github Rules
### Branch naming
> - __작업타입/#이슈 기능__
> - ex) feat/#issueNum user

<br>

### Issue template
- __[Task] 이슈 제목__
> #### ISSUE
> - Group:  `user`,`board`,`like`,`commnent`
> - Type: `feat`,`update`
>
> #### 할 일
> 1. [ ] 로그인 기능 구현
>
> #### 소요 시간
> #### `3h`


- __[Bug] 이슈 제목__
> **👾 Bug**
> 
> 어떤 버그가 발생하였는지 작성해주세요.
> 
> **Bug Situation**
> 
>버그가 발생한 과정에 대해 작성해주세요:
> 1. '...'
> 2. '...'
> 3. '...'
> 4. 에러발생
>
> **Expected behavior**
> 
> 버그를 발생시킨 예상되는 원인을 작성해주세요.
>
> **Screenshots**
>
> 버그상황에 대한 영상 또는 스크린샷을 작성해주세요.
>
> **Version**
> 
> 버그가 발생한 버전 상태 등을 작성해주세요.
> - Version [__JDK__ : 17]
> 
> **Additional context**
> 
> 추가적인 내용이 있다면 작성해주세요.
> 
<br>

### Commit rules
> - __작업타입 : 작업 내용__
> - ex) 🎉 add : 유저 전체 조회 기능 추가

|      작업 타입       | 작업 내용  |                                 
|:----------------:|:------:|
|     ✨ update     |  해당 파일에 새로운 기능이 생김  |
|      🎉 add      | 없던 파일을 생성함, 초기 세팅 |
|     🐛 bugfix     | 버그 수정 |
|     ♻️ refactor     | 코드 리팩토링 |
|       🩹 fix       | 코드 수정 |
|      🚚 move       | 파일 옮김/정리  |
|   🔥 del    | 기능/파일을 삭제 |
|      🍻 test      | 테스트 코드를 작성 |
|  💄 style   | css |
|🙈 gitfix| gitignore 수정 |
|    🔨script     | package.json 변경(npm 설치 등) |
<br>

### PR title naming
> - __[날짜] 브랜치명 >> 작업내용__
> - ex) [2024.06.20] feat/#10 admin-user >> 백오피스 유저 기능 추가


### PR template
> #### #️⃣ 연관된 이슈
> 
> #이슈번호
> 
> Close #이슈번호
>
> #### 📑 작업 내용
> 
> -
>
> #### 💭 리뷰 요구사항(선택)
> ####



### PR & merge rules
1. PR 후 슬랙에 남기기
2. PR 본 사람은 코드 확인 후 리뷰 남기기
3. 리뷰 반영 후 코드 완성 시 merge

<br>
<br>


## 🍽️ 프로젝트 후기
