import java.util.Arrays;

public class Line {
    double[] point1;
    double[] point2;
    double slope, yint;
    double xval;// if line is horizontal
    boolean vertical = false;
    public Line(double x1, double y1, double x2, double y2){
        point1 = new double[2];
        point2 = new double[2];
        point1[0] = x1; 
        point1[1] = y1;
        point2[0] = x2;
        point2[1] = y2;
        calculate();
    }
    public void calculate(){
        double dx = point2[0]-point1[0];
        double dy = point2[1]-point1[1];
        if(dx == 0){
            xval = point1[0];
            vertical = true;
        }else{
            slope = dy/dx;
            yint = point1[1] - slope*point1[0];
            vertical = false;
        }
    }
    public boolean checkIntersection(Line line){
        if(line.getSlope() == slope) return false;
        //System.out.println("Checking : " + line+ " vs .");
        //System.out.println(toString());
        if(line.isVertical() && isVertical()){
            return false;
        }else if( line.isVertical()){
            double intersection = slope*line.getXVal() + yint;
            if(line.withinRange(intersection, false) && withinRange(intersection,false)){
                return true;
            }
            return false;
        }else if(isVertical()){
            double intersection = line.getSlope()*getXVal() + line.getYInt();
            if(line.withinRange(intersection, false) && withinRange(intersection,false)){
                return true;
            }
            return false;
        }
        else {
            //(Yint-lineInt)/(slope-lineSlope)
            //m1x+b1=m2x+b2
            //(m1-m2)x = b2-b1
            //(b2-b1)/(m1-m2) == x
            double intersectionX = (yint-line.getYInt())/(line.getSlope()-slope);//-1602-465)/-6.55
            double intersectionY = intersectionX*slope+yint;
            if(line.withinRange(intersectionY, false) && withinRange(intersectionY,false)){
                if(line.withinRange(intersectionX, true) && withinRange(intersectionX,true)){
                    return true;
                }
            }
            return false;
        }
    }
    public double[] findIntersection(Line line){
        double intersection[] = new double[2];
        if(isVertical()){
            intersection[1] = line.getSlope()*xval + line.getYInt();
            intersection[0] = xval;
            System.out.println(" 1 " +Arrays.toString(intersection) + toString() );

        }else if( line.isVertical()){
            intersection[1] = getSlope()*line.getXVal() + getYInt();
            intersection[0] = line.getXVal();
            System.out.println(" 2 " + Arrays.toString(intersection));

        }else{
            // m x +b = mx+b
            //m1x-m2x = (b2-b1)
            // x(m1-m2) = b2-b1
            // x = (b2-b1)/(m1-m2)
            intersection[0] = (yint-line.getYInt())/(line.getSlope() - slope);
            intersection[1] = slope*intersection[0] +yint;
            System.out.println(Arrays.toString(intersection));
        }
        return intersection;
    }
    public boolean withinRange(double val, boolean x){
        // if x is true, check domain   if x is false check range
        if(x){
            if(isVertical() && val == xval){
                return true;
            }
            return  (val>=Math.min(point1[0] , point2[0] ) )&& ( val<=Math.max(point1[0] , point2[0]));
        }else{
            return (val>=Math.min(point1[1],point2[1])) && val<=Math.max(point1[1], point2[1]);
        }
    }
    public void adjustX(double dx){
        point1[0]+=dx;
        point2[0]+=dx;
        calculate();
    }
    public void adjustY(double dy){
        point1[1]+=dy;
        point2[1]+=dy;
        calculate();
    }
    public double getY(double x){
        return x*slope+yint;
    }
    public double getX(double y){
        if(isVertical())return xval;
        return (y-yint)/slope;
    }
    public double getXVal(){
        return xval;
    }
    public boolean isVertical(){
        return vertical;
    }
    public double getSlope(){
        return slope;
    }
    public double getYInt(){
        return yint;
    }
    public String toString(){
        String out = "Point1 : (" + point1[0] + "," + point1[1] + ")  Point 2 : ("+point2[0] + "," + point2[1]+")";
        if(!isVertical()){
            out+= " Slope : " + slope +" Yint : "+ yint ;
        }else{
            out+= " XVal : " + xval;
        }
        return out;
    }
    public double getAngle(){
        //4 cases  1 (+x , +y)   2(-x,+y) 3 (-x, -y) 4 (x,-y)
        double dx = point2[0] - point1[0];
        double dy = point2[1] - point1[1];
        if(dx == 0 ){
            return dy>0 ? 90:270;
        }
        if(dy==0){
            return dx>0 ?0 : 180;
        }
        double out = 0;
        if(dx>0){
            if(dy>0){ // Q1
                return Math.atan(dy/dx);
            }else{ // Q4
                return Math.atan(dy/dx) + Math.PI;
            }
        }else{
            if(dy>0){ //Q2
                return Math.atan(dy/dx);
            }
        }

        
    }
}
