public class Percolation {
   private int N;
   private boolean[][] isOpenMem;
   private WeightedQuickUnionUF wUF;
   
   public Percolation(int N) {
   testN(N);
   this.N = N;
   this.isOpenMem = new boolean[N][N];
   this.wUF = new WeightedQuickUnionUF(N*N+2);
   for (int i = 1; i <= N; i++) {
   this.wUF.union(0, matrix2array(1, i));
   this.wUF.union(N*N+1, matrix2array(N, i));
   }
   }                // create N-by-N grid, with all sites blocked
   
   private void testN(int n) {
   if (n <= 0) throw new IllegalArgumentException("input N is illegal");
   }
   private void testIndex(int i) {
   if (i <= 0 || i > N) 
   throw new IndexOutOfBoundsException("row index i out of bounds");
   }
   private void testRowCol(int i, int j) {
   testIndex(i);
   testIndex(j);
   }
   private int matrix2array(int i, int j) {
   testRowCol(i, j);
   return (i-1)*N+j;
   }
   private boolean isRowColValid(int i, int j) {
   return (i >= 1 && i <= N) && (j >= 1 && j <= N);
   }
   
   public void open(int i, int j) {
   if (!isOpen(i, j)) {
   isOpenMem[i-1][j-1] = true;
   if (isRowColValid(i-1, j) && isOpen(i-1, j)) {
  this.wUF.union(matrix2array(i-1, j), matrix2array(i, j));
   }
   if (isRowColValid(i+1, j) && isOpen(i+1, j)) {
  this.wUF.union(matrix2array(i+1, j), matrix2array(i, j));
   }
   if (isRowColValid(i, j-1) && isOpen(i, j-1)) {
  this.wUF.union(matrix2array(i, j-1), matrix2array(i, j));
   }
   if (isRowColValid(i, j+1) && isOpen(i, j+1)) {
  this.wUF.union(matrix2array(i, j+1), matrix2array(i, j));
   }
   }
   }           // open site (row i, column j) if it is not already
   public boolean isOpen(int i, int j) {
   testRowCol(i, j);
   return this.isOpenMem[i-1][j-1];
   }      // is site (row i, column j) open?
   public boolean isFull(int i, int j) {
   return isOpen(i, j) && this.wUF.connected(matrix2array(i, j), 0);
   }      // is site (row i, column j) full?
   public boolean percolates() {
   if (N == 1) {
       return isOpen(1, 1);
   }
   return this.wUF.connected(0, N*N+1);
   }              // does the system percolate?
   public static void main(String[] args) {
   }   // test client, optional
}