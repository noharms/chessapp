package com.chess;

import com.chess.controller.Controller;
import com.chess.gui.MainFrame;

public class ChessApp {

  public static void main(String[] args) {

    Controller controller = new Controller();
    MainFrame mainFrame = new MainFrame(controller);
    controller.setMainFrame(mainFrame);
    controller.visualiseGame();

  }

}
