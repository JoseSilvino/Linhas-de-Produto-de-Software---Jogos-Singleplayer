/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lps_g3;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Neto
 */
public class Sprite {
    public BufferedImage img;
    public int x;
    public int y;
    protected int height,width;

    public int getHeight() {
        return height;
    }

    public BufferedImage getImg() {
        return img;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Sprite(BufferedImage img, int x, int y) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.height = img.getHeight(null);
        this.width = img.getWidth(null);
    }

    public Sprite() {
    }
    public Rectangle getRect(){
        return new Rectangle(x,y,width,height);
    }
    
}
