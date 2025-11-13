package com.example.bankcards.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @SequenceGenerator(name = "cards_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cards_seq")
    long number;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    User owner;
    Instant expiration = Instant.now();
    public enum Status {
        ACTIVE, BLOCKED, EXPIRED
    }

    boolean blockRequested;
    Status status = Status.ACTIVE;
    int balance = 0;

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }

    public boolean isBlockRequested() {
        return blockRequested;
    }

    public void setBlockRequested(boolean blockRequested) {
        this.blockRequested = blockRequested;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
