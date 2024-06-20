package com.sparta.greeypeople.menu.repository;

import com.sparta.greeypeople.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
