package com.chess.model;

import java.util.*;
import java.util.stream.Collectors;

import static com.chess.model.EngineMoves.computePotentialMovesForPieces;
import static com.chess.model.MovesBasic.*;
import static com.chess.model.Board.*;
import static com.chess.model.PieceConfigUtils.getPiecesSameColor;


public class EngineIsCheck {

  private EngineIsCheck() {
    throw new RuntimeException("Class is supposed to be used statically, i.e. without instantiating it.");
  }

  /**
   * White isCheck, if black has moved and any of black's pieces could hit the
   * white king with their next move.
   *
   * So, to check after a black move, whether or not white isCheck now, one needs
   * to compute the hypothetical next moves of all black figures.
   *
   * Note: a king can never pose check to another king, so we do not need to check
   * if the other king threatens the king under investigation.
   *
   * Note: for the question whether the opposite king is in check it is irrelevant
   *       if the piece threatening it could actually attack the king with a valid move !
   *       it suffices that there is a potential move, e.g. imagine you have
   *       moved a bishop inbetween an opposing rook and your king to guard it from check.
   *       if your bishop is threatening the opposing king from the new position
   *       this is check even though in fact you would not be allowed to move your
   *       bishop away due to the threat of the opposing rook.
   *
   * @return
   */
  public static Set<PieceConfig> isCheck(Board board, final PieceConfig[] allPieces, Player playerToMove) {

    Player playerLastMoved = (playerToMove.isBlack() ? Player.W : Player.B);
    Set<PieceConfig> piecesOfColorToMove = getPiecesSameColor(allPieces, playerToMove);
    Set<PieceConfig> piecesOfColorLastMoved = getPiecesSameColor(allPieces, playerLastMoved);
//    PieceConfig kingColorToMove = getKing(piecesOfColorToMove, playerToMove);
//    PieceConfig kingColorLastMoved = getKing(piecesOfColorLastMoved, playerLastMoved);

    // 0. compute potential new positions (not considering whether move would put own king into check)
    Map<PieceConfig, Set<Move>> piecesOfLastMovedColor2PotentialMoves =
            computePotentialMovesForPieces(board, piecesOfColorLastMoved);

    // 1.  compare king position with potential moves
    Set<PieceConfig> threateningPieces = new HashSet<>();
    for (Map.Entry<PieceConfig, Set<Move>> entry : piecesOfLastMovedColor2PotentialMoves.entrySet()) {
      PieceConfig pieceOfColorLastMoved = entry.getKey();
      if (pieceOfColorLastMoved.isKing()) {
        continue; // opposing king can never pose check
      }
      Set<Move> potentialMoves = entry.getValue();
      for (Move potentialMove : potentialMoves) {
        int row = potentialMove.getNewPos().row;
        int col = potentialMove.getNewPos().col;
        PieceConfig pieceAtTargetField = board.getPieceAtCoordinates(row, col);
        if (pieceAtTargetField != null
                && pieceAtTargetField.isKing()) {
          threateningPieces.add(pieceOfColorLastMoved);
          break; // other potential moves of this piece are irrelevant
        }
      }
    }

    return threateningPieces;
  }


}


