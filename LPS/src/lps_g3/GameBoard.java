/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lps_g3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Neto
 */
public class GameBoard extends JPanel{
    public boolean gameon;
    public boolean selected;
    public void gameOver(Graphics g){
        Font f = new Font("Arial",Font.BOLD,30);
        FontMetrics fm = getFontMetrics(f);
        this.setFont(f);
        String msg = "Game Over";
        g.drawString(msg,(this.getWidth()-fm.stringWidth(msg))/2, this.getHeight()/2);
        selected = false;
        gameon=false;
        //labelpos = 0;
    }

    public GameBoard() {
        super(null);
        setFocusable(false);
        initBoard();
    }
    protected JLabel gameoptions[];
    public int labelpos;
    protected void initBoard(){
        selected = false;
        gameon=false;
        gameoptions = new JLabel[3];
        this.setSize(400, 400);
        this.setPreferredSize(new Dimension(400,400));
        this.setBackground(Color.black);
        labelpos=0;
        gameoptions = new JLabel[3];
        JLabel Welcome = new JLabel("Welcome");
        String [] gamestr = {"Snake","Tetris","Minesweeper"};
        Font f = new Font("Arial",Font.BOLD,15);
        FontMetrics fm = getFontMetrics(f);
        Font f2 = new Font("Times",Font.BOLD,25);
        FontMetrics fm2 = getFontMetrics(f2);
        Welcome.setFont(f2);
        Welcome.setForeground(Color.white);
        Welcome.setBounds((getWidth()-fm2.stringWidth("Welcome"))/2, getHeight()/4 + fm2.getHeight(),fm2.stringWidth("Welcome") , fm2.getHeight());
        this.add(Welcome);
        for (int i=0;i<3;i++){
            gameoptions[i] = new JLabel(gamestr[i]);
            gameoptions[i].setForeground(Color.white);
            gameoptions[i].setFont(f);
            gameoptions[i].setBounds((getWidth()-fm.stringWidth(gamestr[i]))/2, getHeight()/2 + fm.getHeight()*i+10,fm.stringWidth(gamestr[i]) , fm.getHeight());
            this.add(gameoptions[i]);
        }
        gameoptions[labelpos].setForeground(Color.BLUE);
        this.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                gameoptions[labelpos].setForeground(Color.BLUE);
                if (key == KeyEvent.VK_UP){
                    gameoptions[labelpos].setForeground(Color.white);
                    labelpos--;
                    if(labelpos<0) labelpos = 2;
                }else if(key == KeyEvent.VK_DOWN){
                    gameoptions[labelpos].setForeground(Color.white);
                    labelpos++;
                    if(labelpos>2) labelpos=0;
                    
                }
                gameoptions[labelpos].setForeground(Color.BLUE);
                if(key == KeyEvent.VK_ENTER){
                    gameoptions[labelpos].setForeground(Color.white);
                    labelpos++;
                    selected=true;
                    gameoptions[0].setForeground(Color.blue);
                }
                repaint();
            }
            
        });
    }

    
    public void startGame() {
        this.gameon=true;
        this.selected=false;
        initBoard();
    }
}
