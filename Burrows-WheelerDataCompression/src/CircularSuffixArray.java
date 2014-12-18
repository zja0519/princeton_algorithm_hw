
public class CircularSuffixArray {
	private char[] strArray;
	private int[] suffixFirstIndex;
	private int N;
	private static final int CUTOFF = 15;
	
	private int charAt(int index, int d) {
		return strArray[( index + d ) % N];
	}
    private void sort(int[] suffixFirstIndex, int lo, int hi, int d) { 

        // cutoff to insertion sort for small subarrays
        if (hi <= lo + CUTOFF) {
            insertion(suffixFirstIndex, lo, hi, d);
            return;
        }

        int lt = lo, gt = hi;
        int v = charAt(suffixFirstIndex[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(suffixFirstIndex[i], d);
            if      (t < v) exch(suffixFirstIndex, lt++, i++);
            else if (t > v) exch(suffixFirstIndex, i, gt--);
            else              i++;
        }

        // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi]. 
        sort(suffixFirstIndex, lo, lt-1, d);
        if (v >= 0) sort(suffixFirstIndex, lt, gt, d+1);
        sort(suffixFirstIndex, gt+1, hi, d);
    }

    // sort from a[lo] to a[hi], starting at the dth character
    private void insertion(int[] suffixFirstIndex, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(suffixFirstIndex[j], suffixFirstIndex[j-1], d); j--)
                exch(suffixFirstIndex, j, j-1);
    }
    
    private void exch(int[] suffixFirstIndex, int i, int j) {
        int temp = suffixFirstIndex[i];
        suffixFirstIndex[i] = suffixFirstIndex[j];
        suffixFirstIndex[j] = temp;
    }
    
    private boolean less(int suffixStartIndexV, int suffixStartIndexW, int d) {
        //assert v.substring(0, d).equals(w.substring(0, d));
        for (int i = d; i < N; i++) {
            if (charAt(suffixStartIndexV,i) < charAt(suffixStartIndexW,i)) return true;
            if (charAt(suffixStartIndexV,i) > charAt(suffixStartIndexW,i)) return false;
        }
        return false;
    }
    
    public CircularSuffixArray(String s) {
    	N = s.length();
    	strArray = s.toCharArray();
    	suffixFirstIndex = new int[strArray.length];
    	for(int i=0;i<N;i++)
    		suffixFirstIndex[i] = i;
    	sort(suffixFirstIndex,0,N-1,0);	
    } // circular suffix array of s
    
    public int length() {
    	return N;
    }                  // length of s
    
    public int index(int i) {
    	if(!(i>=0 && i<N)) throw new IndexOutOfBoundsException();
    	return suffixFirstIndex[i];
    }              // returns index of ith sorted suffix
    
//    public void printOut() {
//    	for(int i:this.suffixFirstIndex)
//    		StdOut.println(i);
//    }
    public static void main(String[] args) {
//    	String s = BinaryStdIn.readString();
//    	CircularSuffixArray a = new CircularSuffixArray(s);
//    	a.printOut();
    }// unit testing of the methods (optional)
}
