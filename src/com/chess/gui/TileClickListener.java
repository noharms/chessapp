package com.chess.gui;

import com.chess.model.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
    PieceConfig pieceOnThisTile = boardPanel.controller.getPieceAtCoors(coors);
    Player playerToMove = boardPanel.controller.getPlayerToMove();

    // set user selected tiles in boardPanel
    if (isRightMouseButton(e)) {

      boardPanel.setSelectedSourceTile(null);
      boardPanel.setValidMovesForSelectedPiece(null);
      boardPanel.setSelectedMove(null);

    } else if (isLeftMouseButton(e)) {

      boardPanel.setSelectedMove(null);

      if (boardPanel.getSelectedSourceTile() == null) {

        if (pieceOnThisTile == null || pieceOnThisTile.getPlayer() != playerToMove) {
          boardPanel.setValidMovesForSelectedPiece(null);
        } else {
          boardPanel.setSelectedSourceTile(tilePanel);
          Coordinates srcCoors = boardPanel.getSelectedSourceTile().getTileCoors();
          boardPanel.setValidMovesForSelectedPiece(boardPanel.controller.getValidMoves(srcCoors));
          System.out.println("selecting as src tile:" + ", r:" + coors.row + ", c: " + coors.col);
          System.out.println("--> we have " + boardPanel.getValidMovesForSelectedPiece().size() + " valid moves.");
        }

      } else { // srcTile != null

        for (Move move : boardPanel.getValidMovesForSelectedPiece()) {
          if (boardPanel.getSelectedSourceTile().getTileCoors().equals(move.getOldPos())
                  && coors.equals(move.getNewPos())) {
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

    boardPanel.controller.visualiseGame(); // every click leads to new rendering of board
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
