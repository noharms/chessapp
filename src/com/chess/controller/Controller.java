package com.chess.controller;

import com.chess.gui.MainFrame;
import com.chess.model.Coordinates;
import com.chess.model.Game;
import com.chess.model.GameUtils;
import com.chess.model.PieceConfig;

import java.util.Set;

public class Controller {

  // reference to game (actual memory is allocated in data model)
  Game game;
  MainFrame mainFrame;

  public Controller() {
    game = GameUtils.createGame();
  }

  //------------------- Model control
  public boolean tileIsOccupied(int row, int col) {
    return (game.getBoard().getPieceAtCoordinates(row, col) != null);
  }

  public PieceConfig getPieceAtCoors(int row, int col) {
    return game.getBoard().getPieceAtCoordinates(row, col);
  }

  public Set<Coordinates> getValidDestinations(int src_row, int src_col) {
    return game.computeValidDestinationCoors(src_row, src_col);
  }

  //------------------- GUI control
  // requests to model and update of gui once done
  public void visualiseGame() {
    if (mainFrame != null) {
      mainFrame.visualiseGame(game);
    }
  }


  //------------------- Getter and Setter
  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public MainFrame getMainFrame() {
    return mainFrame;
  }

  public void setMainFrame(MainFrame mainFrame) {
    this.mainFrame = mainFrame;
  }
}
