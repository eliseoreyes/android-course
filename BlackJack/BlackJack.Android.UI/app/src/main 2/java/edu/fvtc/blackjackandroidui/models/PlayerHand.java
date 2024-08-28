package edu.fvtc.blackjackandroidui.models;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class PlayerHand implements Serializable {

    private UUID id;
    private UUID userId;
    private UUID cardId;
    private User user;
    private Card card;
    private List<Card> cards;

    public PlayerHand(){}
    public PlayerHand(UUID id, UUID userId, UUID cardId, User user, Card card, List<Card> cards) {
        this.id = id;
        this.userId = userId;
        this.cardId = cardId;
        this.user = user;
        this.card = card;
        this.cards = cards;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getCardId() {
        return cardId;
    }

    public void setCardId(UUID cardId) {
        this.cardId = cardId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
