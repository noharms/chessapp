package com.chess.model;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class PieceConfigUtils {

  private PieceConfigUtils() {
    throw new RuntimeException("Class is supposed to be used statically, i.e. without instantiating it.");
  }

  public static Set<PieceConfig> getPiecesSameColor(PieceConfig[] pieces, Player player) {
    return Arrays.stream(pieces).
            filter(piece -> piece.getPlayer() == player).
            collect(Collectors.toSet());
  }

  public static PieceConfig getKing(Set<PieceConfig> pieces, Player player) {
    for (PieceConfig piece : pieces) {
      if (piece.getPieceType() == Piece.K && piece.getPlayer() == player) {
        return piece;
      }
    }
    return null; // reaching here, means no king found
  }

}
