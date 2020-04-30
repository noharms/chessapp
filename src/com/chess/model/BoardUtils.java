package com.chess.model;

public class BoardUtils {

  public static final int K_DIM = 8;

  public static final int K_WHITE = 0;
  public static final int K_BLACK = 1;

  public static final int K_DIRECTION_WHITE = -1;
  public static final int K_DIRECTION_BLACK = 1;

  public static final int K_ROW_WHITE_PAWNS_START= 6;
  public static final int K_ROW_BLACK_PAWNS_START = 1;
  public static final int K_ROW_BLACK_PAWNS_WAIT_FOR_ENPASSANT = 4;
  public static final int K_ROW_WHITE_PAWNS_WAIT_FOR_ENPASSANT = 3;

  public static int getLinIdx(int row, int col) {
    return row * K_DIM + col;
  }

  public static int getRowFromLinIdx(int linIdx) {
    return linIdx / 8;
  }

  public static int getColFromLinIdx(int linIdx) {
    return linIdx % 8;
  }

}
