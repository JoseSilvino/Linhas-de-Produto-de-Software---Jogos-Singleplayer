/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lps_g3.mines;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import lps_g3.GameBoard;

/**
 *
 * @author Neto
 */
public class MinesBoard extends GameBoard{

    public MinesBoard() {
        super();
    }
    
    private final int CellSize = 20;

    private final int Cover = 10;
    private final int MarkCELL = 10;
    private final int EmptyCELL = 0;
    private final int MineCELL = 9;
    private final int CoveredMINECELL = MineCELL + Cover;
    private final int MarkedMINECELL = CoveredMINECELL + MarkCELL;

    private final int MINEImg = 9;
    private final int COVERImg = 10;
    private final int MARKImg = 11;
    private final int WRONG_MARKImg = 12;

    private final int Mines = 45;
    private final int Rows = 19;
    private final int Cols = 20;
    private final int BoardWidth = Cols*CellSize;
    private final int BoardHeight = Rows*CellSize;
    private int [] board;
    private int minesLeft;
    private Image []imgs;
    private int allCells;
    private JLabel status;
    private boolean ingame;
    @Override
    protected void initBoard() {
        if(gameon){
            status = new JLabel(String.valueOf(minesLeft));
            status.setForeground(Color.white);
            status.setBounds(0,380,20,15);
            setPreferredSize(new Dimension(400,400));
            this.setSize(400, 400);
            this.setBackground(Color.black);
            imgs = new Image[13];
            for(int i=0;i<13;i++){
                String path = System.getProperty("user.dir") + "/src/image/" + i + ".png";
                imgs[i] = (new ImageIcon(path)).getImage();
            }
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    mousePress(e);
                }
                
            });
            newGame();
            this.add(status);
        }
    }
    private void mousePress(MouseEvent e) {
        int x = e.getX(),y = e.getY();
        int cCol = x/CellSize;
        int cRow = y/CellSize;
        boolean Repaint = false;
        int pos = (cRow*Cols)+cCol;
        if(!ingame) gameon=false;
        if((x<Cols*CellSize) && (y<Rows*CellSize)){
            if(e.getButton()==MouseEvent.BUTTON3){
                if(board[pos]>MineCELL){
                    Repaint = true;
                    if(board[pos]<=CoveredMINECELL){
                        if(minesLeft>0){
                            board[pos] += MarkCELL;
                            minesLeft--;
                            status.setText(String.valueOf(minesLeft));
                        }else{
                            status.setText("No Marks left");
                        }
                    }else{
                        board[pos] -= MarkCELL;
                        minesLeft++;
                        status.setText(String.valueOf(minesLeft));
                    }
                }
                
            }else{
                if(board[pos]>CoveredMINECELL) return;
                if(board[pos]>MineCELL && board[pos]< MarkedMINECELL){
                    board[pos] -= Cover;
                    Repaint=true;
                    if(board[pos]==MineCELL) ingame=false;
                    if(board[pos]==EmptyCELL) findEmpty(pos);
                }
            }
            if(Repaint) repaint();
        }
        
    }
    private void newGame(){
        int cell;
        Random r = new Random();
        ingame=true;
        minesLeft = Mines;
        allCells = Cols*Rows;
        board = new int[allCells];
        for(int i=0;i<allCells;i++) board[i] = Cover;
        status.setText(String.valueOf(minesLeft));
        int i=0;
        while(i<Mines){
            int pos = r.nextInt(0, allCells);
            if(pos<allCells && (board[pos]!=CoveredMINECELL)){
                int ccol = pos % Cols;
                board[pos] = CoveredMINECELL;
                i++;
                if(ccol>0){
                    cell = pos-1 - Cols;
                    if(cell>=0) if(board[cell]!=CoveredMINECELL) board[cell]++;
                    cell = pos-1;
                    if(cell>=0) if(board[cell]!=CoveredMINECELL) board[cell]++;
                    cell = pos+Cols-1;
                    if(cell<allCells) if(board[cell]!=CoveredMINECELL) board[cell]++;
                }
                cell = pos-Cols;
                if(cell>=0) if(board[cell]!=CoveredMINECELL) board[cell]++;
                cell = pos+Cols;
                if(cell<allCells) if(board[cell]!=CoveredMINECELL) board[cell]++;
                
                if(ccol<(Cols-1)){
                    cell = pos-Cols+1;
                    if(cell>=0) if(board[cell]!=CoveredMINECELL) board[cell]++;
                    cell = pos+Cols+1;
                    if(cell<allCells) if(board[cell]!=CoveredMINECELL) board[cell]++;
                    cell = pos+1;
                    if(cell<allCells) if(board[cell]!=CoveredMINECELL) board[cell]++;
                }
            }
        }
    }
    private void findEmpty(int j){
        int ccol = j%Cols;
        int cell;
        if(ccol>0){
            cell = j-Cols-1;
            if(cell>=0) if(board[cell]>MineCELL){
                board[cell]-=Cover;
                if(board[cell] == EmptyCELL) findEmpty(cell);
            }
            cell = j-1;
            if(cell>=0) if(board[cell]>MineCELL){
                board[cell]-=Cover;
                if(board[cell] == EmptyCELL) findEmpty(cell);
            }
            cell = j+Cols-1;
            if(cell<allCells) if(board[cell]>MineCELL){
                board[cell]-=Cover;
                if(board[cell] == EmptyCELL) findEmpty(cell);
            }
            
        }
        cell = j+Cols;
        if(cell<allCells) if(board[cell]>MineCELL){
            board[cell]-=Cover;
            if(board[cell] == EmptyCELL) findEmpty(cell);
        }
        if(ccol<(Cols-1)){
            cell = j-Cols+1;
            if(cell>=0) if(board[cell]>MineCELL){
                board[cell]-=Cover;
                if(board[cell] == EmptyCELL) findEmpty(cell);
            }
            cell = j+Cols+1;
            if(cell<allCells) if(board[cell]>MineCELL){
                board[cell]-=Cover;
                if(board[cell] == EmptyCELL) findEmpty(cell);
            }
            cell = j+1;
            if(cell<allCells) if(board[cell]>MineCELL){
                board[cell]-=Cover;
                if(board[cell] == EmptyCELL) findEmpty(cell);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int uncover=0;
        for (int i=0;i<Rows;i++){
            for(int j=0;j<Cols;j++){
                int cell = board[(i*Cols)+j];
                if(ingame && cell==MineCELL) ingame = false;
                if(!ingame){
                    if(cell==CoveredMINECELL) cell = MINEImg;
                    else if(cell==MarkedMINECELL) cell = MARKImg;
                    else if(cell > CoveredMINECELL) cell = WRONG_MARKImg;
                    else if(cell>MineCELL) cell = COVERImg;
                }else{
                    if(cell>CoveredMINECELL) cell = MARKImg;
                    else if(cell>MineCELL) {
                        cell = COVERImg;
                        uncover++;
                    }
                }
                g.drawImage(imgs[cell], j*CellSize, i*CellSize, this);
            }
        }
        if(uncover==0 && ingame){
            ingame = false;
            status.setText("Game Won");
        }else if(!ingame) status.setText("Game Lost");
    }
    
}







