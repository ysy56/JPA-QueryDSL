package com.sparta.greeypeople.store.repository;

import com.sparta.greeypeople.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
