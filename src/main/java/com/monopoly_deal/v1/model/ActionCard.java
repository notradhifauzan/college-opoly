package com.monopoly_deal.v1.model;

import com.monopoly_deal.v1.enums.ActionType;
import com.monopoly_deal.v1.enums.CardType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ActionCard extends Card {
    private ActionType actionType;

    public ActionCard(String id, String name, int value, ActionType actionType) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.cardType = CardType.ACTION;
        this.actionType = actionType;
    }
}
