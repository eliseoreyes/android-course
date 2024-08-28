package edu.fvtc.blackjackandroidui.models;

import android.util.Log;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Card implements Serializable {

    private CardSuit suit;
    private CardRank rank;
    private int value;
    private String cardImg;

    public Card(CardSuit suit, CardRank rank){
            this.suit = suit;
            this.rank = rank;
            this.value = getCardValue(rank);
            this.cardImg = String.format("%s_of_%s", rank.toString().toLowerCase(), suit);
    }



    public CardSuit getSuit() {
        return suit;
    }

    public void setSuit(CardSuit suit) {
        this.suit = suit;
    }

    public CardRank getRank() {
        return rank;
    }

    public void setRank(CardRank rank) {
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

    private int getCardValue(CardRank rank) {
        switch (rank){
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
            case FIVE:
                return 5;
            case SIX:
                return 6;
            case SEVEN:
                return 7;
            case EIGHT:
                return 8;
            case NINE:
                return 9;
            case TEN:
            case JACK:
            case QUEEN:
            case KING:
                return 10;
            case ACE:
                return 11;
            default:
                return -1;
        }
    }

    public void printCard(){
        Log.v("Error", cardImg);
    }
}
