package com.monopoly_deal.v1.events;

import org.springframework.context.ApplicationEvent;

public abstract class GameEvent extends ApplicationEvent {
    private final String gameId;
    private final String message;

    public GameEvent(Object source, String gameId, String message) {
        super(source);
        this.gameId = gameId;
        this.message = message;
    }

    public String getGameId() {
        return gameId;
    }

    public String getMessage() {
        return message;
    }
}