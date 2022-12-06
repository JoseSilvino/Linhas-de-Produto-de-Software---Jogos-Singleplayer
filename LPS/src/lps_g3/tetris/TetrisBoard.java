/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lps_g3.tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import lps_g3.GameBoard;

/**
 *
 * @author Neto
 */
public class TetrisBoard extends GameBoard{

    public TetrisBoard() {
        super();
    }
    JLabel status;
    Thread thread;
    boolean fallingfinished;
    boolean paused;
    int linesfinished = 0;
    int curx,cury;
    Shape curpiece;
    Shape.TetrisShape[] board;
    int BoardWidth=20,BoardHeight=20;
    int period=300;
    @Override
    protected void initBoard() {
        if(gameon){
            this.setSize(400, 400);
            this.setPreferredSize(new Dimension(400,400));
            this.setBackground(Color.black);
            status = new JLabel("Tetris");
            addKeyListener(new KeyAdapter(){
                @Override
                public void keyPressed(KeyEvent e) {
                    if(curpiece.getShape()==Shape.TetrisShape.NoShape) return;
                    int key = e.getKeyCode();
                    switch (key) {

                    case KeyEvent.VK_P -> pause();
                    case KeyEvent.VK_LEFT -> tryMove(curpiece, curx - 1, cury);
                    case KeyEvent.VK_RIGHT -> tryMove(curpiece, curx + 1, cury);
                    case KeyEvent.VK_DOWN -> tryMove(curpiece.rotateRight(), curx, cury);
                    case KeyEvent.VK_UP -> tryMove(curpiece.rotateLeft(), curx, cury);
                    case KeyEvent.VK_SPACE -> dropDown();
                    case KeyEvent.VK_D -> LineDown();
                }
                }

            });
            curpiece = new Shape();
            board = new Shape.TetrisShape[BoardWidth*BoardHeight];
            clear();
            newPiece();
            thread = new Thread(){
                @Override
                public void run() {
                    while(true){
                        if(gameon){
                            update();
                            repaint();
                        }
                        try {
                            Thread.sleep(period);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(TetrisBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
              
            };
            status.setBounds(0, 0, 30, 15);
            add(status);
            thread.start();
        }
    }
    private void dropDown(){
        int nY = cury;
        while(nY<BoardHeight){
            if(!tryMove(curpiece, curx, nY+1)) break;
            nY++;
        }
        pieceDropped();
    }
    private void pause(){
        paused = !paused;
        if(paused) status.setText("Paused");
        else status.setText(String.valueOf(linesfinished));
        repaint();
    }
    private void update(){
        if(paused) return;
        if(fallingfinished){
            fallingfinished = false;
            newPiece();
        }else{
            LineDown();
        }
    }
    private boolean tryMove(Shape newpiece,int newX,int newY){
        for(int i=0;i<4;i++){
            int x = newX + newpiece.x(i);
            int y = newY + newpiece.y(i);
            if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight) return false;
            if(shapeAt(x, y)!=Shape.TetrisShape.NoShape) return false;
        }
        curpiece = newpiece;
        curx = newX;
        cury = newY;
        repaint();
        return true;
    }
    private void newPiece(){
        curpiece.setRandomShape();
        curx = BoardWidth/2 +1;
        cury = 0 + Math.abs(curpiece.minY());
        if(!tryMove(curpiece, curx, cury)){
            curpiece.setShape(Shape.TetrisShape.NoShape);
            gameon = false;
        }
    }
    private void LineDown(){
        if(!tryMove(curpiece, curx, cury+1)) pieceDropped();
    }
    
    private void pieceDropped(){
        for(int i=0;i<4;i++){
            int x = curx + curpiece.x(i);
            int y = cury + curpiece.y(i);
            board[(y*BoardWidth) + x] = curpiece.getShape();
        }
        removeFullLines();
        if(!fallingfinished) newPiece();
    }
    private void removeFullLines(){
        int nfulllines = 0;
        for(int i=BoardHeight-1;i>=0;i--){
            boolean isfull = true;
            for(int j=0;j<BoardWidth;j++){
                if(shapeAt(j, i)==Shape.TetrisShape.NoShape){
                    isfull = false;
                    break;
                }
            }
            if(isfull){
                nfulllines++;
                for(int k=i;k>0;k--) for(int j=0;j<BoardWidth;j++) board[(k*BoardWidth)+j] = shapeAt(j, k-1);
            }
        }
        if(nfulllines>0){
            linesfinished +=  nfulllines;
            status.setText(String.valueOf(linesfinished));
            fallingfinished = true;
            curpiece.setShape(Shape.TetrisShape.NoShape);
        }
    }
    private void clear(){
        for(int i=0;i<BoardWidth*BoardHeight;i++) board[i] = Shape.TetrisShape.NoShape;
    }
    private int squareWidth(){
        return (int) getSize().getWidth()/BoardWidth;
    }
    private int squareHeight(){
        return (int) getSize().getHeight()/BoardHeight;
    }
    private Shape.TetrisShape shapeAt(int x,int y){
        return board[(y*BoardWidth) + x];
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(gameon) draw(g);
        else gameOver(g);
    }
    private void draw(Graphics g){
        Dimension size = getSize();
        int top = (int) size.getHeight()-BoardHeight*squareHeight();
        for(int i=0;i<BoardHeight;i++){
            for(int j=0;j<BoardWidth;j++){
                Shape.TetrisShape shape = shapeAt(j, i);
                if(shape!= Shape.TetrisShape.NoShape){
                    drawSquare(g,j*squareWidth(),(i*squareHeight()),shape);
                }
            }
        }
        if(curpiece.getShape()!=Shape.TetrisShape.NoShape)
            for(int i=0;i<4;i++){
                int x = curx + curpiece.x(i);
                int y = cury + curpiece.y(i);
                drawSquare(g, x*squareWidth(), y*squareHeight(), curpiece.getShape());
            }
    }
    private void drawSquare(Graphics g,int x,int y, Shape.TetrisShape shape){
        Color colors[] = {Color.white,Color.red,Color.blue,Color.gray,Color.pink,Color.yellow,Color.GREEN,Color.orange};
        Color color = colors[shape.ordinal()];
        g.setColor(color);
        g.fillRect(x+1, y+1, squareWidth()-2, squareHeight()-2);
        g.setColor(color.brighter());
        g.drawLine(x, y+squareHeight()-1, x, y);
        g.drawLine(x, y, x+squareWidth()-1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,x + squareWidth() - 1, y + 1);
    }
    
    
    
}
