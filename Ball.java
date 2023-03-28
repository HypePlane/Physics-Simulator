import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
public class Ball implements Entity {
    double pos[] = new double[2];
    double prevPos[] = new double[2];//center
    double v[] = new double[2];
    double prevV[] = new double[3];
    double a[] = new double[3];
    double prevA[] = new double[3];
    int mass,radius;
    Color color = Color.green;
    public Ball(int x, int y, int m, int r){
        pos[0] = x;
        pos[1] = y;

        mass = m;
        radius =r;
    }
    public void setVelocity(int vx, int vy){
        v[0]= vx;
        v[1] = vy;
    }
    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval((int)pos[0]-radius,(int)pos[1]-radius, radius*2, radius*2);
    } 
    public void update (double fx, double fy){
        prevPos[0] = pos[0];
        prevPos[1] = pos[1];
        a[0] = fx/mass;
        a[1] = fy/mass;
        a[2] = Math.hypot(a[0], a[1]);
        pos[0] += .5*a[0] + v[0];
        pos[1] += .5*a[1] + v[1];
        prevV[0] = v[0];
        prevV[1] = v[1];
        prevV[2] = Math.hypot(v[0], v[1]);
        v[0]+=a[0];
        v[1]+=a[1];
    }
    public void checkIntersection(Border border){
        // find line that insersects with path of ball first
        // find angle of line
        // find normal line of line
        // find point of intersection
        // find velocity at point of intersection
        // find new direction of velocity(same magnitude)
        // find new position after velocity times remaining time
        // find new velocity after acceleration
        
    }
    public void resolve(Border border, Graphics g){
        // find line that insersects with path of ball first
        Line velocity = new Line(prevPos[0], prevPos[1], pos[0], pos[1]);

        // y-y1 = m(x-x1
        // y = mx-mx1+y1
        // b = y1-mx1
        ArrayList<Integer> xCoords = border.getXCoords();
        ArrayList<Integer> yCoords = border.getYCoords();
        double shortest = prevV[2];
        g.setColor(Color.yellow);
        g.drawLine((int)pos[0], (int)pos[1], (int)(pos[0]+prevV[0]*50), (int)(pos[1]+ prevV[1]*50));
        int n = xCoords.size();
        double time;
        double[] intersection;
        for (int i = 0; i <n; i++) {
            // equation of line
            Line line = new Line(xCoords.get((i-1+n)%n),yCoords.get((i-1+n)%n) , xCoords.get(i), yCoords.get(i));
            double dX = xCoords.get(i)-xCoords.get((i-1+n)%n);
            double dY = yCoords.get(i)-yCoords.get((i-1+n)%n);
            //double lineSlope = (dY)/(dX);
            //int xpos = xCoords.get(i);
            //int ypos = yCoords.get(i);
            
            //double lineInt = yCoords.get(i)-lineSlope*xCoords.get(i);
            double adjustedX = radius*Math.abs(dY)/Math.hypot(dX,dY);
            if(!line.isVertical()){
                double adjustedY = radius*Math.abs(dX)/Math.hypot(dX, dY);
                if(prevPos[1] > line.getY(prevPos[0])){
                    line.adjustY(adjustedY);
                }else{
                    line.adjustY(-adjustedY);
                }
            }
            
            if(prevPos[0] > line.getX(prevPos[1])){
                line.adjustX(adjustedX);
            }else{
                line.adjustX(-adjustedX);
            }
            //System.out.println(velocity+ " " + line);

            //lineInt = ypos-lineSlope*xpos;
            // m2x +b2 = m1x + b1
            // x(m2-m1) =(b1-b2)
            // x = (b1-b2)/(m1-m2)
            if(velocity.getSlope() == line.getSlope())continue;
            // point of intersection
            //TODOHERE
            double[] tempIntersection;

            if(line.checkIntersection(velocity)){
                
                intersection = line.findIntersection(velocity);
                System.out.println("Collision at " + intersection[0] +" "+  intersection[1]);
            }else{
                //System.out.println("fail");
                continue;
            }

            // check if point of intersection is in front of starting point
            if(prevV[0]!=0 && (intersection[0]-prevPos[0])/prevV[0] <0){
                System.out.println("bruh");
                continue;
            }
            if(prevV[1]!=0 && (intersection[1]-prevPos[1])/prevV[1]<0){
                System.out.println("bruh2");    
                continue;
            }
            // distance from previous point to intersection
            double y = intersection[1]-prevPos[1];
            double x = intersection[0]-prevPos[0];
            double distance = Math.hypot(x,y);
             // get shortest distance
            //System.out.println(Arrays.toString(prevPos));

            if(distance>shortest){
                continue;
            }else{
                shortest = distance;
            }
            //double normalInt = interY-slope*interX;
            // find velocity at point of intersection
            double velocityInter = Math.sqrt(prevV[2]*prevV[2] + 2*a[2]*distance);
            time =  (velocityInter-prevV[2])/a[2];
            // find new direction of velocity(same magnitude)
            double angleLine = line.getAngle();
            double angleVelocity = velocity.getAngle();
            //reflect angle over the normal line
            double newAngle = (angleLine*2-angleVelocity);
            //System.out.println(" Angle of Incidence: " + angleVelocity + " Angle Line : " + angleLine + " Angle of Reflection : " + newAngle);

            // use trig to find new velocity with new angle
            double newVX = Math.cos(Math.toRadians(newAngle))*velocityInter;
            double newVY = Math.sin(Math.toRadians(newAngle))*velocityInter;
            //System.out.println(v[0] + " "+ newVX + "   " + v[1] + newVY);
            time  = 1-time;
            // find new position after collision
            pos[0] =intersection[0]+ (int) (.5*a[0]*time*time + newVX*time);
            pos[1] = intersection[1] + (int) (.5*a[1]*time*time + newVY*time);
            //find new velocity after acceleration
            v[0] = newVX+ a[0]*time;
            v[1] = newVY+a[1]*time;


        }
        public void resolve(Entity e){
            
        }
        
            
                
            
           

    }
    // find intersection after moving the line of the shape one vakk  radius towards the ball line
    // instead of representing lines as mx+b represent them as an angle around a point
}
