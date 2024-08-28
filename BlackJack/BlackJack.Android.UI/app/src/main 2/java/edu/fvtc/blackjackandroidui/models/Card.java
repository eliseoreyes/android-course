package edu.fvtc.blackjackandroidui.models;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Card implements Serializable {

    private UUID id;
    private String suit;
    private String rank;
    private int value;
    private String cardImg;
    private UUID deckId;
    private List<Deck> decks;
    private List<PlayerHand> playerHands;

    public Card(){}

    public Card(UUID id, String suit, String rank, int value, String cardImg, UUID deckId, List<Deck> decks, List<PlayerHand> playerHands) {
        this.id = id;
        this.suit = suit;
        this.rank = rank;
        this.value = value;
        this.cardImg = cardImg;
        this.deckId = deckId;
        this.decks = decks;
        this.playerHands = playerHands;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCardImg() {
        return cardImg;
    }

    public void setCardImg(String cardImg) {
        this.cardImg = cardImg;
    }

    public UUID getDeckId() {
        return deckId;
    }

    public void setDeckId(UUID deckId) {
        this.deckId = deckId;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public List<PlayerHand> getPlayerHands() {
        return playerHands;
    }

    public void setPlayerHands(List<PlayerHand> playerHands) {
        this.playerHands = playerHands;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", suit='" + suit + '\'' +
                ", rank='" + rank + '\'' +
                ", value=" + value +
                ", cardImg='" + cardImg + '\'' +
                ", deckId=" + deckId +
                ", decks=" + decks +
                ", playerHands=" + playerHands +
                '}';
    }
}
