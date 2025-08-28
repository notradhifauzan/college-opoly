# Design Doc: Player Interaction and Pending Actions

This document outlines the design for implementing interactive, player-to-player actions (e.g., Rent, Just Say No) in the Monopoly Deal game.

## Current Progress Summary (2025-08-28)

### ✅ Completed Features:

1. **WebSocket Architecture Migration** - Successfully migrated from REST API to WebSocket-based communication
2. **Game Start via WebSocket** - All players receive synchronized game start notifications
3. **Card Drawing with Privacy** - Implemented private/public messaging system:
   - Drawing player sees actual cards drawn (private message)
   - Other players only see card count (public message)
4. **Turn Validation** - Frontend validates player turns before allowing actions
5. **Real-time Game State Updates** - All players receive synchronized game state via WebSocket
6. **Sly Deal Action Card Implementation** - Complete backend foundation:
   - ActionCard model with ActionType enum integration
   - PendingAction creation and GameState integration
   - PENDING_ACTION_PHASE game phase for response handling
   - End-to-end card play flow via WebSocket

### 🏗️ Architecture Updates:

- **GameController** - Kept for reference but marked as ignored in CLAUDE.md
- **WebSocketController** - Now handles all game interactions:
  - `/app/game/start` - Start game with player list
  - `/app/game/draw` - Draw cards with privacy protection
  - `/app/game/play-card` - Play cards including action cards
  - `/app/game/respond-to-action` - Response handler (stub created)
- **Frontend** - Updated websocket-test.html with:
  - Private message subscription per player (`/queue/player/{playerId}`)
  - Public message handling (`/topic/game/updates`)
  - Turn-based UI controls

### 1. The Core Problem

The initial architecture was purely sequential, lacking a mechanism to handle game states where the engine must pause and wait for input from a specific player who is not the current turn-taker. This is a requirement for most action cards.

### 2. The Solution: The `PendingAction` State

The chosen solution is to introduce a `PendingAction` object into the `GameState`. 

- **Purpose**: This object acts as a "pause" signal, indicating the game is waiting for a specific player to respond to an event.
- **Data Structure**: The `GameState` will contain a `List<PendingAction>` to correctly handle scenarios where one card requires simultaneous responses from multiple players (e.g., an "all players pay rent" card).

### 3. Communication Models & The Role of `PendingAction`

We analyzed two communication protocols, concluding that the `PendingAction` logic is a core, backend concept essential for both:

- **REST API (Polling)**: The immediate implementation path. Clients must repeatedly poll a `/game/state` endpoint. When a client sees a `PendingAction` in the response that targets them, it prompts the user for input.
- **WebSockets (Pushing)**: The more advanced, efficient model. The server actively pushes the updated `GameState` to all clients instantly when a change occurs, eliminating the need for polling.

### 4. Visualizing the Flow

To clarify complex interactions, we created several **Mermaid sequence diagrams** to visualize the message flow for:

- The basic RESTful polling loop.
- A WebSocket-based rent card play.
- A complex "Just Say No" counter-play sequence, demonstrating how the `PendingAction` model can manage a stack of actions and responses.

### 5. `PendingAction` Class Design

We finalized the design for the `PendingAction.java` class:

- **Single, Concrete Class**: We opted for a single class design rather than a complex inheritance hierarchy.
- **Flexible Payload**: It will use a `Map<String, Object> payload` to store action-specific data. This is extensible and easy to serialize.
- **`ActionType` Enum**: A type-safe enum (`ActionType.java`) will define the nature of the action (e.g., `RENT_PAYMENT`, `JUST_SAY_NO_RESPONSE`).
- **Key Fields**: The class will include `actionId` (UUID), `targetPlayerId`, and `requestingPlayerId` to uniquely identify and route the action.
- **Immutability**: All fields will be `final` to make the object immutable, which is a best practice that prevents a wide range of bugs.

### 6. Next Implementation Steps

**Immediate Next Features to Implement:**

1. **End Turn via WebSocket** - Complete the turn cycle
2. **Play Card via WebSocket** - Allow playing cards with privacy
3. **Game State Display** - Better UI for game state visualization

**✅ PendingActions Implementation Completed:**

1. ✅ Created the `ActionType.java` enum with SLY_DEAL, JUST_SAY_NO, DEAL_BREAKER, ITS_MY_BIRTHDAY
2. ✅ Created the `PendingAction.java` class with immutable design and UUID generation
3. ✅ Added `List<PendingAction>` field to `GameState.java` with proper initialization
4. ✅ Modified `ActionCardStrategy` to create and add `PendingAction` objects instead of direct processing
5. ✅ Added `PENDING_ACTION_PHASE` to GamePhase enum for response handling
6. ✅ Updated GameEngine to transition to PENDING_ACTION_PHASE when PendingActions exist

**Remaining Implementation Tasks:**

1. **Response Handler Implementation** - Complete `/game/respond-to-action` endpoint
2. **Property Transfer Logic** - Implement actual sly deal execution when target accepts
3. **Property Validation** - Verify target player owns requested property
4. **Just Say No Counter Logic** - Stack-based PendingAction resolution
5. **Frontend Implementation** - UI for displaying and responding to PendingActions

**Current Architecture Benefits:**

- ✅ Real-time synchronization solved with WebSocket
- ✅ Privacy protection implemented with dual messaging  
- ✅ Turn validation working properly
- ✅ Clean separation between GameController (deprecated) and WebSocketController (active)
- ✅ PendingAction foundation ready for interactive gameplay
- ✅ Game phase management for response handling
