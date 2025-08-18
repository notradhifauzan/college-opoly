# Monopoly Deal Card Game

## Project Overview

This project is a Java-based implementation of the Monopoly Deal card game. It is built using the Spring Boot framework, providing a RESTful API for interacting with the game. The application is designed to be played by multiple players, with the game state managed on the server.

The project follows a modular architecture, with clear separation of concerns between controllers, services, and the game engine. The core game logic is encapsulated within the `GameEngine` class, which manages the game flow, player actions, and win conditions. The `GameController` exposes a set of API endpoints for clients to interact with the game, such as starting a new game, drawing cards, playing cards, and ending a turn.

The game uses a set of JSON files to define the different types of cards in the game, including action cards, money cards, property cards, and wild cards. These cards are loaded into the game at startup, providing a flexible and extensible way to manage the card deck.

## Building and Running

The project is built using Apache Maven. To build and run the project, you can use the following commands:

```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The application will be available at `http://localhost:8080`.

## Development Conventions

The project uses the following development conventions:

*   **Language:** Java 17
*   **Framework:** Spring Boot
*   **Build Tool:** Apache Maven
*   **Code Style:** The project follows the standard Java code style, with a few minor variations.
*   **Testing:** The project uses JUnit 5 for unit testing.
*   **Dependencies:** The project uses Lombok to reduce boilerplate code.

## API Endpoints

The following are the main API endpoints provided by the application:

*   `POST /game/start`: Starts a new game with a list of player names.
*   `GET /game/state`: Returns the current state of the game.
*   `POST /game/draw`: Draws two cards for the current player.
*   `POST /game/play-card`: Plays a card from the current player's hand.
*   `POST /game/end-turn`: Ends the current player's turn.
*   `GET /game/status`: Returns the status of the game server.
*   `GET /game/random-card`: Returns a random card from the current player's hand.

## Architectural Decisions

Major feature designs and architectural decisions are recorded in separate `DESIGN-*.md` files.
*   See `DESIGN-PENDING_ACTION.md` for the implementation plan for player-to-player interactions and the `PendingAction` model.