
public class Outcast {
	private WordNet wordnet;
	public Outcast(WordNet wordnet) {
		this.wordnet=wordnet;
	}        // constructor takes a WordNet object
	public String outcast(String[] nouns) {
		int curMax = Integer.MIN_VALUE;
		String res = null;
		int count = nouns.length;
		int curDis = 0;
		for(int i=0;i<count;i++){
			curDis = 0;
			for(int j=0;j<count;j++){
				if(i==j) continue;
				curDis+=wordnet.distance(nouns[i], nouns[j]);
			}
			if(curDis>curMax) {
				res=nouns[i];
				curMax=curDis;
			}
		}
		return res;
	}  // given an array of WordNet nouns, return an outcast
	public static void main(String[] args) {
		WordNet wordnet = new WordNet(args[0], args[1]);
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < args.length; t++) {
	        In in = new In(args[t]);
	        String[] nouns = in.readAllStrings();
	        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	    }
	} // see test client below
}
