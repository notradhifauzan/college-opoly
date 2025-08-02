package com.monopoly_deal.v1.model;

import com.monopoly_deal.v1.enums.CardType;

import lombok.Data;

@Data
public abstract class Card {
    protected String id;
    protected String name;
    protected int value;
    protected CardType cardType;
}
