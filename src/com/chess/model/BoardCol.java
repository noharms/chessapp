package com.chess.model;

public enum BoardCol {
  COL_A(0),
  COL_B(1),
  COL_C(2),
  COL_D(3),
  COL_E(4),
  COL_F(5),
  COL_G(6),
  COL_H(7);

  int technicalIdx;

  BoardCol(int technicalIdx) {
    this.technicalIdx = technicalIdx;
  }

  public int getIdx() {
    return technicalIdx;
  }

}
