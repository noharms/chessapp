package com.chess.model;

public class Move extends SrcDstCoordinates {
  private final boolean isAttack;
  private final boolean isEnPassantAttack;

  public Move(Coordinates coorsNew, Coordinates coorsOld, boolean isAttack) {
    super(coorsNew, coorsOld);
    this.isAttack = isAttack;
    this.isEnPassantAttack = false;
  }
  public Move(Coordinates coorsNew, Coordinates coorsOld, boolean isAttack, boolean isEnPassantAttack) {
    super(coorsNew, coorsOld);
    this.isAttack = isAttack;
    this.isEnPassantAttack = isEnPassantAttack;
  }

  public Move(Move other) {
    super(other.newPos, other.oldPos);
    this.isAttack = other.isAttack;
    this.isEnPassantAttack = other.isEnPassantAttack;
  }

  public boolean isAttack() { return isAttack; }
  public boolean isEnPassantAttack() { return isEnPassantAttack; }


  @Override
  public int hashCode() {
    int hashCode = 0;
    int kMult = 17;
    hashCode += newPos.getRow() + oldPos.getRow();
    hashCode += (newPos.getCol() + oldPos.getCol()) * kMult;
    hashCode += (isAttack ? 1 : 0) * kMult*kMult;
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
    Move other = (Move)obj;
    if (!newPos.equals(other.newPos)) {
      return false;
    }
    if (!oldPos.equals(other.oldPos)) {
      return false;
    }
    if (isAttack != other.isAttack) {
      return false;
    }
    return true;
  }

}
