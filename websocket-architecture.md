# WebSocket Architecture - Monopoly Deal

This diagram shows the event-driven architecture implemented for real-time WebSocket communication in the Monopoly Deal game.

## Architecture Diagram

```mermaid
graph TB
    subgraph "Client Layer"
        Client1[Client 1 - Browser]
        Client2[Client 2 - Phone]
    end

    subgraph "Controller Layer"
        GC[GameController<br/>REST API]
        WSC[WebSocketController<br/>STOMP Messages]
    end

    subgraph "Service Layer"
        GS[GameService]
    end

    subgraph "Engine Layer (Domain Logic)"
        GE[GameEngine<br/>Pure Game Logic]
        EP[ApplicationEventPublisher<br/>Spring Component]
    end

    subgraph "Event System"
        GSE[GameStartedEvent]
        CPE[CardPlayedEvent]  
        TEE[TurnEndedEvent]
        GWE[GameWonEvent]
    end

    subgraph "Listeners (Infrastructure)"
        GEL[GameEventListener<br/>@EventListener]
    end

    subgraph "Configuration"
        WSConfig[WebSocketConfig<br/>STOMP + SockJS]
    end

    %% Client connections
    Client1 -.->|HTTP REST| GC
    Client2 -.->|HTTP REST| GC
    Client1 <-.->|WebSocket/STOMP| WSC
    Client2 <-.->|WebSocket/STOMP| WSC
    WSConfig -.->|Configures| WSC

    %% Request flow
    GC --> GS
    GS --> GE
    
    %% Event publishing
    GE --> EP
    EP -.->|Publishes| GSE
    EP -.->|Publishes| CPE
    EP -.->|Publishes| TEE  
    EP -.->|Publishes| GWE

    %% Event handling
    GSE -.->|@EventListener| GEL
    CPE -.->|@EventListener| GEL
    TEE -.->|@EventListener| GEL
    GWE -.->|@EventListener| GEL

    %% Broadcasting
    GEL --> WSC
    WSC -.->|Broadcast to /topic/game/updates| Client1
    WSC -.->|Broadcast to /topic/game/updates| Client2

    %% Styling
    classDef domainLogic fill:#e1f5fe,stroke:#01579b,stroke-width:2px
    classDef infrastructure fill:#f3e5f5,stroke:#4a148c,stroke-width:2px
    classDef events fill:#fff3e0,stroke:#e65100,stroke-width:2px
    classDef clients fill:#e8f5e8,stroke:#2e7d32,stroke-width:2px

    class GE,EP domainLogic
    class WSC,GEL,WSConfig infrastructure
    class GSE,CPE,TEE,GWE events
    class Client1,Client2 clients
```

## Flow Explanation

### 1. REST API Flow
1. Client makes HTTP request → `GameController`
2. `GameController` → `GameService` → `GameEngine`
3. `GameEngine` executes pure game logic

### 2. Event-Driven WebSocket Flow
1. `GameEngine` publishes domain event via `ApplicationEventPublisher`
2. `GameEventListener` catches event with `@EventListener`
3. `GameEventListener` calls `WebSocketController.broadcast()`
4. `WebSocketController` sends message to all connected clients via STOMP

### 3. Key Benefits of This Architecture

**🎯 Separation of Concerns:**
- `GameEngine` = Pure domain logic + event publishing
- `GameEventListener` = Infrastructure concern (WebSocket)
- Clean boundary between business and infrastructure

**🔄 Loose Coupling:**
- GameEngine doesn't know about WebSocket
- Easy to add more listeners (email, logging, analytics)
- Can test GameEngine independently

**📡 Real-time Communication:**
- All clients get instant updates when game state changes
- Bidirectional communication via WebSocket
- Scalable to multiple players

**🧪 Testability:**
- GameEngine can be unit tested without WebSocket infrastructure
- Events can be verified independently
- Infrastructure concerns are isolated

## Implementation Files

### Domain Events
- `GameEvent.java` - Base event class
- `GameStartedEvent.java` - Game initialization event
- `CardPlayedEvent.java` - Card play event
- `TurnEndedEvent.java` - Turn change event
- `GameWonEvent.java` - Game completion event

### Infrastructure
- `WebSocketConfig.java` - STOMP configuration
- `WebSocketController.java` - Message broadcasting
- `GameEventListener.java` - Event to WebSocket bridge

### Domain Logic
- `GameEngine.java` - Pure game logic with event publishing
- Uses `ApplicationEventPublisher` for domain events

This pattern follows **Domain-Driven Design (DDD)** principles and is commonly known as **Domain Events**.