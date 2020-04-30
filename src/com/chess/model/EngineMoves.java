package com.chess.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.chess.model.BoardUtils.*;
import static com.chess.model.EngineIsCheck.isCheck;
import static com.chess.model.MovesBasic.*;

// TODO outsource the move computations that are specific to the piece
public class EngineMoves {

  private EngineMoves() {
    throw new RuntimeException("Class is supposed to be used statically, i.e. without instantiating it.");
  }

  /**
   * Note: assuming board is consistent with the piecesToMove (TODO: check that ?)
   *
   * @param piecesToMove
   * @param board
   * @return
   */
  public static Map<PieceConfig, Set<Move>> computePotentialMovesForPieces(
          Board board,
          Set<PieceConfig> piecesToMove
  ) {
    Map<PieceConfig, Set<Move>> piece2moves = new HashMap<>();
    for (PieceConfig piece : piecesToMove) {
      Set<Move> moves = computePotentialMovesForPiece(board, piece);
      piece2moves.put(piece, moves);
    }
    return piece2moves;
  }

  private static Set<Move> computePotentialMovesForPiece(Board board, PieceConfig pieceToMove) {
    if (pieceToMove.isPawn()) {
      return computePotentialMovesForPawn(board, pieceToMove);
    } else if (pieceToMove.isRook()) {
      return computePotentialMovesForRunners(board, pieceToMove, K_BASIC_MOVES_ROOK);
    } else if (pieceToMove.isKnight()) {
      return computePotentialMovesForKnightOrKing(board, pieceToMove, K_BASIC_MOVES_KNIGHT);
    } else if (pieceToMove.isBishop()) {
      return computePotentialMovesForRunners(board, pieceToMove, K_BASIC_MOVES_BISHOP);
    } else if (pieceToMove.isQueen()) {
      return computePotentialMovesForRunners(board, pieceToMove, K_BASIC_MOVES_QUEEN);
    } else if (pieceToMove.isKing()) {
      return computePotentialMovesForKnightOrKing(board, pieceToMove, K_BASIC_MOVES_KING);
    } else {
      throw new Error("Unknown piece type. " + pieceToMove.toString());
    }
  }


