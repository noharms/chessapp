package com.chess.model;

import java.util.HashSet;
import java.util.Set;

public class MovesBasic {

  public static final Set<Coordinates> K_BASIC_MOVES_KNIGHT = new HashSet<>() {
    {
      add(new Coordinates(2, 1));
      add(new Coordinates(2, -1));
      add(new Coordinates(1, 2));
      add(new Coordinates(1, -2));
      add(new Coordinates(-2, -1));
      add(new Coordinates(-2, 1));
      add(new Coordinates(-1, -2));
      add(new Coordinates(-1, 2));
    }
  };

  public static final Set<Coordinates> K_BASIC_MOVES_BISHOP = new HashSet<>() {
    {
      add(new Coordinates(-1, -1));
      add(new Coordinates(-1, 1));
      add(new Coordinates(1, -1));
      add(new Coordinates(1, 1));
    }
  };

  public static final Set<Coordinates> K_BASIC_MOVES_ROOK = new HashSet<>() {
    {
      add(new Coordinates(1, 0));
      add(new Coordinates(-1, 0));
      add(new Coordinates(0, 1));
      add(new Coordinates(0, -1));
    }
  };

  public static final Set<Coordinates> K_BASIC_MOVES_KING = new HashSet<>() {
    {
      add(new Coordinates(1, 0));
      add(new Coordinates(-1, 0));
      add(new Coordinates(0, 1));
      add(new Coordinates(0, -1));
      add(new Coordinates(-1, -1));
      add(new Coordinates(-1, 1));
      add(new Coordinates(1, -1));
      add(new Coordinates(1, 1));
    }
  };

  public static final Set<Coordinates> K_BASIC_MOVES_QUEEN = K_BASIC_MOVES_KING;

}
