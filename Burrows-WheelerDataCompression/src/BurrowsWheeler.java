
public class BurrowsWheeler {
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    private static final int R = 256;
	public static void encode() {
    	String s = BinaryStdIn.readString();
    	CircularSuffixArray suffixArray = new CircularSuffixArray(s);
    	int N = suffixArray.length();
    	
    	for(int i=0;i<N;i++) {
    		if(suffixArray.index(i)==0) {
    			BinaryStdOut.write(i);
    			break;
    		}
    	}
    	
    	for(int i=0;i<N;i++)
    		BinaryStdOut.write( s.charAt( (suffixArray.index(i) + N-1) % N ) );
    	
    	BinaryStdOut.close();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
    	int first = BinaryStdIn.readInt();
    	int N = 0;
    	int[] count = new int[R+1];
    	int[] next;
    	int curIndex;
    	char c;
    	StringBuilder str = new StringBuilder();
    	
    	for(;!BinaryStdIn.isEmpty();N++) {
    		c = BinaryStdIn.readChar();
    		str.append(c);
    		count[c+1]++;
    	}
    	for(int r = 0 ; r < R ; r++) {
    		count[r+1] += count[r];
    	}
    	next = new int[N];
    	for(int i = 0;i<N;i++) {
    		c = str.charAt(i);
    		next[count[c]++] = i;
    	}
    	curIndex = first;
    	for(int i=0;i<N;i++) {
    		BinaryStdOut.write(str.charAt(next[curIndex]));
    		curIndex = next[curIndex];
    	}
    	BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
