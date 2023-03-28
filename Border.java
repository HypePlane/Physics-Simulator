import java.awt.*;
import java.util.ArrayList;

public class Border {
    //system of points
    ArrayList<Integer> pointsX = new ArrayList<>();
    ArrayList<Integer> pointsY = new ArrayList<>();
    int width, height;
    int x, y;
    Color color = Color.ORANGE;

    public Border(int x, int y, int width, int height){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        pointsX.add(x); pointsY.add(y);
        pointsX.add(x+width); pointsY.add(y);
        pointsX.add(x+width); pointsY.add(y+height);
        pointsX.add(x); pointsY.add(y+height);
    }
    public Border(ArrayList<int[]> points){
        x = points.get(0)[0];
        y = points.get(0)[1];
        width = points.get(0)[0];
        height = points.get(0)[1];

        for (int[] point : points) {
            pointsX.add(point[0]);
            pointsY.add(point[1]);
            x = Math.min(x, point[0]);
            y = Math.min(y, point[1]);
            width = Math.max(width, point[0]);
            height = Math.max(height, point[1]);
        }
        width -=x;
        height-=y;
    }
    public void setColor(Color color){
        this.color = color;
    }
    public void paint (Graphics g){
        g.setColor(color);
        Polygon temp = new Polygon(pointsX.stream().mapToInt(i -> i).toArray(),  pointsY.stream().mapToInt(i -> i).toArray(), pointsY.size());
        g.drawPolygon(temp);
    }
    public ArrayList<Integer> getXCoords(){
        return pointsX;
    }
    public ArrayList<Integer> getYCoords(){
        return pointsY;
    }
    public int getHeight(){return height;}
    
    public int getWidth(){return width;}
    public int getX(){return x;}
    public int getY(){return y;}
    public boolean checkIntersection(Ball ball){
        return false;
    }
}
