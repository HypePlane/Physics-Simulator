
import java.awt.*;
import javax.swing.JFrame;
public class Main extends JFrame{
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    public Main(){
        setSize(WIDTH+16, HEIGHT+39);
        System.out.println(getWidth()+ " " + getHeight());
        add(new Board(WIDTH,HEIGHT));

        setResizable(false);

        setTitle("Physics goes brrrrrrr");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ex = new Main();
            ex.setVisible(true);
        });
    }
}

//TODO

// create check for checkIntersection for ball-ball
//fix conservation of energy

