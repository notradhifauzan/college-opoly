package com.monopoly_deal.v1.model;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Deck {
    private Deque<Card> cards = new LinkedList<>();

    public Deck(List<Card> initialCards) {
        this.cards.addAll(initialCards);
        shuffle();
    }

    public Card draw() {
        return cards.pollFirst();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

    private void shuffle() {
        List<Card> tempList = new LinkedList<>(cards);
        Collections.shuffle(tempList);
        cards.clear();
        cards.addAll(tempList);
    }

    // reshuffle from discardPile (if runs out of card)
    public void reshuffleFrom(List<Card> discardCards) {
        cards.addAll(discardCards);
        shuffle();
    }
}
