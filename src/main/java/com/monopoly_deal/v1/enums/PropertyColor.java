package com.monopoly_deal.v1.enums;

public enum PropertyColor {
    RED(3),
    GREEN(3),
    PURPLE(3),
    PINK(3),
    NAVY(2),
    ORANGE(3),
    BLUE(3);
    
    private final int completionRequirement;
    
    PropertyColor(int completionRequirement) {
        this.completionRequirement = completionRequirement;
    }
    
    public int getCompletionRequirement() {
        return completionRequirement;
    }
}
