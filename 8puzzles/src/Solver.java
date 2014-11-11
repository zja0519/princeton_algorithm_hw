import java.util.Iterator;

public class Solver {
    private MinPQ<SearchNode> minPqOri;
    private MinPQ<SearchNode> minPqTwin;
    private SearchNode curOriNode;
    private SearchNode curTwinNode;
    private boolean isSolvable;
    
    private class SearchNode implements Comparable<SearchNode> {
        private SearchNode preNode;
        private int curMove;
        private Board board;
        
        public SearchNode (SearchNode preNode,int curMove, Board board){
            this.preNode=preNode;
            this.curMove=curMove;
            this.board=board;
        }

        @Override
        public int compareTo(SearchNode that) {
            // TODO Auto-generated method stub
            int priority1 = this.board.manhattan() + curMove;
            int priority2 = that.board.manhattan() + that.curMove;
            if (priority1<priority2) return -1;
            if (priority1>priority2) return +1;
            return 0;
        }
    }
    public Solver(Board initial) {
        minPqOri = new MinPQ<SearchNode>();
        minPqOri.insert(new SearchNode(null,0,initial));
        
        minPqTwin = new MinPQ<SearchNode>();
        minPqTwin.insert(new SearchNode(null,0,initial.twin()));
        
        while(true) {
            curOriNode = minPqOri.delMin();
            curTwinNode = minPqTwin.delMin();
            if(curOriNode.board.isGoal() || curTwinNode.board.isGoal()) break;

            Iterator<Board> curOriNeighbor = curOriNode.board.neighbors().iterator();
            Iterator<Board> curTwinNeighbor = curTwinNode.board.neighbors().iterator();
            
            while(curOriNeighbor.hasNext()) {
                Board curBoard = curOriNeighbor.next();
                if(curOriNode.preNode==null || !curBoard.equals(curOriNode.preNode.board)){
                    minPqOri.insert(new SearchNode(curOriNode,curOriNode.curMove+1,curBoard));
                }
            }
            while(curTwinNeighbor.hasNext()) {
                Board curBoard = curTwinNeighbor.next();
                if(curTwinNode.preNode==null || !curBoard.equals(curTwinNode.preNode.board)){
                    minPqTwin.insert(new SearchNode(curTwinNode,curTwinNode.curMove+1,curBoard));
                }
            }
        }
        
        this.isSolvable=curOriNode.board.isGoal();
    }         // find a solution to the initial board (using the A* algorithm)
    public boolean isSolvable() {
        return isSolvable;
    }           // is the initial board solvable?
    public int moves() {
        if(isSolvable) return curOriNode.curMove;
        return -1;
    }                     // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution() {
        if(!isSolvable) return null;
        Stack<Board> res= new Stack<Board>();
        SearchNode runner = curOriNode;
        while(runner!=null) {
            res.push(runner.board);
            runner=runner.preNode;
        }
        return (Iterable<Board>)res;
    }     // sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args) {
     // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }// solve a slider puzzle (given below)
}