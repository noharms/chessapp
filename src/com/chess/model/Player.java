package com.chess.model;

public enum Player {
  W("WHITE", 0),
  B("BLACK", 1);

  private String color;
  private int ID;

  private Player(String color, int ID) {
    this.color = color;
    this.ID = ID;
  }

  public boolean isWhite() {
    return (this == Player.W);
  }

  public boolean isBlack() {
    return (this == Player.B);
  }

}
