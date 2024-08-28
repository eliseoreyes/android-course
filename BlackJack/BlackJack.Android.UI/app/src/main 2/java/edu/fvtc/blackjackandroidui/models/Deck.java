package edu.fvtc.blackjackandroidui.models;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Deck implements Serializable {

    private UUID id;
    private UUID gameId;
    private Game game;
    //private UUID cardId;
    private List<Card> cards;

    public Deck(){}
    public Deck(UUID id, UUID gameId, Game game, List<Card> cards) {
        this.id = id;
        this.gameId = gameId;
        this.game = game;
        this.cards = cards;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "id=" + id +
                ", gameId=" + gameId +
                ", game=" + game +
                ", cards=" + cards +
                '}';
    }
}
