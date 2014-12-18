
public class MoveToFront {
	// apply move-to-front encoding, reading from standard input and writing to standard output
	//private static final int R = 256;
	/*
	public static void encode() {
		StringBuilder symTable = new StringBuilder();
		char c;
		int index;
		for(int i=0;i<R;i++)
			symTable.append((char)i);
		while(!BinaryStdIn.isEmpty()) {
			c = BinaryStdIn.readChar();
			index = symTable.indexOf(""+c);
			BinaryStdOut.write( (char)index );
			symTable.deleteCharAt(index).insert(0, c);
		}
		BinaryStdOut.close();
	}
	
	// apply move-to-front decoding, reading from standard input and writing to standard output
	public static void decode() {
		StringBuilder symTable = new StringBuilder();
		char c;
		int index;
		for(int i=0;i<R;i++)
			symTable.append((char)i);
		while(!BinaryStdIn.isEmpty()) {
			index = (int)(BinaryStdIn.readChar());
			c = symTable.charAt(index);
			BinaryStdOut.write( c );
			symTable.deleteCharAt(index).insert(0, c);
		}
		BinaryStdOut.close();
	}
	*/
	/*
	public static void encode() {
		ArrayList<Character> symTable = new ArrayList<Character>();
		char c;
		int index;
		for(int i=0;i<R;i++)
			symTable.add((char)i);
		while(!BinaryStdIn.isEmpty()) {
			c = BinaryStdIn.readChar();
			index = symTable.indexOf(c);
			BinaryStdOut.write( (char)index );
			symTable.remove(index);
			symTable.add(0, c);
		}
		BinaryStdOut.close();
	}
	
	// apply move-to-front decoding, reading from standard input and writing to standard output
	public static void decode() {
		ArrayList<Character> symTable = new ArrayList<Character>();
		char c;
		int index;
		for(int i=0;i<R;i++)
			symTable.add((char)i);
		while(!BinaryStdIn.isEmpty()) {
			index = (int)(BinaryStdIn.readChar());
			c = symTable.get(index);
			BinaryStdOut.write( c );
			symTable.remove(index);
			symTable.add(0, c);
		}
		BinaryStdOut.close();
	}
	*/
	public static void encode() {
		RtableList symTable = new RtableList();
		int index;
		char c;
		while(!BinaryStdIn.isEmpty()) {
			c = BinaryStdIn.readChar();
			index = symTable.move2front(c);
			BinaryStdOut.write( (char)index );
		}
		BinaryStdOut.close();
	}
	
	// apply move-to-front decoding, reading from standard input and writing to standard output
	public static void decode() {
		RtableList symTable = new RtableList();
		int index;
		char c;
		while(!BinaryStdIn.isEmpty()) {
			index = (int)(BinaryStdIn.readChar());
			c = symTable.move2front(index);
			BinaryStdOut.write( c );
		}
		BinaryStdOut.close();
	}
	// if args[0] is '-', apply move-to-front encoding
	// if args[0] is '+', apply move-to-front decoding
	public static void main(String[] args) {
		if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
	}
}
