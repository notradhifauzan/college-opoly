package com.monopoly_deal.v1.model;

import java.util.Map;
import java.util.UUID;

import com.monopoly_deal.v1.enums.ActionType;

public class PendingAction {
    private final String actionId;
    
    private final String requestingPlayerId;
    private final String targetPlayerId;
    private final ActionType actionType;

    private final Map<String, Object> payload;

    public PendingAction(String from, String to, Map<String,Object> payload, ActionType actionType) {
        this.actionId = UUID.randomUUID().toString();
        requestingPlayerId = from;
        targetPlayerId = to;
        this.payload = payload;
        this.actionType = actionType;
    }

    public String getActionId() {
        return actionId;
    }

    public String getRequestingPlayerId() {
        return requestingPlayerId;
    }

    public String getTargetPlayerId() {
        return targetPlayerId;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public ActionType getActionType() {
        return actionType;
    }
}
