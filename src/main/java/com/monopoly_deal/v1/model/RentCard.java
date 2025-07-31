package com.monopoly_deal.v1.model;

import java.util.Set;

import com.monopoly_deal.v1.enums.CardType;
import com.monopoly_deal.v1.enums.PropertyColor;

public class RentCard extends Card {
    private Set<PropertyColor> applicableColors;

    public RentCard(String id, String name, int value, Set<PropertyColor> colors) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.applicableColors = colors;
        this.cardType = CardType.ACTION;
    }

    public Set<PropertyColor> getApplicableColors() {
        return applicableColors;
    }


    public void setApplicableColors(Set<PropertyColor> applicableColors) {
        this.applicableColors = applicableColors;
    }

    
}
