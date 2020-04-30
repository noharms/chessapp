package com.chess.model;

public class GameUtils {

  private GameUtils() {
    throw new RuntimeException("Class is supposed to be used statically, i.e. without instantiating it.");
  }

  public static Game createGame() {
    return new Game();
  }
}
