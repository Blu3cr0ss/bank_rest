package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Card c SET c.status = :status WHERE c.number = :number")
    void setStatusByNumber(Card.Status status, long number);

    @Query(value = "SELECT * FROM cards LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Card> findAll(int limit, int offset);

    @Query(value = "SELECT * FROM cards WHERE balance >= :minBalance LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Card> findWithMinBalance(int limit, int offset, int minBalance);

    @Query(value = "SELECT * FROM cards WHERE balance >= :minBalance", nativeQuery = true)
    List<Card> findWithMinBalance( int minBalance);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Card c SET c.owner = :owner WHERE c.number = :number")
    void setOwnerByNumber(long owner, long number);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Card c SET c.balance = c.balance + :amount WHERE c.number = :number")
    void increaseBalanceByNumber(int amount, long number);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Card c SET c.balance = c.balance - :amount WHERE c.number = :number")
    void decreaseBalanceByNumber(int amount, long number);

    @Query("SELECT c.owner.id FROM Card c WHERE c.number = :number")
    long getOwnerIdByNumber(long number);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Card c SET c.blockRequested = :blockRequested WHERE c.number = :number")
    void setBlockRequestedByNumber(boolean blockRequested, long number);

    int getBalanceByNumber(long number);
}