  /**
   * Pawn has three basic moves:
   *
   * move one row up/down
   * attack one diagonal up/down left, if occupied by enemy
   * attack one diagonal up/down right, if occupied by enemy
   *
   * And three special moves:
   *
   * move two row up/down, if still in starting position
   * attack one diagonal up/down left, if enemy pawn made two step across target field
   * attack diagonal up/down right, if enemy pawn made two step across target field
   *
   *
   * @param pieceToMove
   * @return
   */
  private static Set<Move> computePotentialMovesForPawn(Board board, PieceConfig pieceToMove) {
    Player playerToMove = pieceToMove.getPlayer();
    Coordinates oldCoors = new Coordinates(pieceToMove.getRow(), pieceToMove.getCol());
    Coordinates newCoors;
    Set<Move> potentialMoves = new HashSet<>();
    int pawnsMoveDirection =
            (pieceToMove.getPlayer().isWhite() ? K_DIRECTION_WHITE : K_DIRECTION_BLACK); // white moves upwards (row index decreases)
    // 1. move one up/down
    Coordinates move1 = new Coordinates(pawnsMoveDirection, 0);
    newCoors = Coordinates.add(oldCoors, move1);
    if (newCoors.isInsideBoard()) {
      PieceConfig pieceOnNewPos = board.getPieceAtCoordinates(newCoors.row, newCoors.col);
      if(pieceOnNewPos == null) {
        potentialMoves.add(new Move(newCoors, oldCoors,false));
      }
    }
    // 2. attack diagonal to smaller column side
    Coordinates attack1 = new Coordinates(pawnsMoveDirection, -1);
    newCoors = Coordinates.add(oldCoors, attack1);
    if (newCoors.isInsideBoard()) {
      PieceConfig pieceOnNewPos = board.getPieceAtCoordinates(newCoors.row, newCoors.col);
      if (pieceOnNewPos != null && pieceOnNewPos.getPlayer() != pieceToMove.getPlayer()) {
        potentialMoves.add(new Move(newCoors, oldCoors, true));
      }
    }
    // 3. attack diagonal to greater column side
    Coordinates attack2 = new Coordinates(pawnsMoveDirection, 1);
    newCoors = Coordinates.add(oldCoors, attack2);
    if (newCoors.isInsideBoard()) {
      PieceConfig pieceOnNewPos = board.getPieceAtCoordinates(newCoors.row, newCoors.col);
      if (pieceOnNewPos != null && pieceOnNewPos.getPlayer() != pieceToMove.getPlayer()) {
        potentialMoves.add(new Move(newCoors, oldCoors, true));
      }
    }
    // 4. move 2 up
    int rowPawnsStart = playerToMove.isBlack() ? K_ROW_BLACK_PAWNS_START : K_ROW_WHITE_PAWNS_START;
    if (pieceToMove.getRow() == rowPawnsStart) {
      Coordinates move2 = new Coordinates(pawnsMoveDirection * 2, 0);
      newCoors = Coordinates.add(oldCoors, move2);
      if (newCoors.isInsideBoard()) {
        // check one step is OK
        PieceConfig pieceOnMiddleField = board.getPieceAtCoordinates(newCoors.row - pawnsMoveDirection, newCoors.col);
        if(pieceOnMiddleField == null) {
          // check two step is OK
          PieceConfig pieceOnNewPos = board.getPieceAtCoordinates(newCoors.row, newCoors.col);
          if (pieceOnNewPos == null) {
            potentialMoves.add(new Move(newCoors, oldCoors, false));
          }
        }
      }
    }
    // 5. and 6. en passant attacks to left or right
    int rowToWaitForEnpassant =
            playerToMove.isBlack() ? K_ROW_BLACK_PAWNS_WAIT_FOR_ENPASSANT : K_ROW_WHITE_PAWNS_WAIT_FOR_ENPASSANT;
    int rowOpposingPawnsStart =
            playerToMove.isBlack() ? K_ROW_WHITE_PAWNS_START : K_ROW_BLACK_PAWNS_START;
    if (pieceToMove.getRow() == rowToWaitForEnpassant) {
      if (pieceToMove.getCol() > 0) {
        PieceConfig leftNeighbor = board.getBoardMatrix()[pieceToMove.getRow()][pieceToMove.getCol() - 1];
        // check if leftNeighbor made en passant movement
        if (leftNeighbor != null &&
                leftNeighbor.isPawn() && leftNeighbor.getPlayer() != playerToMove &&
                leftNeighbor.getPrevRow() == rowOpposingPawnsStart) {
          Coordinates attack3 = new Coordinates(pawnsMoveDirection, -1);
          newCoors = Coordinates.add(oldCoors, attack3);
          // if two-up move of the left neighbor was correct, no need to check if other piece is sitting at newcoors
          potentialMoves.add(new Move(newCoors, oldCoors, true, true));
        }
      }
      if (pieceToMove.getCol() < 7) {
        PieceConfig rightNeighbor = board.getBoardMatrix()[pieceToMove.getRow()][pieceToMove.getCol() + 1];
        // check if right neighbor made en passent movement
        if (rightNeighbor != null &&
                rightNeighbor.isPawn() && rightNeighbor.getPlayer() != playerToMove &&
                rightNeighbor.getPrevRow() == rowOpposingPawnsStart) {
          Coordinates attack4 = new Coordinates(pawnsMoveDirection, 1);
          newCoors = Coordinates.add(oldCoors, attack4);
          potentialMoves.add(new Move(newCoors, oldCoors, true, true));
        }
      }
    }
    return potentialMoves;
  }
  private static Set<Move> computePotentialMovesForKnightOrKing(
          Board board,
          PieceConfig pieceToMove,
          Set<Coordinates> basicMoves) {
    Coordinates oldCoors = new Coordinates(pieceToMove.getRow(), pieceToMove.getCol());
    Set<Move> potentialMoves = new HashSet<>();
    for (Coordinates basicMove : basicMoves) {
      Coordinates newCoors = Coordinates.add(oldCoors, basicMove);
      if (newCoors.isInsideBoard()) {
        boolean occupiedBySameColor = false;
        boolean occupiedByOtherColor = false;
        PieceConfig pieceOnNewPos = board.getPieceAtCoordinates(newCoors.row, newCoors.col);
        if (pieceOnNewPos != null) {
          if (pieceOnNewPos.getPlayer() == pieceToMove.getPlayer()) {
            occupiedBySameColor = true;
          } else {
            occupiedByOtherColor = true;
          }
        }
        if (!occupiedBySameColor) {
          potentialMoves.add(new Move(newCoors, oldCoors, occupiedByOtherColor));
        }
      }
    }
    return potentialMoves;
  }

