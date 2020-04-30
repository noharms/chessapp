package com.chess.gui;

import java.awt.*;

public class AppDimensions {

  public static final Dimension MAINWINDOW_FRAME_DIMENSION = new Dimension(600, 600);
  public static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400,400);
  public static final Dimension TILE_PANEL_DIMENSION = new Dimension(30, 30);

  private AppDimensions() {
    throw new RuntimeException("Class is meant to be static. No instances designated.");
  }
}
