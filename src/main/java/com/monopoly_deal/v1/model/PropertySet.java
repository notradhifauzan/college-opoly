package com.monopoly_deal.v1.model;

import java.util.ArrayList;
import java.util.List;

public class PropertySet {
    private PropertyColor color;
    private List<PropertyCard> cards = new ArrayList<>();

    // upon creation, only set the color
    public PropertySet(PropertyColor color) {
        this.color = color;
    }

    // utility
    public void addCard(PropertyCard card) {
        cards.add(card);
    }

    // getter setter
    public PropertyColor getColor() {
        return color;
    }
    public void setColor(PropertyColor color) {
        this.color = color;
    }
    public List<PropertyCard> getCards() {
        return cards;
    }
    public void setCards(List<PropertyCard> cards) {
        this.cards = cards;
    }

    
}
