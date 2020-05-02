package com.chess.gui;

import com.chess.controller.Controller;
import com.chess.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.chess.gui.AppDimensions.BOARD_PANEL_DIMENSION;
import static com.chess.model.BoardUtils.K_DIM;
import static com.chess.model.BoardUtils.getLinIdx;


/**
 *  Index Convention for Board used here:
 *
 *           0    1    2    3   4   5    6  7    -  column ID
 *    0      w    b    w    b   w   b    w  b
 *    1      b    w   ...  BLACK ...
 *    2
 *    3
 *    4
 *    5
 *    6      w    b  ...  WHITE ...
 *    7      b    w   b    w    b   w    b  w
 *
 *  row ID
 *
 */
public class BoardPanel extends JPanel {

  Controller controller;

  private final List<TilePanel> boardTiles;

  private BoardDirection boardDirection = BoardDirection.NORMAL;

  private boolean showLegalMoves = true;
  private TilePanel selectedSourceTile = null;
  private Set<Move> validMovesForSelectedPiece = null;
  private Move selectedMove = null;

  public BoardPanel(Controller controller) {
    super(new GridLayout(8, 8)); // TODO: remove magic number 8

    this.controller = controller;

    boardTiles = new ArrayList<>();
    for (int i = 0; i < 8*8; ++i) {
      final TilePanel tilePanel = new TilePanel(this, i);
      boardTiles.add(tilePanel);
      this.add(tilePanel);
    }
    setPreferredSize(BOARD_PANEL_DIMENSION);
    validate();
  }

  public void visualiseBoard(Game game) {
    removeAll();
    Board board = game.getBoard();
    int r0, c0, r1, c1, step;
    if (boardDirection == BoardDirection.NORMAL) {
      r0 = 0;
      c0 = 0;
      r1 = K_DIM;
      c1 = K_DIM;
      step = 1;
    } else {
      r0 = K_DIM - 1;
      c0 = K_DIM - 1;
      r1 = -1;
      c1 = -1;
      step = -1;
    }
    for (int r = r0; r != r1; r += step) {
      for (int c = c0; c != c1; c += step) {
        TilePanel tilePanel = boardTiles.get(getLinIdx(r, c));
        tilePanel.draw(board);
        this.add(tilePanel);
      }
    }
    validate();
    repaint();
  }

  public TilePanel getTileAtLinIdx(int linIdx) {
    return boardTiles.get(linIdx);
  }

  public TilePanel getSelectedSourceTile() {
    return selectedSourceTile;
  }

  public void setSelectedSourceTile(TilePanel selectedSourceTile) {
    this.selectedSourceTile = selectedSourceTile;
  }

  public Move getSelectedMove() {
    return selectedMove;
  }

  public void setSelectedMove(Move selectedMove) {
    this.selectedMove = selectedMove;
  }

  public BoardDirection getBoardDirection() {
    return boardDirection;
  }

  public void setBoardDirection(BoardDirection boardDirection) {
    this.boardDirection = boardDirection;
  }

  public boolean isShowLegalMoves() {
    return showLegalMoves;
  }

  public void setShowLegalMoves(boolean showLegalMoves) {
    this.showLegalMoves = showLegalMoves;
  }

  public Set<Move> getValidMovesForSelectedPiece() {
    return validMovesForSelectedPiece;
  }

  public void setValidMovesForSelectedPiece(Set<Move> validMovesForSelectedPiece) {
    this.validMovesForSelectedPiece = validMovesForSelectedPiece;
  }
}
