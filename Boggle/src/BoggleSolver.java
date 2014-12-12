import java.util.HashSet;


public class BoggleSolver {
	// Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
	private TrieST26 dict = new TrieST26();
	/***********recursion version used**************/
	private StringBuilder str = new StringBuilder();
	private HashSet<String> res;
	private BoggleBoard board;
	private boolean[] visited;
	private int M;
	private int N;
	/**********************************************/
	
    public BoggleSolver(String[] dictionary) {
    	for(String s:dictionary) {
    		dict.put(s);
    	}
    	dict.createPrefixS();
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    private void dfs(int i, int j) {
    	char c = board.getLetter(i, j);
    	dict.prefixPush(c);
    	if(dict.prefixCheck()==-1) {
    		dict.prefixPop(c);
    		return;
    	}
    	visited[i*N+j] = true;
		str.append(c);
		if(c=='Q') str.append('U');
		if(dict.prefixCheck()==1 && str.length()>=3) res.add(str.toString());
		
    	int testR;
    	int testC;
    	
    	for(int ii =-1;ii<2;ii++)
    		for(int jj=-1;jj<2;jj++) {
    			testR=i+ii;
    			testC=j+jj;
    			if(testR>=0 && testR <M && testC>=0 && testC<N && !visited[testR*N+testC]) {
    				dfs(testR,testC);
    			}
    		}
    	
    	visited[i*N+j] = false;
    	dict.prefixPop(c);
    	if(c=='Q') str.delete(str.length()-2, str.length());
		else str.deleteCharAt(str.length()-1);
    }

    
    public Iterable<String> getAllValidWords(BoggleBoard board) {
    	M = board.rows();
    	N = board.cols();
    	visited = new boolean[M*N];
    	res = new HashSet<String>();
    	this.board = board;
    	
    	for(int i=0;i<M;i++)
    		for(int j=0;j<N;j++) {
    			dfs(i,j);
		}
    	
    	return res;
    }
    
    //non recursive version
    /*
    public Iterable<String> getAllValidWords(BoggleBoard board) {
    	int M = board.rows();
    	int N = board.cols();
    	int[] steps = new int[M*N];
    	int[] rowOffset = new int[3];
    	int[] colOffset = new int[3];
    	int v = 0;
    	int curR = 0;
    	int curC = 0;
    	int nextR = 0;
    	int nextC = 0;
    	int curStep = 0;
    	int strType = -1;
    	char c;
    	
    	boolean[] visited = new boolean[M*N];
    	StringBuilder str = new StringBuilder();
    	Stack<Integer> stack = new Stack<Integer>();
    	HashSet<String> res = new HashSet<String>();
    	
    	rowOffset[0] = 0;
    	rowOffset[1] = -1;
    	rowOffset[2] = 1;
    	
    	colOffset[0] = 0;
    	colOffset[1] = -1;
    	colOffset[2] = 1;
    	
    	for(int i=0;i<M;i++)
    		for(int j=0;j<N;j++) {
    			
    			stack.push(i*N+j);
    			c = board.getLetter(i, j);
    			dict.prefixPush(c);
    			str.append(board.getLetter(i, j));
    			if(c=='Q') str.append('U');
    			visited[i*N+j] = true;
    			
    			while(!stack.isEmpty()) {
    				v = stack.peek();
    				curR = v/N;
    				curC = v%N;
    				curStep = steps[curR*N+curC];
    				//curStr = str.toString();
    				strType = dict.prefixCheck();
    				if(strType!=-1 && curStep < 8) {
    					if(str.length() >= 3 && strType==1) res.add(str.toString());
    					
    					for(int k=curStep;k<8;k++) {
        					steps[curR*N+curC]++;
        					nextR = curR + rowOffset[ (k+1) / 3 ];
        					nextC = curC + colOffset[ (k+1) % 3 ];
        					if(nextR >=0 && nextR<M && nextC>=0 && nextC<N && !visited[nextR*N+nextC]) {
        						stack.push( nextR * N + nextC );
        						visited[nextR*N+nextC]=true;
        						c = board.getLetter(nextR, nextC);
        						dict.prefixPush(c);
        		    			str.append(board.getLetter(nextR, nextC));
        		    			if(c=='Q') str.append('U');
        		    			break;
        					}
    					}
    				}
    				else {
    					steps[curR*N+curC] = 0;
    					visited[curR*N+curC] = false;
    					c = board.getLetter(curR, curC);
    					dict.prefixPop(c);
    					//if(str.length()==dict.prefixSsize()-1) dict.prefixPop(c);
    					if(c=='Q') str.delete(str.length()-2, str.length());
    					else str.deleteCharAt(str.length()-1);
    					//if((stack.size()==dict.prefixSsize()-1) || (c=='Q' && stack.size()==dict.prefixSsize()-2)) dict.prefixPop(c);
    					stack.pop();	
    				}
    			}
    		}
    	
    	return res;
    }
*/
    
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
    	if(!dict.contains(word)) return 0;
    	int len = word.length();
    	int score = 0;
    	
    	if(len>=0 && len<=2) score=0;
    	else if(len>=3 && len<=4) score=1;
    	else if(len==5) score=2;
    	else if(len==6) score=3;
    	else if(len==7) score=5;
    	else score=11;
    	
    	return score;
    }
    
    public static void main(String[] args)
    {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        int count = 0;
        for (String word : solver.getAllValidWords(board))
        {
            StdOut.println(word);
            score += solver.scoreOf(word);
            count++;
        }
        StdOut.println("Score = " + score);
        StdOut.println("Count = " + count);
    }
}
