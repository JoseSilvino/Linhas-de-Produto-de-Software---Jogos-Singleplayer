/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lps_g3.tetris;

import java.util.Random;

/**
 *
 * @author Neto
 */
public class Shape {
    enum TetrisShape{
        NoShape,ZShape,SShape,IShape,TShape,SquareShape,LShape,MirrorLShape
    }
    private TetrisShape pieceShape;
    private int [][] coords;

    public Shape() {
        coords = new int[4][2];
        setShape(TetrisShape.NoShape);
    }
    public void setShape(TetrisShape shape){
        int [][][] tablecoords = new int[][][] {
            {{0,0},{0,0},{0,0},{0,0}},
            {{0,-1},{0,0},{-1,0},{-1,1}},
            {{0,-1},{0,0},{1,0},{1,1}},
            {{0,-1},{0,0},{0,1},{0,2}},
            {{-1,0},{0,0},{1,0},{0,1}},
            {{0,0},{1,0},{0,1},{1,1}},
            {{-1,-1},{0,-1},{0,0},{0,1}},
            {{1,-1},{0,-1},{0,0},{0,1}}};
        for (int i=0;i<4;i++) System.arraycopy(tablecoords[shape.ordinal()], 0, coords, 0, 4);
        pieceShape=shape;
    }
    private void setX(int index,int x){
        coords[index][0] = x;
    }
    private void setY(int index,int y){
        coords[index][1] = y;
    }
    public int x(int index){
        return coords[index][0];
    }
    public int y(int index){
        return coords[index][1];
    }
    TetrisShape getShape(){
        return pieceShape;
    }
    public void setRandomShape(){
        Random r = new Random();
        int x = Math.abs(r.nextInt())%7+1;
        setShape(TetrisShape.values()[x]);
    }
    Shape rotateLeft(){
        if(pieceShape==TetrisShape.SquareShape) return this;
        Shape res = new Shape();
        res.pieceShape = pieceShape;
        for(int i=0;i<4;i++){
            res.setX(i, y(i));
            res.setY(i, -x(i));
        }
        return res;
    }
    Shape rotateRight(){
        if(pieceShape==TetrisShape.SquareShape) return this;
        Shape res = new Shape();
        res.pieceShape = pieceShape;
        for(int i=0;i<4;i++){
            res.setX(i, -y(i));
            res.setY(i, x(i));
        }
        return res;
    }
    int minY() {

        int m = coords[0][1];

        for (int i = 0; i < 4; i++) {

            m = Math.min(m, coords[i][1]);
        }

        return m;
    }
}
