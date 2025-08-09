package com.monopoly_deal.v1.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class Player {
    private String id;
    private String name;

    private List<Card> hand = new ArrayList<>();

    // because Action or Rent card can be played as money
    private List<Card> bank = new ArrayList<>();
    private List<PropertySet> propertySets = new ArrayList<>();

    public Player(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    // utility methods
    public void addToHand(Card card) {
        hand.add(card);
    }

    public void addToBank(Card card) {
        bank.add(card);
    }
    
    /*
     * TODO: how to add Wild Card on top of a property?
     * 
     */
    public void addPropertyCard(PropertyCard card) {
        // find matching PropertySet by color or create new
        for(PropertySet set: propertySets) {
            if(set.getColor() == card.getColor()) {
                set.addCard(card);
                return;
            }
        }

        // if no matching set found, create a new one
        PropertySet newSet = new PropertySet(card.getColor());
        newSet.addCard(card);
        propertySets.add(newSet);
    }
}
