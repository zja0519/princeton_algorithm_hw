
public class RtableList {
	private Node first;
	private static final int R = 256;
    
	private static class Node {
        private char item;
        private Node next;

        Node(char item, Node next) { this.item = item; this.next = next; }
    }
	
	private void move2front(Node curNode) {
		Node temp;
		if(curNode.next!=null) {
        	temp = curNode.next;
        	curNode.next = curNode.next.next;
        	temp.next = first.next;
        	first.next = temp;
    	}
	}
    
    public RtableList() {
    	Node curNode;
    	first = new Node('0',null);
    	curNode = first;
    	for(int i=0;i<R;i++){
    		curNode.next = new Node((char)i,null);
    		curNode = curNode.next;
    	}
    }
    
    public int move2front(char c) {
    	Node curNode = first;
    	int i = 0;
    	while(curNode.next!=null && curNode.next.item!=c) {
    		curNode=curNode.next;
    		i++;
    	}
    	move2front(curNode);
    	return i;
    }
    
    public char move2front(int index) {
    	Node curNode = first;
    	char c;
    	while(index>0) {
    		curNode=curNode.next;
    		index--;
    	}
    	c = curNode.next.item;
    	move2front(curNode);
    	return c;
    }
}
