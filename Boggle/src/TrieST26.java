
public class TrieST26 {
	 private static final int R = 26;        // extended ASCII


	    private Node root;      // root of trie
	    private int N;          // number of keys in trie
	    private Stack<Node> prefixS;

	    // R-way trie node
	    private static class Node {
	        //private boolean isWord=false;
	        private String value = null;
	        private Node[] next = new Node[R];
	    }

	    public TrieST26() {
	    }

	   /**
	     * Initializes an empty string symbol table.
	     */

	    /**
	     * Returns the value associated with the given key.
	     * @param key the key
	     * @return the value associated with the given key if the key is in the symbol table
	     *     and <tt>null</tt> if the key is not in the symbol table
	     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
	     */
	    
	    public void createPrefixS(){
	    	prefixS = new Stack<Node>();
	    	prefixS.push(root);
	    }
	    
	    public void prefixPush(char c){
	    	//StdOut.println(prefixS.size());
	    	if(prefixS.peek()==null) return;
	    	Node nextNode = prefixS.peek().next[c-'A'];
	    	prefixS.push(nextNode);
	    	if(c=='Q') {
	    		if(prefixS.peek()!=null) prefixS.push(prefixS.peek().next['U'-'A']);
	    		else prefixS.push(null);
	    	}
	    }
	    
	    public int prefixCheck() {
	    	int type = -1;
	    	Node curNode = prefixS.peek();
	    	if(curNode!=null) {
	    		if(curNode.value!=null) type = 1;
	    		else type =0;
	    	}
	    	return type;
	    }
	    
	    public void prefixPop(char c){
	    	if(prefixS.size()>1) {
	    		//StdOut.println("pop "+c);
	    		prefixS.pop();
	    		if(c=='Q') prefixS.pop();
	    	} 
	    }
	    
	    public int prefixSsize() {
	    	return this.prefixS.size();
	    }

	    public Node get(String key) {
	        return get(root, key, 0);
	    }

	    /**
	     * Does this symbol table contain the given key?
	     * @param key the key
	     * @return <tt>true</tt> if this symbol table contains <tt>key</tt> and
	     *     <tt>false</tt> otherwise
	     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
	     */
	    public boolean contains(String key) {
	    	Node node = get(key);
	        return node!=null && node.value!=null;
	    }

	    private Node get(Node x, String key, int d) {
	        if (x == null) return null;
	        if (d == key.length()) return x;
	        char c = key.charAt(d);
	        return get(x.next[c-'A'], key, d+1);
	    }

	    /**
	     * Inserts the key-value pair into the symbol table, overwriting the old value
	     * with the new value if the key is already in the symbol table.
	     * If the value is <tt>null</tt>, this effectively deletes the key from the symbol table.
	     * @param key the key
	     * @param val the value
	     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
	     */
	    public void put(String key) {
	        root = put(root, key, 0);
	    }

	    private Node put(Node x, String key, int d) {
	        if (x == null) x = new Node();
	        if (d == key.length()) {
	            N++;
	            x.value = key;
	            return x;
	        }
	        char c = key.charAt(d);
	        x.next[c-'A'] = put(x.next[c-'A'], key, d+1);
	        return x;
	    }

	    /**
	     * Returns the number of key-value pairs in this symbol table.
	     * @return the number of key-value pairs in this symbol table
	     */
	    public int size() {
	        return N;
	    }

	    /**
	     * Is this symbol table empty?
	     * @return <tt>true</tt> if this symbol table is empty and <tt>false</tt> otherwise
	     */
	    public boolean isEmpty() {
	        return size() == 0;
	    }
	    
	    public boolean containsAsPrefix(String prefix) {
	    	if(prefix.isEmpty()) return true;
	    	if(root==null) return false;
	    	Node curNode = root;
	    	int len = prefix.length();
	    	for(int i=0;i<len;i++) {
	    		if(curNode.next[prefix.charAt(i)-'A']==null) return false;
	    		curNode = curNode.next[prefix.charAt(i)-'A'];
	    	}
	    	return true;
	    }
	    
	    public int checkString(String str) {
	    	if(str.isEmpty()) return 1;
	    	if(root==null) return -1;
	    	Node curNode = root;
	    	int len = str.length();
	    	for(int i=0;i<len;i++) {
	    		if(curNode.next[str.charAt(i)-'A']==null) return -1;
	    		curNode = curNode.next[str.charAt(i)-'A'];
	    	}
	    	if(curNode.value!=null) return 1;
	    	else return 0;
	    }
	    
	    public String getTopWord(){
	    	if(this.prefixS.peek()==null) return null;
	    	return this.prefixS.peek().value;
	    }

	    /**
	     * Unit tests the <tt>TrieSET</tt> data type.
	     */
	    public static void main(String[] args) {

	        // build symbol table from standard input
	        TrieST<Integer> st = new TrieST<Integer>();
	        for (int i = 0; !StdIn.isEmpty(); i++) {
	            String key = StdIn.readString();
	            st.put(key, i);
	        }

	        // print results
	        if (st.size() < 100) {
	            StdOut.println("keys(\"\"):");
	            for (String key : st.keys()) {
	                StdOut.println(key + " " + st.get(key));
	            }
	            StdOut.println();
	        }

	        StdOut.println("longestPrefixOf(\"shellsort\"):");
	        StdOut.println(st.longestPrefixOf("shellsort"));
	        StdOut.println();

	        StdOut.println("keysWithPrefix(\"shor\"):");
	        for (String s : st.keysWithPrefix("shor"))
	            StdOut.println(s);
	        StdOut.println();

	        StdOut.println("keysThatMatch(\".he.l.\"):");
	        for (String s : st.keysThatMatch(".he.l."))
	            StdOut.println(s);
	    }
}
