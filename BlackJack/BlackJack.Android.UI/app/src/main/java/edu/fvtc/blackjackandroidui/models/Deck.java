package edu.fvtc.blackjackandroidui.models;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Deck implements Serializable {

    private static final int TOTAL_CARDS = 52;
    private int remainingCards;
    private int topCard;
    private Card[] cards;

    public Deck(){
        int card = 0;
        cards = new Card[TOTAL_CARDS];
        for (CardSuit suit : CardSuit.values()){
            for (CardRank rank : CardRank.values()){
                cards[card] = new Card(suit, rank);
                card++;
            }
        }

        remainingCards = TOTAL_CARDS;
        topCard = 0;
    }

    public void swap(Card card1, Card card2){

        CardSuit tmpSuit;
        CardRank tmpRank;
        int tmpVal;
        String tmpImage;

        tmpSuit = card1.getSuit();
        tmpRank = card1.getRank();
        tmpVal = card1.getValue();
        tmpImage = card1.getCardImg();

        card1.setSuit(card2.getSuit());
        card1.setRank(card2.getRank());
        card1.setValue(card2.getValue());
        card1.setCardImg(card2.getCardImg());

        card2.setSuit(tmpSuit);
        card2.setRank(tmpRank);
        card2.setValue(tmpVal);
        card2.setCardImg(tmpImage);

    }

    public void shuffle(){
        int i;

        Random randNum = new Random();

        for (Card card : cards){
            i = randNum.nextInt(52);
            swap(card, cards[i]);
        }

        remainingCards = TOTAL_CARDS;
        topCard = 0;
    }

    public void printDeck(){
        for (Card card : cards){
            card.printCard();
        }
    }
}
