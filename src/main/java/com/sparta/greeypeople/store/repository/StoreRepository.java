package com.sparta.greeypeople.store.repository;

import com.sparta.greeypeople.menu.entity.Menu;
import com.sparta.greeypeople.store.entity.Store;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Menu> findMenusByIdOrderByCreatedAtDesc(Long storeId);
}