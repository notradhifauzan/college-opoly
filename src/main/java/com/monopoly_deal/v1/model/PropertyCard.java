package com.monopoly_deal.v1.model;

public class PropertyCard extends Card {
    private PropertyColor color;

    public PropertyCard(String id, String name, int value, PropertyColor color) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.color = color;
        this.cardType = CardType.PROPERTY;
    }

    public PropertyColor getColor() {
        return color;
    }

    public void setColor(PropertyColor color) {
        this.color = color;
    }
}
