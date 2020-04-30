package com.chess.gui;

import com.chess.controller.Controller;
import com.chess.model.Board;
import com.chess.model.Game;
import com.chess.model.PieceConfig;

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

  public void visualiseGame(Game game) {
    Board board = game.getBoard();
    for (int r = 0; r < K_DIM; ++r) {
      for (int c = 0; c < K_DIM; ++c) {
        PieceConfig pieceConfig = board.getPieceAtCoordinates(r, c);
        if (pieceConfig != null) {
          TilePanel tilePanel = boardTiles.get(getLinIdx(r, c));
          tilePanel.setPieceIcon(pieceConfig);
        }
      }
    }
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
}
