/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lps_g3.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.util.Random;
import lps_g3.GameBoard;
import lps_g3.Sprite;
/**
 *
 * @author Neto
 */
public class SnakeBoard extends GameBoard {
    SnakeHead head;
    ArrayList<Sprite> bodyparts;
    int bodysize = 2;
    public SnakeBoard() {
        super();
    }
    int xacc,yacc;
    ArrayList<Sprite>apples;
    BufferedImage appleimg,bodyimg;
    Random rand;
    int dec_s;
    @Override
    protected void initBoard(){
        if(gameon){
            try {
                this.setSize(400, 400);
                this.setPreferredSize(new Dimension(400,400));
                this.setBackground(Color.black);
                dec_s=0;
                rand = new Random();
                xacc = yacc = 0;
                apples = new ArrayList<>();
                String parent = System.getProperty("user.dir") + "/src/image/apple.png";
                appleimg = ImageIO.read(new File(parent));
                head = SnakeHead.getInstance();
                head.setX(this.getWidth()/2-head.getWidth());
                head.setY(this.getHeight()/2-head.getHeight());

                bodyparts = new ArrayList<>();
                parent = System.getProperty("user.dir") + "/src/image/body.png";
                bodyimg = ImageIO.read(new File(parent));
                for(int i=0;i<bodysize;i++){
                    bodyparts.add(new Sprite(bodyimg,head.getX()+(i+1)*head.getWidth(),head.getY()));
                }
                this.addKeyListener(new KeyAdapter(){
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if(e.getKeyCode()>=KeyEvent.VK_LEFT && e.getKeyCode()<=KeyEvent.VK_DOWN){
                            SnakeKeyPressed pressed =  SnakeKeyPressed.values()[e.getKeyCode()-KeyEvent.VK_LEFT];
                            xacc = pressed.getX();
                            yacc = pressed.getY();
                        }
                    }

                });
                setApples(3);
                Thread t = new Thread(){
                    @Override
                    public void run() {
                        while(gameon){
                                try {
                                    if((xacc!=0 || yacc!=0) && head.getRect().intersects(new Rectangle(35,35,getWidth()-70,getHeight()-70))){
                                        move();
                                    }else if((xacc!=0 || yacc!=0)){
                                        gameon=false;
                                        xacc = -xacc;
                                        yacc = -yacc;
                                        move();
                                    }
                                    for(int i=0;i<apples.size();i++){
                                        Sprite apple = apples.get(i);
                                        if(apple!=null){
                                            if(apple.getRect().intersects(head.getRect())){
                                                Sprite lb = bodyparts.get(bodyparts.size()-1);
                                                bodyparts.add(new Sprite(bodyimg,lb.x,lb.y));
                                                apples.remove(apple);
                                            }
                                        }
                                    }
                                    for(Sprite body:bodyparts){
                                        if(body.getRect().intersects(head.getRect())){
                                            gameon=false;
                                        }
                                    }
                                    repaint();
                                    Thread.sleep(100);
                                    dec_s++;
                                    if((dec_s%10)==0){
                                        boolean is_empty = true;
                                        for(int i=0;i<apples.size();i++) if(apples.get(i)!=null) is_empty=false;
                                        if(is_empty){
                                            apples.clear();
                                            setApples((int)(dec_s/100));
                                        }

                                    }
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(SnakeBoard.class.getName()).log(Level.SEVERE, null, ex);
                                }   
                        }
                    }

                };
                t.start();
            } catch (IOException ex) {
                Logger.getLogger(SnakeBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private void setApples(int size){
        for(int i=0;i<size;i++) {
                int x=0 ,y=0;
                x = rand.nextInt(20,380);
                y = rand.nextInt(20,380);
                while(head.getRect().intersects(new Rectangle(x,y,appleimg.getWidth(null),appleimg.getHeight(null)))){
                    x = rand.nextInt(20,380);
                    y = rand.nextInt(20,380);
                }
                apples.add(new Sprite(appleimg, x, y));
            }
    }
    private void move(){
        int xprev,yprev;
        xprev = head.getX();
        yprev = head.getY();
        head.setX(head.getX()+xacc);
        head.setY(head.getY()+yacc);
        for(int i=0;i<bodyparts.size();i++){
            Sprite body = bodyparts.get(i);
            int x_prev = body.getX();
            int y_prev = body.getY();
            body.setX(xprev);
            body.setY(yprev);
            xprev = x_prev;
            yprev = y_prev;
        }
    }
    public void paint(Graphics g) {
            super.paint(g);
            if(gameon){
                g.drawImage(head.getHead(), head.getX(), head.getY(),null);
                for(Sprite body:bodyparts){
                    g.drawImage(body.getImg(),body.getX(),body.getY(),null);
                }
                for(int i=0;i<apples.size();i++) g.drawImage(apples.get(i).img, apples.get(i).x, apples.get(i).y, this);
            }else{
                gameOver(g);
            }
    }
}
