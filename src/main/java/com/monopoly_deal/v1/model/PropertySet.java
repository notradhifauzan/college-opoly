package com.monopoly_deal.v1.model;

import java.util.ArrayList;
import java.util.List;

import com.monopoly_deal.v1.enums.PropertyColor;

import lombok.Data;

@Data
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
}
