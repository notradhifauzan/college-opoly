package com.monopoly_deal.v1.model;

import com.monopoly_deal.v1.enums.CardType;

public class MoneyCard extends Card {

    public MoneyCard(String id, int value) {
        this.id = id;
        this.value = value;
        this.name = value + "M";
        this.cardType = CardType.MONEY;
    }
}
