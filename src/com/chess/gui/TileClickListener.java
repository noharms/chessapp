package com.chess.gui;

import com.chess.model.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class TileClickListener implements MouseListener {

  private final TilePanel tilePanel;
  private final BoardPanel boardPanel; // parent board containing the tile
  private final Coordinates coors;

  TileClickListener(TilePanel tilePanel, BoardPanel boardPanel, Coordinates coors) {
    this.tilePanel = tilePanel;
    this.boardPanel = boardPanel;
    this.coors = coors;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // let controller get relevant infos of that tile from data storage
    PieceConfig pieceConfig = boardPanel.controller.getPieceAtCoors(coors);
    Player playerToMove = boardPanel.controller.getPlayerToMove();

    // set user selected tiles in boardPanel
    if (isRightMouseButton(e)) {

      boardPanel.setSelectedSourceTile(null);
      boardPanel.setSelectedDestinationTile(null);
      boardPanel.setSelectedMove(null);

    } else if (isLeftMouseButton(e)) {

      TilePanel srcTile = boardPanel.getSelectedSourceTile();
      boardPanel.setSelectedDestinationTile(null);
      boardPanel.setSelectedMove(null);

      if (srcTile == null) {

        if (pieceConfig == null || pieceConfig.getPlayer() != playerToMove) {
          // do nothing, so srtTile and dstTile remain null
        } else {
          boardPanel.setSelectedSourceTile(tilePanel);
          System.out.println("selecting as src tile:" + ", r:" + coors.row + ", c: " + coors.col);
        }

      } else { // srcTile != null

        Coordinates srcCoors = boardPanel.getSelectedSourceTile().getTileCoors();
        List<Move> validMoves = new ArrayList<>(boardPanel.controller.getValidMoves(srcCoors));
        for (Move move : validMoves) {
          if (boardPanel.getSelectedSourceTile().getTileCoors().equals(move.getOldPos())
                  && coors.equals(move.getNewPos())) {
            boardPanel.setSelectedDestinationTile(tilePanel);
            boardPanel.setSelectedMove(new Move(move));
            System.out.println("selecting as dst tile:" + ", r:" + coors.row + ", c: " + coors.col);
            break;
          }
        }

      }
    }

    // apply move if src and dst tile selected
    if (boardPanel.getSelectedMove() != null) {
      boardPanel.controller.applySelectedMove();
    }

  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

}
