package com.chess.gui;

import com.chess.controller.Controller;
import com.chess.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.chess.gui.AppDimensions.MAINWINDOW_FRAME_DIMENSION;

public class MainFrame extends JFrame {

  private Controller controller;

  private final JMenuBar menuBar;
  private final JMenu fileMenu;
  private final JMenuItem loadGameMenuItem;
  private final JMenuItem exitAppMenuItem;

  private final BoardPanel boardPanel;

  public MainFrame(Controller controller) {

    super("ChessApp");

    setVisible(true);
    setLayout(new BorderLayout());
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setSize(MAINWINDOW_FRAME_DIMENSION);

    // set controller reference, so that mainframe can send request to controller
    this.controller = controller;

    // set up menu bar
    menuBar = new JMenuBar();
    fileMenu =  new JMenu("File");

    loadGameMenuItem = new JMenuItem("Load game");
    loadGameMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("TODO: open file from pgn file.");
      }
    });
    fileMenu.add(loadGameMenuItem);

    exitAppMenuItem = new JMenuItem("Exit");
    exitAppMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });
    fileMenu.add(exitAppMenuItem);

    menuBar.add(fileMenu);
    setJMenuBar(menuBar);

    // set up board panel
    boardPanel = new BoardPanel(controller);
    add(boardPanel, BorderLayout.CENTER);

    validate();
  }

  public void visualiseGame(Game game) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        System.out.println("Game to visualise:");
        game.getBoard().print();
        boardPanel.visualiseBoard(game);
      }
    });
  }


  public BoardPanel getBoardPanel() {
    return boardPanel;
  }
}
