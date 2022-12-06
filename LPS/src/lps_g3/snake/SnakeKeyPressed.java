/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package lps_g3.snake;

/**
 *
 * @author Neto
 */
public enum SnakeKeyPressed {
    Left(-20,0),Up(0,-20),Right(20,0),Down(0,20);
    private int x,y;

    public static SnakeKeyPressed getDown() {
        return Down;
    }

    public static SnakeKeyPressed getLeft() {
        return Left;
    }

    public static SnakeKeyPressed getRight() {
        return Right;
    }

    public static SnakeKeyPressed getUp() {
        return Up;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    private SnakeKeyPressed(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
}
