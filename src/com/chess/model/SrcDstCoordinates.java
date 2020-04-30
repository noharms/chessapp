package com.chess.model;

public class SrcDstCoordinates {

  protected final Coordinates oldPos;
  protected final Coordinates newPos;

  public SrcDstCoordinates(int rowNew, int colNew, int rowOld, int colOld) {
    newPos = new Coordinates(rowNew, colNew);
    oldPos = new Coordinates(rowOld, colOld);
  }
  public SrcDstCoordinates(Coordinates coorsNew, Coordinates coorsOld) {
    newPos = new Coordinates(coorsNew);
    oldPos = new Coordinates(coorsOld);
  }

  public Coordinates getNewPos() {
    return newPos;
  }
  public Coordinates getOldPos() {
    return oldPos;
  }


  @Override
  public int hashCode() {
    int hashCode = 0;
    int kMult = 17;
    hashCode += newPos.getRow() + oldPos.getRow();
    hashCode += (newPos.getCol() + oldPos.getCol()) * kMult;
    return hashCode;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this == obj) {
      return true;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    SrcDstCoordinates other = (SrcDstCoordinates)obj;
    if (!newPos.equals(other.newPos)) {
      return false;
    }
    if (!oldPos.equals(other.oldPos)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
