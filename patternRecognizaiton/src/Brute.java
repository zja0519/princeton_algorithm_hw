import java.util.Arrays;


public class Brute {
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);
        
        In in = new In(args[0]);      // input file
        int N = in.readInt();         // N-by-N percolation system
        int i = 0,j = 0,k = 0,l = 0;
        Point[] pArray = new Point[N];
        Point[] tempArray = new Point[4];
        double slope1,slope2,slope3;
        
        while (!in.isEmpty()) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            pArray[i++] = p;
            p.draw();
        }
        
        for(i = 0; i <= N-4; i++)
            for(j = i+1; j <= N-3; j++)
                for(k = j+1; k <= N-2;k++)
                    for(l = k+1; l <= N-1; l++) {
                        tempArray[0]=pArray[i];
                        tempArray[1]=pArray[j];
                        tempArray[2]=pArray[k];
                        tempArray[3]=pArray[l];
                        
                        slope1=tempArray[0].slopeTo(tempArray[1]);
                        slope2=tempArray[1].slopeTo(tempArray[2]);
                        slope3=tempArray[2].slopeTo(tempArray[3]);
                        
                        if((slope1 == slope2) && (slope2 == slope3)) {
                            Arrays.sort(tempArray);
                            tempArray[0].drawTo(tempArray[3]);
                            StdOut.println(tempArray[0].toString()+" -> "+
                                           tempArray[1].toString()+" -> "+
                                           tempArray[2].toString()+" -> "+
                                           tempArray[3].toString()
                                          );
                        }
                    }
        
        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }
}