  /**
   * Let's call the pieces that can move 1 to 8 fields in a direction "Runners": rook, bishop, queen
   *
   * @param pieceToMove
   * @param board
   * @param basicMoves
   * @return
   */
  private static Set<Move> computePotentialMovesForRunners(
          Board board,
          PieceConfig pieceToMove,
          Set<Coordinates> basicMoves) {
    Coordinates oldCoors = new Coordinates(pieceToMove.getRow(), pieceToMove.getCol());
    Set<Move> potentialMoves = new HashSet<>();
    for (Coordinates basicMove : basicMoves) {
      Coordinates newCoors = Coordinates.add(oldCoors, basicMove);
      boolean newPosOccupiedBySameColor = false;
      boolean newPosOccupiedByOtherColor = false;
      // in contrast to knightMoves, there are potentially multiple newPositions for each multiple of the basic move
      // once the new position is occupied, no need to compute further positions beyond that
      while (newCoors.isInsideBoard() &&
              !newPosOccupiedBySameColor && !newPosOccupiedByOtherColor) {
        PieceConfig pieceOnNewPos = board.getPieceAtCoordinates(newCoors.row, newCoors.col);
        if (pieceOnNewPos != null) {
          if (pieceOnNewPos.getPlayer() == pieceToMove.getPlayer()) {
            newPosOccupiedBySameColor = true;
          } else {
            newPosOccupiedByOtherColor = true;
          }
        }
        if (!newPosOccupiedBySameColor) {
          potentialMoves.add(new Move(newCoors, oldCoors, newPosOccupiedByOtherColor));
        }
        // update with further basic move
        newCoors = Coordinates.add(newCoors, basicMove);
      }
    }
    return potentialMoves;
  }


  /*  compute valid (=notPuttingOwnKingInCheck) moves

      1. compute the potential moves without caring whether the move is actually valid
         (a potential move can be invalid if it puts the own king into check)

      2. for each potential move, we have to compute the hypothetical board
         and check if in that board the own king is in check

  */
  public static Map<PieceConfig, Set<Move>> computeValidMovesForPieces(
          Board board,
          Set<PieceConfig> piecesToMove,
          Player playerToMove){
    // 0. compute potential new positions (not considering whether move would put own king into check)
    Map<PieceConfig, Set<Move>> piecesOfColorToMove2PotentialMoves =
            computePotentialMovesForPieces(board, piecesToMove);
    // 1. filter potential moves to valid moves
    //    (a move is valid, if afterwards the own king is not in check)
    Map<PieceConfig, Set<Move>> piecesOfColorToMove2ValidMoves = new HashMap<>();
    for (Map.Entry<PieceConfig, Set<Move>> entry : piecesOfColorToMove2PotentialMoves.entrySet()) {
      PieceConfig pieceOfColorToMove = entry.getKey();
      Set<Move> potentialMoves = entry.getValue();
      Set<Move> validMoves = new HashSet<>();
      for (Move hypotheticalMove : potentialMoves) {
        Board hypotheticalBoard = new Board(board, pieceOfColorToMove, hypotheticalMove);
        PieceConfig[] allPiecesAfterHypotheticalMove = hypotheticalBoard.buildPiecesArray();
        Set<PieceConfig> piecesThreateningKing = isCheck(hypotheticalBoard, allPiecesAfterHypotheticalMove, playerToMove);
        if (piecesThreateningKing.isEmpty()) {
          validMoves.add(hypotheticalMove);
        } else {
          // king is threatened ! hypothetical move is invalid and thus not copied to validMoves set
        }
      }
      if (!validMoves.isEmpty()) {
        piecesOfColorToMove2ValidMoves.put(pieceOfColorToMove, validMoves);
      }
    }
    return piecesOfColorToMove2ValidMoves;
  }

  public static Set<Coordinates> computeValidDestinationsForPiece(Board board, PieceConfig pieceConfig) {
    HashSet<PieceConfig> setOfOnlyOnePiece = new HashSet<>();
    setOfOnlyOnePiece.add(pieceConfig);
    Map<PieceConfig, Set<Move>> pieceConfig2Moves =
            computeValidMovesForPieces(board, setOfOnlyOnePiece, pieceConfig.getPlayer());
    Set<Coordinates> validDestinationCoors = new HashSet<>();
    for (Move move : pieceConfig2Moves.get(pieceConfig)) {
      Coordinates coors = move.getNewPos();
      validDestinationCoors.add(coors);
    }
    return validDestinationCoors;
  }

}
