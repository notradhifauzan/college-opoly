# Design Doc: Player Interaction and Pending Actions

This document outlines the design for implementing interactive, player-to-player actions (e.g., Rent, Just Say No) in the Monopoly Deal game.

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

### 6. Implementation Plan

The agreed-upon implementation will start with the backend logic first:
1. Create the `ActionType.java` enum.
2. Create the `PendingAction.java` class.
3. Add a `List<PendingAction>` field to `GameState.java`.
4. Begin modifying the `CardActionStrategy` classes (starting with `RentCardStrategy`) to create and add `PendingAction` objects to the game state instead of processing the game event directly.
