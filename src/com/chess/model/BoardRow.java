package com.chess.model;

public enum BoardRow {
  ROW_1(7),
  ROW_2(6),
  ROW_3(5),
  ROW_4(4),
  ROW_5(3),
  ROW_6(2),
  ROW_7(1),
  ROW_8(0);

  int technicalIdx;

  BoardRow(int technicalIdx) {
    this.technicalIdx = technicalIdx;
  }

  public int getIdx() {
    return technicalIdx;
  }
}
