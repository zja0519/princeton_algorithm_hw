public class PercolationStats {
   private double[] resMem;
   private int T;
   private int N;
   private double mean;
   private double myStddev;
   private double loCon;
   private double upCon;
   
   public PercolationStats(int N, int T) {
       if (N <= 0 || T <= 0) 
           throw new IllegalArgumentException("input N or T is illegal");
       this.resMem = new double[T];
       this.T = T;
       this.N = N;
       this.mean = 0.0;
       this.myStddev = 0.0;
       this.loCon = 0.0;
       this.upCon = 0.0;
       fillUpResMem();
       calculatePara();
   }    // perform T independent computational experiments on an N-by-N grid
   private void calculatePara() {
       calStddev();
       if (T == 1) {
           this.upCon = Double.NaN;
           this.loCon = Double.NaN;
       }
       else {
           this.loCon = this.mean-(1.96*this.myStddev)/Math.sqrt(T);
           this.upCon = this.mean+(1.96*this.myStddev)/Math.sqrt(T);
       }
   }
   
   private void fillUpResMem() {
   int curCount = 0;
   int row;
   int col;
   for (int i = 0; i <= T-1; i++) {
   Percolation curPer = new Percolation(N);
   while (!curPer.percolates()) {
   while (true) {
   row = StdRandom.uniform(N)+1;
   col = StdRandom.uniform(N)+1;
   if (!curPer.isOpen(row, col)) break;
   }
   curPer.open(row, col);
   curCount++;
   }
   resMem[i] = (double) curCount/(double) (N*N);
   mean += resMem[i]/T;
   curCount = 0;
   }
   }
   
   public double mean() {
   return this.mean;
   }
   // sample mean of percolation threshold
   private double calStddev() {
   double curStddev;
   if (T == 1) curStddev = Double.NaN;
   else {
   curStddev = 0.0;
   for (int i = 0; i <= T-1; i++) {
   curStddev += (resMem[i]-mean)*(resMem[i]-mean);
   }
   curStddev /= T-1;
   }
   this.myStddev = Math.sqrt(curStddev);
   return this.myStddev;
   }                // sample standard deviation of percolation threshold
   
   public double stddev() {
       return this.myStddev;
   }
   
   public double confidenceLo() {
   return this.loCon;
   }          // returns lower bound of the 95% confidence interval
   
   public double confidenceHi() {
   return this.upCon;
   }          // returns upper bound of the 95% confidence interval
   
   public static void main(String[] args) {
   int N = Integer.parseInt(args[0]);
   int T = Integer.parseInt(args[1]);
   
   PercolationStats myStats = new PercolationStats(N, T);
   System.out.println("mean                    = "+ myStats.mean());
   System.out.println("stddev                  = "+ myStats.stddev());
   System.out.println(
   "95% confidence interval = "+ myStats.confidenceLo()+", "+myStats.confidenceHi());
   }  // test client, described below
}