package com.monopoly_deal.v1.service.strategies;

import com.monopoly_deal.v1.model.PropertyCard;
import com.monopoly_deal.v1.service.context.ActionContext;

public class PropertyCardStrategy implements CardActionStrategy {
    @Override
    public void execute(ActionContext context) {
        if (!(context.getCard() instanceof PropertyCard)) {
            throw new IllegalStateException("Expected PropertyCard, got " + context.getCard().getClass().getSimpleName());
        }
        context.getPlayer().addPropertyCard((PropertyCard) context.getCard());
    }
}
