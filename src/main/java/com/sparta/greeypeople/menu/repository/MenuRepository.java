package com.sparta.greeypeople.menu.repository;

import com.sparta.greeypeople.menu.dto.response.MenuResponseDto;
import com.sparta.greeypeople.menu.entity.Menu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByStoreId(Long storeId);

    @Query("SELECT new com.sparta.greeypeople.menu.dto.response.MenuResponseDto(m.store.id, m.menuName, m.price) " +
        "FROM Menu m " +
        "WHERE m.store.id IN :storeIds " +
        "ORDER BY m.createdAt DESC")
    List<MenuResponseDto> findMenusFromFollowedStoresOrderByCreatedAtDesc(@Param("storeIds") List<Long> storeIds);
}
