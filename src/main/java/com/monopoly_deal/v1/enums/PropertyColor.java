package com.monopoly_deal.v1.enums;

public enum PropertyColor {
    RED(3),
    GREEN(2),
    BLUE(2);
    
    private final int completionRequirement;
    
    PropertyColor(int completionRequirement) {
        this.completionRequirement = completionRequirement;
    }
    
    public int getCompletionRequirement() {
        return completionRequirement;
    }
}
