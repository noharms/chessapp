package com.chess.model;

import static com.chess.model.BoardUtils.K_DIM;

public class Coordinates {

  public final int row, col; // row 0 == 8, col 0 == 'a'; row 7 == 1, col 7 == 'h'

  Coordinates(int row, int col) {
    this.row = row;
    this.col = col;
  }
  Coordinates(Coordinates other) {
    this.row = other.row;
    this.col = other.col;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Coordinates)) {
      return false;
    }
    Coordinates other = (Coordinates)obj;
    return (row == other.row && col == other.col);
  }

  @Override
  public int hashCode() {
    return (row % (Integer.MAX_VALUE / 2) + col % (Integer.MAX_VALUE / 2));
  }

  public boolean isInsideBoard(){
    return (row >= 0 && col >= 0 && row < K_DIM && col < K_DIM);
  }
  public boolean isOutsideBoard(){
    return !isInsideBoard();
  }

  public int getRow() {
    return row;
  }
  public int getCol() {
    return col;
  }

  public static Coordinates add(Coordinates coors1, Coordinates coors2) {
    return new Coordinates(coors1.row + coors2.row, coors1.col + coors2.col);
  }

}
