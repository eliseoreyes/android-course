package edu.fvtc.blackjackandroidui.models;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class Player implements Serializable {

    private int cardPos;
    private int numCards;
    private int numAces;
    private int cardTotal;
    private TextView playerCardTotal;
    private ImageView[] images;

    public Player(){
        this.cardPos = 0;
        this.numCards = 0;
        this.numAces = 0;
        this.cardTotal = 0;
        images = new ImageView[12];
    }

    public void resetValues(){
        this.cardPos = 0;
        this.numCards = 0;
        this.numAces = 0;
        this.cardTotal = 0;
        playerCardTotal.setText("");
    }

    public int getCardPos() {
        return cardPos;
    }

    public void setCardPos(int cardPos) {
        this.cardPos = cardPos;
    }

    public int getNumCards() {
        return numCards;
    }

    public void setNumCards(int numCards) {
        this.numCards = numCards;
    }

    public int getNumAces() {
        return numAces;
    }

    public void setNumAces(int numAces) {
        this.numAces = numAces;
    }

    public int getCardTotal() {
        return cardTotal;
    }

    public void setCardTotal(int cardTotal) {
        this.cardTotal = cardTotal;
    }

    public TextView getPlayerCardTotal() {
        return playerCardTotal;
    }

    public void setPlayerCardTotal(TextView playerCardTotal) {
        this.playerCardTotal = playerCardTotal;
    }

    public ImageView[] getImages() {
        return images;
    }

    public void setImages(ImageView[] images) {
        this.images = images;
    }
}
