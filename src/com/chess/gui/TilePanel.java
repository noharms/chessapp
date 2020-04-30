package com.chess.gui;

import com.chess.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import static com.chess.gui.AppDimensions.TILE_PANEL_DIMENSION;
import static com.chess.model.BoardUtils.getColFromLinIdx;
import static com.chess.model.BoardUtils.getRowFromLinIdx;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class TilePanel extends JPanel {

  private final int tileID; // replace by Enum 0 to 63

  public static final String PATH_TO_PIECEICONS = ".\\resources\\standard\\";

  private static final Color LIGHT_TILE_COLOR = Color.decode("#FFFACD");
  private static final Color DARK_TILE_COLOR = Color.decode("#593E1A");

  public TilePanel(final BoardPanel boardPanel,
                   final int tileID) {
    super(new GridBagLayout());
    this.tileID = tileID;
    int row = getRowFromLinIdx(tileID);
    int col = getColFromLinIdx(tileID);
    setPreferredSize(TILE_PANEL_DIMENSION);
    assignTileColor();

    addMouseListener(new MouseListener() { // TODO: make named class instead of anonymous
      @Override
      public void mouseClicked(MouseEvent e) {
        // let controller get relevant infos of that tile from data storage
        PieceConfig pieceConfig = boardPanel.controller.getPieceAtCoors(row, col);
        Player playerToMove = boardPanel.controller.getGame().getPlayerToMove();
        Set<Coordinates> validDestinations = boardPanel.controller.getGame().computeValidDestinationCoors(row, col);

        if (isRightMouseButton(e)) {
          // right click ALWAYS deselects previous selection
          boardPanel.setSelectedSourceTile(null);
        } else if (isLeftMouseButton(e)) {
          // left click can select destination tile (if src tile is selected and suitable)
          // or it can select a src tile (if occupied by a tile of the player on move)
          TilePanel previouslySelectedTile = boardPanel.getSelectedSourceTile();
          if (previouslySelectedTile != null) { // possibly selecting destination tile
            // TODO: allow here only a selection, if the destination tile produces a valid move
            if (pieceConfig != null && pieceConfig.getPlayer() == playerToMove) {
              boardPanel.setSelectedSourceTile(TilePanel.this);
              System.out.println("selecting as src tile:" + ", r:" + row + ", c: " + col);
            }
            if (pieceConfig == null) { // and tile is possible destination src
              // set destination
            }
          } else { // selecting src tile, if piece of correct color is on it
            if (pieceConfig != null && pieceConfig.getPlayer() == playerToMove) {
              boardPanel.setSelectedSourceTile(TilePanel.this);
              System.out.println("selecting as src tile:" + ", r:" + row + ", c: " + col);
            } else {
              boardPanel.setSelectedSourceTile(null);
            }
          }
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
    });

    validate();
  }

  public void setPieceIcon(PieceConfig pieceConfig) {
    this.removeAll();
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
    revalidate();
  }

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
  private void assignTileColor() {
    int row = tileID / 8;
    int col = tileID % 8;
    if (row % 2 == 0) {
      setBackground(col % 2 == 0 ? LIGHT_TILE_COLOR : DARK_TILE_COLOR);
    } else {
      setBackground(col % 2 == 0 ? DARK_TILE_COLOR : LIGHT_TILE_COLOR);
    }
  }
}
