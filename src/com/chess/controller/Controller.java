package com.chess.controller;

import com.chess.gui.BoardPanel;
import com.chess.gui.MainFrame;
import com.chess.model.*;

import java.util.List;
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

  public PieceConfig getPieceAtCoors(Coordinates coors) {
    return game.getBoard().getPieceAtCoordinates(coors);
  }

  public Player getPlayerToMove() {
    return game.getPlayerToMove();
  }

  public Set<Coordinates> getValidDestinations(Coordinates srcCoors) {
    return game.computeValidDestinationCoors(srcCoors);
  }

  public Set<Move> getValidMoves(Coordinates srcCoors) {
    return game.computeValidMoves(srcCoors);
  }

  public void applySelectedMove() {
    BoardPanel boardPanel = mainFrame.getBoardPanel();
    Move selectedMove = boardPanel.getSelectedMove();
    if (selectedMove != null) {
      game.applyMove(selectedMove);
      boardPanel.setSelectedMove(null);
      boardPanel.setSelectedSourceTile(null);
      boardPanel.setSelectedDestinationTile(null);
    }
    mainFrame.visualiseGame(game);
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
