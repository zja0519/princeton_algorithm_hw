import java.util.ArrayList;
import java.util.HashMap;

public class BaseballElimination {
	private int[] w;
	private int[] l;
	private int[] r;
	private int[][] g;
	private ArrayList<String> teams;
	private HashMap<String, Integer> team2index;
	private int N;
	private int V;
 	
	public BaseballElimination(String filename) {
		In in = new In(filename);
		N = in.readInt();
		String curTeam;
		
		w = new int[N];
		l = new int[N];
		r = new int[N];
		g = new int[N][N];
		teams = new ArrayList<String>();
		team2index = new HashMap<String, Integer>();
		
		for(int i = 0; i<N; i++) {
			curTeam = in.readString();
			teams.add( curTeam );
			team2index.put(curTeam, i);
			w[i] = in.readInt();
			l[i] = in.readInt();
			r[i] = in.readInt();
			for(int j=0;j<N;j++) {
				g[i][j] = in.readInt();
			}
		}
		
		V = 2 + N + ( N * (N-1) ) / 2;
	}                   // create a baseball division from given filename in format specified below
	
	public int numberOfTeams() {
		return N;
	}                       // number of teams
	
	public Iterable<String> teams() {
		return teams;
	}                                // all teams
	
	public int wins(String team) {
		if(!team2index.containsKey(team)) throw new IllegalArgumentException();
		return w[team2index.get(team)];
	}                     // number of wins for given team
	
	public int losses(String team) {
		if(!team2index.containsKey(team)) throw new IllegalArgumentException();
		return l[team2index.get(team)];
	}                   // number of losses for given team
	
	public int remaining(String team) {
		if(!team2index.containsKey(team)) throw new IllegalArgumentException();
		return r[team2index.get(team)];
	}                // number of remaining games for given team
	
	public int against(String team1, String team2) {
		if( ! ( team2index.containsKey(team1) && team2index.containsKey(team2) ) ) throw new IllegalArgumentException();
		return g[team2index.get(team1)][team2index.get(team2)];
	}   // number of remaining games between team1 and team2
	
	private int isTrivialEliminated(String team) {
		int testIndex = team2index.get(team);
		for(int i=0;i<N;i++) {
			if(testIndex==i) continue;
			if((w[testIndex]+r[testIndex]-w[i])<0) return i;
		}
		return -1;
	}
	
	private FordFulkerson getMaxFlowSolution(String team) {
		if(!team2index.containsKey(team)) throw new IllegalArgumentException();
		
		int testIndex = team2index.get(team);
		int s = V-2;
		int t = V-1;
		int gIndexInNetwork = 0;
		
		FlowNetwork flowNetwork = new FlowNetwork(V);
		
		for(int i=0;i<N;i++) {
			if (i==testIndex) continue;
			for(int j=i+1;j<N;j++){
				if(j==testIndex) continue;
				gIndexInNetwork = N-1 + ( ( (2*N-2-i) * (i+1) ) / 2 - ( N - 1 - j ) );
				flowNetwork.addEdge(new FlowEdge(s,gIndexInNetwork,g[i][j]));
				flowNetwork.addEdge(new FlowEdge(gIndexInNetwork,i,Double.POSITIVE_INFINITY));
				flowNetwork.addEdge(new FlowEdge(gIndexInNetwork,j,Double.POSITIVE_INFINITY));
			}
		}
		
		for(int i=0;i<N;i++) {
			if(i==testIndex) continue;
			flowNetwork.addEdge(new FlowEdge(i,t,w[testIndex]+r[testIndex]-w[i]));
		}
		
		return new FordFulkerson(flowNetwork,s,t);
	}
	
	private int divisionGamesWithoutTeam(String team) {
		int count = 0;
		int testIndex = team2index.get(team);
		
		for(int i=0;i<N;i++) {
			if (i==testIndex) continue;
			for(int j=i+1;j<N;j++){
				if(j==testIndex) continue;
				count += g[i][j];
			}
		}
		return count;
	}
	
	public boolean isEliminated(String team) {
		if(!team2index.containsKey(team)) throw new IllegalArgumentException();
		if(isTrivialEliminated(team) != -1) return true;
		FordFulkerson solution = getMaxFlowSolution(team);
		
		return ( solution.value() ) < ( divisionGamesWithoutTeam(team) );
	}             // is given team eliminated?
	
	public Iterable<String> certificateOfElimination(String team) {
		if(!team2index.containsKey(team)) throw new IllegalArgumentException();
		
		int trivialIndex = isTrivialEliminated(team);
		if( trivialIndex != -1) {
			Queue<String> res = new Queue<String>();
			res.enqueue(teams.get(trivialIndex));
			return res;
		}
		
		FordFulkerson solution = getMaxFlowSolution(team);
		if ( ! (( solution.value() ) < ( divisionGamesWithoutTeam(team) )) ) return null;
		
		int testIndex = team2index.get(team);
		
		Queue<String> res = new Queue<String>();
		for(int i=0;i<N;i++) {
			if(i!=testIndex && solution.inCut(i)) res.enqueue(teams.get(i)); 
		}
		
		return res;
	} // subset R of teams that eliminates given team; null if not eliminated
	
//	public void testReadIn () {
//		for(int i=0;i<N;i++) {
//			StdOut.print(teams.get(i)+" ");
//			StdOut.print(w[i]);
//			StdOut.print(' ');
//			StdOut.print(l[i]);
//			StdOut.print(' ');
//			StdOut.print(r[i]);
//			StdOut.print(' ');
//			
//			for(int j=0;j<N;j++) {
//				StdOut.print(g[i][j]);
//				StdOut.print(' ');
//			}
//			StdOut.println();
//		}
//	}
	
	public static void main(String[] args) {
//	    BaseballElimination division = new BaseballElimination(args[0]);
//	    division.testReadIn();
		BaseballElimination division = new BaseballElimination(args[0]);
	    for (String team : division.teams()) {
	        if (division.isEliminated(team)) {
	            StdOut.print(team + " is eliminated by the subset R = { ");
	            for (String t : division.certificateOfElimination(team)) {
	                StdOut.print(t + " ");
	            }
	            StdOut.println("}");
	        }
	        else {
	            StdOut.println(team + " is not eliminated");
	        }
	    }
	}
	
}
