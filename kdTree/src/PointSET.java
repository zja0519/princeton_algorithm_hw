import java.util.Iterator;


public class PointSET {
    
    private SET<Point2D> set;
    public PointSET() {
        set = new SET<Point2D>();
    }                           // construct an empty set of points 
    public boolean isEmpty() {
        return set.isEmpty();
    }                      // is the set empty? 
    public int size() {
        return set.size();
    }                         // number of points in the set 
    public void insert(Point2D p) {
        if(!set.contains(p)) set.add(p);
    }             // add the point to the set (if it is not already in the set)
    public boolean contains(Point2D p) {
        return set.contains(p);
    }            // does the set contain point p? 
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        Iterator<Point2D> it = set.iterator();
        while(it.hasNext()){
            Point2D point = it.next();
            StdDraw.point(point.x(), point.y());
        }
    }                         // draw all points to standard draw 
    public Iterable<Point2D> range(RectHV rect) {
        //long start = System.currentTimeMillis();
        Queue<Point2D> inRange = new Queue<Point2D>();
        Iterator<Point2D> it = set.iterator();
        while(it.hasNext()){
            Point2D point = it.next();
            if(rect.contains(point)) {
                inRange.enqueue(point);
            }
        }
        //System.out.println("brute range runtime is");
        //System.out.println(System.currentTimeMillis()-start);
        return inRange;      
    }           // all points that are inside the rectangle 
    public Point2D nearest(Point2D p) {
        //long start = System.currentTimeMillis();
        Point2D result = null;
        double curMin = Double.MAX_VALUE;
        double curVal = 0.0;
        Iterator<Point2D> it = set.iterator();
        while(it.hasNext()){
            Point2D point = it.next();
            curVal = p.distanceTo(point);
            if(curVal < curMin){
                result = point;
                curMin = curVal;
            }
        }
        //System.out.println("brute nearest runtime is");
        //System.out.println(System.currentTimeMillis()-start);
        return result;
    }           // a nearest neighbor in the set to point p; null if the set is empty 

    public static void main(String[] args) {
        
    }                 // unit testing of the methods (optional) 
}
