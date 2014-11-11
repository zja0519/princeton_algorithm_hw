
public class KdTree {
    
    private Node root;
    private int N;
    private enum Direct {VERTICAL,HORIZONTAL}; //divided according to
    private double curMin;
    private Point2D curNearestPoint;
    
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb = null;        // the left/bottom subtree
        private Node rt = null;        // the right/top subtree
        
        public Node(Point2D p, RectHV rect){
            this.p = p;
            this.rect = rect;
        }
     }
    
    public KdTree() {
        root = null;
        N = 0;
    }                               // construct an empty set of points 
    public boolean isEmpty() {
        return root == null;
    }                      // is the set empty? 
    public int size() {
        return N;
    }                      // number of points in the set 
    public void insert(Point2D p) {
        if(p!=null) {
          root = insert(root,p,0.0,0.0,1.0,1.0,Direct.HORIZONTAL);
        }
    }             // add the point to the set (if it is not already in the set)
    private Node insert(Node node,Point2D p, double xmin, double ymin, double xmax, double ymax,Direct direct){
        if(node==null){
            N++;
            return new Node(p,new RectHV(xmin,ymin,xmax,ymax)); 
        }
        if(node.p.equals(p)) return node;
        switch(direct){
            case HORIZONTAL:
                if(p.x()<node.p.x()) {
                   node.lb = insert(node.lb,p,xmin,ymin,node.p.x(),ymax,Direct.VERTICAL);
                }
                else {
                   node.rt = insert(node.rt,p,node.p.x(),ymin,xmax,ymax,Direct.VERTICAL);
                }
                break;
            case VERTICAL:
                if(p.y()<node.p.y()) {
                   node.lb = insert(node.lb,p,xmin,ymin,xmax,node.p.y(),Direct.HORIZONTAL);
                }
                else{
                   node.rt = insert(node.rt,p,xmin,node.p.y(),xmax,ymax,Direct.HORIZONTAL);
                }
                break;
            default:
                break;
        }    
        return node;
    }
    public boolean contains(Point2D p) {
        return contains(root,p,Direct.HORIZONTAL);
    }           // does the set contain point p?

    private boolean contains(Node node, Point2D p, Direct direct){
        if(node == null) return false;
        if(node.p.equals(p)) return true;
        boolean res = false;
        switch(direct){
            case HORIZONTAL:
                if(p.x()<node.p.x()) {
                    res = contains(node.lb,p,Direct.VERTICAL);
                }
                else {
                    res = contains(node.rt,p,Direct.VERTICAL);
                }
                break;
            case VERTICAL:
                if(p.y()<node.p.y()) {
                    res = contains(node.lb,p,Direct.HORIZONTAL);
                }
                else {
                    res = contains(node.rt,p,Direct.HORIZONTAL);
                }
                break;
            default:
                break;
        }
        return res;
    }
    public void draw() {
        draw(root,Direct.HORIZONTAL);
    }                        // draw all points to standard draw 
    private void draw(Node node, Direct direct) {
        if(node == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        StdDraw.point(node.p.x(), node.p.y());
        switch(direct){
            case HORIZONTAL:
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                StdDraw.line(node.p.x(),node.rect.ymin(), node.p.x(), node.rect.ymax());
                draw(node.lb,Direct.VERTICAL);
                draw(node.rt,Direct.VERTICAL);
                break;
            case VERTICAL:
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
                draw(node.lb,Direct.HORIZONTAL);
                draw(node.rt,Direct.HORIZONTAL);
                break;
            default:
                break;
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        //long start = System.currentTimeMillis();
        
        Queue<Point2D> res = new Queue<Point2D>();
        getPointsInRange(res,root,rect);
        
        //System.out.println("kd range runtime is");
        //System.out.println(System.currentTimeMillis()-start);
        return res;
    }            // all points that are inside the rectangle 
    private void getPointsInRange(Queue<Point2D> res,Node node, RectHV rect){
        if(node == null) return;
        if(rect.intersects(node.rect)) {
            if(rect.contains(node.p)) res.enqueue(node.p);
            getPointsInRange(res,node.lb,rect);
            getPointsInRange(res,node.rt,rect);
        }
    }
    public Point2D nearest(Point2D p) {
        //long start = System.currentTimeMillis();
        
        if(root == null) return null;
        curMin = Double.MAX_VALUE;
        curNearestPoint = null;
        getNearestPoint(p,root,Direct.HORIZONTAL);
        
        //System.out.println("kd nearest runtime is");
        //System.out.println(System.currentTimeMillis()-start);
        return curNearestPoint;
    }            // a nearest neighbor in the set to point p; null if the set is empty
    
    private void getNearestPoint(Point2D p, Node node,Direct direct){
        if(node == null) return;
        if(curMin<node.rect.distanceTo(p)) return;
        
        double curDistance = p.distanceTo(node.p);
        if(curMin>curDistance) {
            curMin = curDistance;
            curNearestPoint = node.p;
        }
        
        switch(direct) {
            case HORIZONTAL:
                if(p.x()<node.p.x()) {
                    getNearestPoint(p,node.lb,Direct.VERTICAL);
                    getNearestPoint(p,node.rt,Direct.VERTICAL);
                }
                else {
                    getNearestPoint(p,node.rt,Direct.VERTICAL);
                    getNearestPoint(p,node.lb,Direct.VERTICAL);
                }
                break;
            case VERTICAL:
                if(p.y()<node.p.y()) {
                    getNearestPoint(p,node.lb,Direct.HORIZONTAL);
                    getNearestPoint(p,node.rt,Direct.HORIZONTAL);
                }
                else {
                    getNearestPoint(p,node.rt,Direct.HORIZONTAL);
                    getNearestPoint(p,node.lb,Direct.HORIZONTAL);
                }
                break;
            default:
                break;
        }
        
    }
    
    public static void main(String[] args) {

    }                 // unit testing of the methods (optional) 
}
