package com.sparta.greeypeople.like.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.greeypeople.menu.entity.Menu;
import com.sparta.greeypeople.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.Objects;

import static com.sparta.greeypeople.like.entity.QMenuLikes.menuLikes;

@RequiredArgsConstructor
public class MenuLikesRepositoryQueryImpl implements MenuLikesRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Menu> findLikedMenus(User user, Pageable pageable) {
        // 사용자가 작성한 메뉴 좋아요를 조회합니다.
        var query = query(menuLikes.menu, user)
                .orderBy(menuLikes.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // 총 갯수를 조회합니다.
        var threads = query.fetch();
        long totalSize = query(Wildcard.count, user).fetch().get(0);

        // 페이지를 생성합니다.
        return PageableExecutionUtils.getPage(threads, pageable, () -> totalSize);
    }

    private <T> JPAQuery<T> query(Expression<T> expr, User user) {
        return jpaQueryFactory.select(expr)
                .from(menuLikes)
                .join(menuLikes.menu)
                .where(
                        userEq(user)
                );
    }

    private BooleanExpression userEq(User user) {
        return Objects.nonNull(user) ? menuLikes.user.eq(user) : null;
    }
}
