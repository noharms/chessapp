package com.chess.model;

public enum Piece {  // TODO: other way around, enum constant one letter and string as value

  P("PAWN",  1),
  R("ROOK", 5),
  N("KNIGHT", 3),
  B("BISHOP", 3),
  Q("QUEEN", 9),
  K("KING", Integer.MAX_VALUE);

  private final String name;
  private final int value;

  Piece(String name, int value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }
  public int getValue() {
    return value;
  }

}
