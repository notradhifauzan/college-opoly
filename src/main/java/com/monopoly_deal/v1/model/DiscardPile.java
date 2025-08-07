package com.monopoly_deal.v1.model;

import java.util.ArrayList;
import java.util.List;

public class DiscardPile {

    private List<Card> cards = new ArrayList<>();
    
    public void add(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}   
