import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel implements Runnable{

    private final static int DELAY = 25;
    ArrayList<Border> borders = new ArrayList<>();
    ArrayList<Ball> balls = new ArrayList<>();
    double gravitationalConstant = .1;
    public Board(int width , int height){
        setBackground(Color.black);
        setSize(new Dimension(width, height));
        System.out.println(getWidth()+ " " + getHeight());
        borders.add(new Border(10, 10, getWidth()-20, getHeight()-20));
        balls.add(new Ball(250, 250,1,25));
        balls.get(0).setVelocity(1,0);
    }

    public void update(Graphics g){
        for (Ball ball : balls) {
            ball.update(0,gravitationalConstant);
            for (Border border : borders) {
               ball.resolve(border, g);
            }
        }
    }
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        update(g);

        for (Ball ball : balls) {
            ball.paint(g);
        }
        for (Border border : borders) {
            border.paint(g);
        }
        
    }
    //https://zetcode.com/javagames/animation/
    @Override
    public void addNotify() {
        super.addNotify();
        Thread animator = new Thread(this);
        animator.start();
    }
    @Override
    public void run(){
        long beforeTime, timediff,sleep;
        beforeTime = System.currentTimeMillis();
        int x = 0;
        while(true){
            repaint();
            //System.out.println(x++);
            timediff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY-timediff;
            if(sleep<0){
                sleep = 2;
            }
            try{
                Thread.sleep(sleep);
            }catch (InterruptedException e) {

                String msg = "u suck lol" +  e.getMessage();

                JOptionPane.showMessageDialog(this, msg, "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            beforeTime = System.currentTimeMillis();
        }

    }   
}
