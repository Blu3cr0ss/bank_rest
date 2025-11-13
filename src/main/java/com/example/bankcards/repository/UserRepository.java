package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u.cards FROM User u WHERE u.id = :id")
    List<Card> getCardsById(long id);

    @Query(value = "SELECT * FROM User LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<User> findAll(int limit, int offset);

    @Query(value = "SELECT u.cards FROM User u WHERE u.id = :id LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Card> getCardsById(long id, int limit, int offset);

    @Query("SELECT u.password FROM User u WHERE u.username = :username")
    String getPasswordByUsername(String username);

    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    long getIdByUsername(String username);

    @Query("SELECT u.role FROM User u WHERE u.id = :id")
    Role getRoleById(long id);
}
