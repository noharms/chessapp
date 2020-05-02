package com.chess.gui;

public enum BoardDirection {
  NORMAL {
    @Override
    BoardDirection opposite() {
      return FLIPPED;
    }
  },
  FLIPPED {
    @Override
    BoardDirection opposite() {
      return NORMAL;
    }
  };

  abstract BoardDirection opposite();
}
