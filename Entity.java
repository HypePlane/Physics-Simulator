import java.awt.Color;
import java.awt.Graphics;
public interface Entity {
    Color color = Color.white;
    public void paint(Graphics g);
    public boolean checkIntersection(Line line);
    public double[] getIntersection(Line line);
    public void update();
    public double getNormal(double[] point);
    public void resolve(Entity e);
}
