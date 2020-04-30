package com.chess.model;

/**
 *  TODO: refactor this class... artifact from Codewars (e.g. use Coordinate class)
 *
 *
 *  Note: all members are final, so after instantiasation one can only
 *        read the members but never change them
 */
public class PieceConfig {

  private static final int K_WITHOUT_PREVIOUS_POSITION = -1;

  private final Piece pieceType;
  private final Player player;
  private final int row;  // replace by Coordinates
  private final int col;
  private final int prevRow;
  private final int prevCol;

  public PieceConfig(Piece piece, Player player, int row, int col, int prevRow, int prevCol) {
    this.pieceType = piece;
    this.player = player;
    this.row = row;
    this.col = col;
    this.prevRow = prevRow;
    this.prevCol = prevCol;
  }
  public PieceConfig(Piece piece, Player player, int row, int col) {
    this.pieceType = piece;
    this.player = player;
    this.row = row;
    this.col = col;
    this.prevRow = K_WITHOUT_PREVIOUS_POSITION;
    this.prevCol = K_WITHOUT_PREVIOUS_POSITION;
  }
  public PieceConfig(PieceConfig other) {
    this.pieceType = other.pieceType;
    this.player = other.player;
    this.row = other.row;
    this.col = other.col;
    this.prevRow = other.prevRow;
    this.prevCol = other.prevCol;
  }
  public PieceConfig(PieceConfig other, Move move) {
    this.pieceType = other.pieceType;
    this.player = other.player;
    this.row = move.getNewPos().getRow();
    this.col = move.getNewPos().getCol();
    this.prevRow = other.row;
    this.prevCol = other.col;
    if (other.getCol() != move.getOldPos().col
            || other.getRow() != move.getOldPos().row) {
      throw new Error("Unreasonable combination of piece to move and move.");
    }
  }

  public Piece getPieceType() {
    return pieceType;
  }
  public Player getPlayer() {
    return player;
  }
  public int getRow() { return row;}
  public int getCol() { return col;}
  public int getPrevRow() throws RuntimeException {
    if (prevRow < 0) {
      throw new RuntimeException();
    }
    return prevRow;
  }
  public int getPrevCol() throws RuntimeException {
    if (prevCol < 0) {
      throw new RuntimeException();
    }
    return prevCol;
  }

  public boolean isPawn() {
    return pieceType == Piece.P;
  }

  public boolean isRook() {
    return pieceType == Piece.R;
  }

  public boolean isKnight() {
    return pieceType == Piece.N;
  }

  public boolean isBishop() {
    return pieceType == Piece.B;
  }

  public boolean isQueen() {
    return pieceType == Piece.Q;
  }

  public boolean isKing() {
    return pieceType == Piece.K;
  }

  @Override
  public int hashCode() {
    int hashCode = 0;
    final int kMult = 17;
    for (int i = 0; i < pieceType.getName().length(); ++i) {
      hashCode *= kMult;
      hashCode += (pieceType.getName().charAt(i) + row + col + prevRow + prevCol);
    }
    if (player == player.B) {
      hashCode *= -1;
    }
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
    PieceConfig otherPieceConfig = (PieceConfig)obj;
    if (!this.pieceType.equals(otherPieceConfig.pieceType)) {
      return false;
    }
    if (this.row != otherPieceConfig.row) {
      return false;
    }
    if (this.col != otherPieceConfig.col) {
      return false;
    }
    if (this.prevRow != otherPieceConfig.prevRow) {
      return false;
    }
    if (this.prevCol != otherPieceConfig.prevCol) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("piece: ");
    builder.append(pieceType);
    builder.append(", owner: ");
    builder.append(player);
    builder.append(", row: ");
    builder.append(row);
    builder.append(", col: ");
    builder.append(col);
    if (prevRow != K_WITHOUT_PREVIOUS_POSITION) {
      builder.append(", prevX: ");
      builder.append(prevRow);
    }
    if (prevCol != K_WITHOUT_PREVIOUS_POSITION) {
      builder.append(", prevY: ");
      builder.append(prevCol);
    }
    return builder.toString();
  }


}
