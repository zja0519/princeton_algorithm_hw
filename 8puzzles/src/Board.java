public class Board {
    private final int[][] blocks;
    private final int N;
    private final int hamming;
    private final int manhattan;
    
    private int[][] copyBoard(){
        int[][] tempBoard = new int[N][N];
        for(int i=0;i<=N-1;i++)
            for(int j=0;j<=N-1;j++){
                tempBoard[i][j] = blocks[i][j];
            }
        return tempBoard;
    }
    
    public Board(int[][] blocks) {
        
        N = blocks.length;
        this.blocks = new int[N][N];
        
        for (int i=0;i<=N-1;i++)
            for(int j=0;j<=N-1;j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        
        int hamming=0;
        for(int i=0;i<=N-1;i++)
            for(int j=0;j<=N-1;j++){
                if(i==N-1 && j==N-1){
                    if (blocks[i][j]!=0) hamming++;
                }
                else {
                    if(blocks[i][j]!=0 && blocks[i][j]!=N*i+j+1) hamming++;
                }
            }
        this.hamming=hamming;
        
        int manhattan=0;
        for(int i=0;i<=N-1;i++)
            for(int j=0;j<=N-1;j++){
                if(blocks[i][j]!=0){
                    manhattan+=Math.abs(i-(blocks[i][j]-1)/N)+Math.abs(j-(blocks[i][j]-1)%N); 
                }
            }
        
        this.manhattan=manhattan;
        
    }          // construct a board from an N-by-N array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
        return this.N;
    }               // board dimension N
    
    public int hamming()  {
        return this.hamming;
    }                 // number of blocks out of place
    
    public int manhattan() {
        return this.manhattan;
    }                // sum of Manhattan distances between blocks and goal
    
    public boolean isGoal() {
        for (int i=0;i<=N-1;i++)
            for(int j=0;j<=N-1;j++){
                if(!(i==N-1 && j==N-1) && blocks[i][j]!=N*i+j+1) return false;
            }
        return true;
    }              // is this board the goal board?
    public Board twin() {
        int[][] tempBoard = copyBoard();
        int temp;
        
        outterLoop:
        for(int i=0;i<=N-1;i++)
            for(int j=0;j<=N-2;j++){
                if(tempBoard[i][j]!=0 && tempBoard[i][j+1]!=0) {
                    temp = tempBoard[i][j];
                    tempBoard[i][j] = tempBoard[i][j+1];
                    tempBoard[i][j+1] = temp;
                    break outterLoop;
                }
            }
        return new Board(tempBoard);
    }                   // a boadr that is obtained by exchanging two adjacent blocks in the same row
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass()!=this.getClass()) return false; 
        Board that = (Board) y;
        if(this.N!=that.N) return false;
        for (int i=0;i<=N-1;i++)
            for(int j=0;j<=N-1;j++){
                if(this.blocks[i][j]!=that.blocks[i][j]) return false;
            }
        return true;
    }       // does this board equal y?
    public Iterable<Board> neighbors() {
        int[][] tempBoard = copyBoard();
        int row=0;
        int col=0;

        Queue<Board> q = new Queue<Board>();
        
        outterLoop:
        for(row=0;row<=N-1;row++)
            for(col=0;col<=N-1;col++){
                if(tempBoard[row][col]==0) break outterLoop;
            }
        
        if(row-1>=0) {
            tempBoard[row][col]=tempBoard[row-1][col];
            tempBoard[row-1][col]=0;
            q.enqueue(new Board(tempBoard));
            tempBoard[row-1][col]=tempBoard[row][col];
            tempBoard[row][col]=0;
        }
        if(row+1<=N-1) {
            tempBoard[row][col]=tempBoard[row+1][col];
            tempBoard[row+1][col]=0;
            q.enqueue(new Board(tempBoard));
            tempBoard[row+1][col]=tempBoard[row][col];
            tempBoard[row][col]=0;
        }
        if(col-1>=0) {
            tempBoard[row][col]=tempBoard[row][col-1];
            tempBoard[row][col-1]=0;
            q.enqueue(new Board(tempBoard));
            tempBoard[row][col-1]=tempBoard[row][col];
            tempBoard[row][col]=0;
        }
        if(col+1<=N-1) {
            tempBoard[row][col]=tempBoard[row][col+1];
            tempBoard[row][col+1]=0;
            q.enqueue(new Board(tempBoard));
            tempBoard[row][col+1]=tempBoard[row][col];
            tempBoard[row][col]=0;
        }
                
        return (Iterable<Board>)q;
    }    // all neighboring boards
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }              // string representation of this board (in the output format specified below)

    public static void main(String[] args) {
        
    } // unit tests (not graded)
}