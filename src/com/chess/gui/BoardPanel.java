package com.chess.gui;

import com.chess.controller.Controller;
import com.chess.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.chess.gui.AppDimensions.BOARD_PANEL_DIMENSION;
import static com.chess.model.BoardUtils.K_DIM;
import static com.chess.model.BoardUtils.getLinIdx;

public class BoardPanel extends JPanel {

  Controller controller;

  private final List<TilePanel> boardTiles;
  private TilePanel selectedSourceTile = null;
  private TilePanel selectedDestinationTile = null;
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
    for (int r = 0; r < K_DIM; ++r) {
      for (int c = 0; c < K_DIM; ++c) {
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

  public TilePanel getSelectedDestinationTile() {
    return selectedDestinationTile;
  }

  public void setSelectedDestinationTile(TilePanel selectedDestinationTile) {
    this.selectedDestinationTile = selectedDestinationTile;
  }

  public Move getSelectedMove() {
    return selectedMove;
  }

  public void setSelectedMove(Move selectedMove) {
    this.selectedMove = selectedMove;
  }
}
