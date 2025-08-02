package com.monopoly_deal.v1.model;

import com.monopoly_deal.v1.enums.CardType;
import com.monopoly_deal.v1.enums.PropertyColor;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PropertyCard extends Card {
    private PropertyColor color;

    public PropertyCard(String id, String name, int value, PropertyColor color) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.color = color;
        this.cardType = CardType.PROPERTY;
    }
}
