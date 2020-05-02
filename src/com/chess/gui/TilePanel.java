package com.chess.gui;

import com.chess.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import static com.chess.gui.AppDimensions.TILE_PANEL_DIMENSION;
import static com.chess.model.BoardUtils.getColFromLinIdx;
import static com.chess.model.BoardUtils.getRowFromLinIdx;

public class TilePanel extends JPanel {

  private final int tileID; // replace by Enum 0 to 63

  private final BoardPanel parentBoardPanel;

  private final Coordinates tileCoors;

  public static final String PATH_TO_PIECEICONS = ".\\resources\\standard\\";

  private static final Color LIGHT_TILE_COLOR = Color.decode("#FFFACD");
  private static final Color DARK_TILE_COLOR = Color.decode("#593E1A");

  public TilePanel(final BoardPanel boardPanel,
                   final int tileID) {
    super(new GridBagLayout());
    this.tileID = tileID;
    parentBoardPanel = boardPanel;
    int row = getRowFromLinIdx(tileID);
    int col = getColFromLinIdx(tileID);
    tileCoors = new Coordinates(row, col);
    setPreferredSize(TILE_PANEL_DIMENSION);
    fillTileColor();
    addMouseListener(new TileClickListener(this, boardPanel, tileCoors));
    validate();
  }

  public void draw(Board board) {
    fillTileColor();

    int row = tileID / 8;
    int col = tileID % 8;
    PieceConfig pieceConfig = board.getPieceAtCoordinates(row, col);
    addPieceIcon(pieceConfig);

    addLegalMoveMarker();

    validate();
    repaint();
  }

  void fillTileColor() {
    int row = tileID / 8;
    int col = tileID % 8;
    if (row % 2 == 0) {
      setBackground(col % 2 == 0 ? LIGHT_TILE_COLOR : DARK_TILE_COLOR);
    } else {
      setBackground(col % 2 == 0 ? DARK_TILE_COLOR : LIGHT_TILE_COLOR);
    }
  }

  private void addPieceIcon(PieceConfig pieceConfig) {
    this.removeAll();
    if (pieceConfig == null) {
      return;
    }
    final String fileExtension = ".gif";
    String pathToIcon = PATH_TO_PIECEICONS
            + (pieceConfig.getPlayer().isWhite() ? "W" : "B")
            + pieceConfig.getPieceType().toString()
            + fileExtension;
    try {
      //final File file = new File("C:\\Users\\Enno\\IdeaProjects\\chessapp\\resources\\standard\\BP.gif");
//      final File file = new File(".\\resources\\standard\\BP.gif"); // after marking dir as resource
      final File file = new File(pathToIcon);
      final BufferedImage image = ImageIO.read(file);
      this.add(new JLabel(new ImageIcon(image)));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void addLegalMoveMarker() {
    if (parentBoardPanel.isShowLegalMoves()) {
      Set<Move> validMovesForSelectedPiece = parentBoardPanel.getValidMovesForSelectedPiece();
      if (validMovesForSelectedPiece == null) {
        return;
      }
      for (Move move : validMovesForSelectedPiece) {
        if (this.getTileCoors().equals(move.getNewPos())) {
          try {
            final File file = new File(".\\resources\\green_dot.png");
            final BufferedImage image = ImageIO.read(file);
            this.add(new JLabel(new ImageIcon(image)));
            add(new JLabel());
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
  }



  public Coordinates getTileCoors() {
    return tileCoors;
  }

}
