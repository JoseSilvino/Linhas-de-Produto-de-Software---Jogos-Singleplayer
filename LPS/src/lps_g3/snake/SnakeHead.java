/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Singleton.java to edit this template
 */
package lps_g3.snake;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import lps_g3.Sprite;

/**
 *
 * @author Neto
 */
public class SnakeHead extends Sprite{
    
    private SnakeHead(){
            try {
                String parent = System.getProperty("user.dir") + "/src/image/head.png";    
                this.img = ImageIO.read(new File(parent));
                this.x = this.y = 0;
                this.height = img.getHeight();
                this.width = img.getWidth();
            } catch (IOException ex) {
                Logger.getLogger(SnakeHead.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public BufferedImage getHead() {
        return img;
    }
 
    public static SnakeHead getInstance() {
        return SnakeHeadHolder.INSTANCE;
    }
    
    private static class SnakeHeadHolder {

        private static final SnakeHead INSTANCE = new SnakeHead();
    }
}
