package com.chess.model;

import java.util.Map;
import java.util.Set;

import static com.chess.model.EngineIsCheck.isCheck;
import static com.chess.model.EngineMoves.computeValidMovesForPieces;
import static com.chess.model.PieceConfigUtils.getKing;
import static com.chess.model.PieceConfigUtils.getPiecesSameColor;

public class EngineIsMate {

  private EngineIsMate() {
    throw new RuntimeException("Class is supposed to be used statically, i.e. without instantiating it.");
  }

  public static boolean isMate(Board board, final PieceConfig[] allPieces, Player playerToMove) {

    Player playerLastMoved = (playerToMove.isBlack() ? Player.W : Player.B);
    Set<PieceConfig> piecesOfColorToMove = getPiecesSameColor(allPieces, playerToMove);
    Set<PieceConfig> piecesOfColorLastMoved = getPiecesSameColor(allPieces, playerLastMoved);
    PieceConfig kingColorToMove = getKing(piecesOfColorToMove, playerToMove);
    PieceConfig kingColorLastMoved = getKing(piecesOfColorLastMoved, playerLastMoved);

    Set<PieceConfig> piecesThreateningKingOfColorToMove = isCheck(board, allPieces, playerToMove);
    boolean isColorToMoveInCheck = !piecesThreateningKingOfColorToMove.isEmpty();
    if (!isColorToMoveInCheck) {
      return false;
    }
    // reaching here, means colorToMove is in check
    // compute all allowed moves of colorToMove and check if there is one move
    // that would put colorToMove out of check
    Map<PieceConfig, Set<Move>> validMoves = computeValidMovesForPieces(board, piecesOfColorToMove);

    return validMoves.isEmpty();
  }

}
