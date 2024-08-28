package edu.fvtc.blackjackandroidui.models;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Game implements Serializable {

    private UUID id;
    private boolean status;
    private UUID userId;
    private User user;
    private List<Deck> decks;

    public Game(UUID id, boolean status, UUID userId, User user, List<Deck> decks) {
        this.id = id;
        this.status = status;
        this.userId = userId;
        this.user = user;
        this.decks = decks;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", status=" + status +
                ", userId=" + userId +
                ", user=" + user +
                ", decks=" + decks +
                '}';
    }
}
