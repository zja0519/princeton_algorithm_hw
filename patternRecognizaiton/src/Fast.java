import java.util.Arrays;


public class Fast {
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);
        
        In in = new In(args[0]);      // input file
        int N = in.readInt();         // N-by-N percolation system
        int i=0,j=0,lastIndex=0;
        double lastSlope=0.0;
        Point[] pArray = new Point[N];
        
        while (!in.isEmpty()) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            pArray[i++] = p;
            p.draw();
        }
        
        for (i = 0; i <= N-1; i++) {
            Point[] pArray2 = new Point[N-1];
            Point p = pArray[i];
            int curIndex=0;
            for (j = 0; j <= N-1; j++) {
                if (i == j) continue;
                pArray2[curIndex++] = pArray[j];
            }
            
            Arrays.sort(pArray2,p.SLOPE_ORDER);
            lastIndex = 0;
            
            while(lastIndex <= N-2) {
                curIndex = lastIndex;
                lastSlope = p.slopeTo(pArray2[lastIndex]);
                while (curIndex+1 <= N-2 && p.slopeTo(pArray2[curIndex+1]) == lastSlope) curIndex++;
                if(curIndex - lastIndex + 1 >= 3) {
                    Arrays.sort(pArray2,lastIndex,curIndex+1);
                    if (p.compareTo(pArray2[lastIndex])<=0) {
                        p.drawTo(pArray2[curIndex]);
                        StdOut.print(p.toString());
                        StdOut.print(" -> ");
                        
                        for (j = lastIndex ; j<=curIndex; j++) {
                            StdOut.print(pArray2[j].toString());
                            if (j!=curIndex) StdOut.print(" -> ");
                        }
                        
                        StdOut.println();
                    }
                }
                lastIndex = curIndex+1;
            }
        }
        
     // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }
}
