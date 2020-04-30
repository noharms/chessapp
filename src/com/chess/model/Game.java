package com.chess.model;

import java.util.HashSet;
import java.util.Set;

public class Game {

  PieceConfig[] pieces;
  Board board;
  Player playerToMove = Player.W;

  public Game() {
    PieceConfig[] pieces = new PieceConfig[] {
            new PieceConfig(Piece.P, Player.B, BoardRow.ROW_7.getIdx(), BoardCol.COL_A.getIdx()),
            new PieceConfig(Piece.P, Player.B, BoardRow.ROW_7.getIdx(), BoardCol.COL_B.getIdx()),
            new PieceConfig(Piece.P, Player.B, BoardRow.ROW_7.getIdx(), BoardCol.COL_C.getIdx()),
            new PieceConfig(Piece.P, Player.B, BoardRow.ROW_7.getIdx(), BoardCol.COL_D.getIdx()),
            new PieceConfig(Piece.P, Player.B, BoardRow.ROW_7.getIdx(), BoardCol.COL_E.getIdx()),
            new PieceConfig(Piece.P, Player.B, BoardRow.ROW_7.getIdx(), BoardCol.COL_F.getIdx()),
            new PieceConfig(Piece.P, Player.B, BoardRow.ROW_7.getIdx(), BoardCol.COL_G.getIdx()),
            new PieceConfig(Piece.P, Player.B, BoardRow.ROW_7.getIdx(), BoardCol.COL_H.getIdx()),
            new PieceConfig(Piece.R, Player.B, BoardRow.ROW_8.getIdx(), BoardCol.COL_A.getIdx()),
            new PieceConfig(Piece.N, Player.B, BoardRow.ROW_8.getIdx(), BoardCol.COL_B.getIdx()),
            new PieceConfig(Piece.B, Player.B, BoardRow.ROW_8.getIdx(), BoardCol.COL_C.getIdx()),
            new PieceConfig(Piece.Q, Player.B, BoardRow.ROW_8.getIdx(), BoardCol.COL_D.getIdx()),
            new PieceConfig(Piece.K, Player.B, BoardRow.ROW_8.getIdx(), BoardCol.COL_E.getIdx()),
            new PieceConfig(Piece.B, Player.B, BoardRow.ROW_8.getIdx(), BoardCol.COL_F.getIdx()),
            new PieceConfig(Piece.N, Player.B, BoardRow.ROW_8.getIdx(), BoardCol.COL_G.getIdx()),
            new PieceConfig(Piece.R, Player.B, BoardRow.ROW_8.getIdx(), BoardCol.COL_H.getIdx()),
            new PieceConfig(Piece.P, Player.W, BoardRow.ROW_2.getIdx(), BoardCol.COL_A.getIdx()),
            new PieceConfig(Piece.P, Player.W, BoardRow.ROW_2.getIdx(), BoardCol.COL_B.getIdx()),
            new PieceConfig(Piece.P, Player.W, BoardRow.ROW_2.getIdx(), BoardCol.COL_C.getIdx()),
            new PieceConfig(Piece.P, Player.W, BoardRow.ROW_2.getIdx(), BoardCol.COL_D.getIdx()),
            new PieceConfig(Piece.P, Player.W, BoardRow.ROW_2.getIdx(), BoardCol.COL_E.getIdx()),
            new PieceConfig(Piece.P, Player.W, BoardRow.ROW_2.getIdx(), BoardCol.COL_F.getIdx()),
            new PieceConfig(Piece.P, Player.W, BoardRow.ROW_2.getIdx(), BoardCol.COL_G.getIdx()),
            new PieceConfig(Piece.P, Player.W, BoardRow.ROW_2.getIdx(), BoardCol.COL_H.getIdx()),
            new PieceConfig(Piece.R, Player.W, BoardRow.ROW_1.getIdx(), BoardCol.COL_A.getIdx()),
            new PieceConfig(Piece.N, Player.W, BoardRow.ROW_1.getIdx(), BoardCol.COL_B.getIdx()),
            new PieceConfig(Piece.B, Player.W, BoardRow.ROW_1.getIdx(), BoardCol.COL_C.getIdx()),
            new PieceConfig(Piece.Q, Player.W, BoardRow.ROW_1.getIdx(), BoardCol.COL_D.getIdx()),
            new PieceConfig(Piece.K, Player.W, BoardRow.ROW_1.getIdx(), BoardCol.COL_E.getIdx()),
            new PieceConfig(Piece.B, Player.W, BoardRow.ROW_1.getIdx(), BoardCol.COL_F.getIdx()),
            new PieceConfig(Piece.N, Player.W, BoardRow.ROW_1.getIdx(), BoardCol.COL_G.getIdx()),
            new PieceConfig(Piece.R, Player.W, BoardRow.ROW_1.getIdx(), BoardCol.COL_H.getIdx()),
    };

    board = new Board(pieces);

    // DEBUG
    board.print();
  }

  public void applyMove(Move selectedMove) {
    PieceConfig pieceConfig = board.getPieceAtCoordinates(selectedMove.getOldPos());
    if (pieceConfig == null) {
      throw new RuntimeException("Invalid Move for given board.");
    }
    board = new Board(board, pieceConfig, selectedMove);
    playerToMove = (playerToMove == Player.W ? Player.B : Player.W);
  }

  public Set<Coordinates> computeValidDestinationCoors(int src_row, int src_col) {
    PieceConfig pieceConfig = board.getPieceAtCoordinates(src_row, src_col);
    return EngineMoves.computeValidDstCoordinatesForPiece(board, pieceConfig);
  }
  public Set<Coordinates> computeValidDestinationCoors(Coordinates srcCoors) {
    PieceConfig pieceConfig = board.getPieceAtCoordinates(srcCoors.row, srcCoors.col);
    return EngineMoves.computeValidDstCoordinatesForPiece(board, pieceConfig);
  }
  public Set<Move> computeValidMoves(Coordinates srcCoors) {
    PieceConfig pieceConfig = board.getPieceAtCoordinates(srcCoors.row, srcCoors.col);
    if (pieceConfig == null) {
      return new HashSet<>();
    }
    return EngineMoves.computeValidMovesForPiece(board, pieceConfig);
  }

  //--------------------------- Getter and Setter
  public PieceConfig[] getPieces() {
    return pieces;
  }

  public void setPieces(PieceConfig[] pieces) {
    this.pieces = pieces;
  }

  public Board getBoard() {
    return board;
  }

  public void setBoard(Board board) {
    this.board = board;
  }

  public Player getPlayerToMove() {
    return playerToMove;
  }

}
