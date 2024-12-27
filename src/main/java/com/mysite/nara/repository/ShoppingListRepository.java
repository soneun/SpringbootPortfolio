package com.mysite.nara.repository;


import com.mysite.nara.entity.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;


public interface ShoppingListRepository extends JpaRepository<ShoppingList,Long> {

    Optional<ShoppingList> findByListId(String listId);

    List<ShoppingList> findByNameContainingAndDateBetweenAndUserId(String keyword, Date start, Date end, Long userId);

    List<ShoppingList> findByUserIdAndDateBetween(Long userId, Date start, Date end);

}
