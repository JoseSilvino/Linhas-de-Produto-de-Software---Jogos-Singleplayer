/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lps_g3;

import lps_g3.snake.SnakeBoard;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import lps_g3.mines.MinesBoard;
import lps_g3.tetris.TetrisBoard;

/**
 *
 * @author Neto
 */
public class Main extends JFrame{

    public Main(){
        super("G3");
        init_components();
    }
    GameBoard board;
    BufferedImage body;
    
    int game;
    //0 = no game, 1 = snake, 2 = tetris, 3 = minesweeper
    GameBoard[] games = {new GameBoard(),new SnakeBoard(),new TetrisBoard(),new MinesBoard()};
    private void init_components(){
        setFocusable(true);
        game = 0;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        board = games[game];
        this.setContentPane(board);
        board.setFocusable(true);
        if(board.getKeyListeners().length>0) this.addKeyListener(board.getKeyListeners()[0]);
        Thread t = new Thread(){
            @Override
            public void run() {
                while(true){
                    
                    if(board.selected && !board.gameon){
                        game = board.labelpos;
                        changeBoard(); 
                    }
                    else if(!board.gameon){
                        backtoChoice();
                    }
                    
                }
            }
            
        };
        t.start();
        pack();
        setResizable(false);
    }
    private void backtoChoice(){
        game=0;
        games[game].selected = false;
        //games[game].labelpos=0;
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        changeBoard();
    }
    private void changeBoard(){
        board.setFocusable(false);
        board = games[game];
        if(game!=0) board.startGame();
        board.setFocusable(true);
        setContentPane(board);
        if(getKeyListeners().length>0) removeKeyListener(getKeyListeners()[0]);
        if(board.getKeyListeners().length>0) addKeyListener(board.getKeyListeners()[0]);
        if(board.getMouseListeners().length>0) addMouseListener(board.getMouseListeners()[0]);
        repaint();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
                new Main().setVisible(true);
            }
            
        });
    }
    
}
