import java.awt.Color;


public class SeamCarver {

	private final static double borderEnergy = 195075;
	private byte[][] r;
	private byte[][] g;
	private byte[][] b;
	private int width;
	private int height;
	
	public SeamCarver(Picture picture) {
		height = picture.height();
		width = picture.width();

		r = new byte[height][width];
		g = new byte[height][width];
		b = new byte[height][width];
		
		for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
            	r[y][x] = (byte)(picture.get(x, y).getRed()-128);
            	g[y][x] = (byte)(picture.get(x, y).getGreen()-128);
            	b[y][x] = (byte)(picture.get(x, y).getBlue()-128);
            }
	}               // create a seam carver object based on the given picture
		   
	public Picture picture() {
		Picture picture =  new Picture(width,height);
		for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
            	picture.set(x, y, new Color((int)(r[y][x]+128),(int)(g[y][x]+128),(int)(b[y][x]+128)));
            }
		return picture;
	}                        // current picture
		   
	public int width() {
		return this.width;
	}                           // width of current picture
		   
	public int height() {
		return this.height;
	}                          // height of current picture
		   
	public double energy(int x, int y)  {
		double energy = 0;
		
		if(x<0 || x>width-1 || y<0 || y>height-1) throw new IndexOutOfBoundsException();
		if(x == 0 || x==width-1 || y==0 || y==height-1) energy = borderEnergy;
		else {
			energy = Math.pow( ( (int)(r[y][x-1]+128) - (int)(r[y][x+1]+128) ),2) +
					 Math.pow( ( (int)(g[y][x-1]+128) - (int)(g[y][x+1]+128) ),2) +
					 Math.pow( ( (int)(b[y][x-1]+128) - (int)(b[y][x+1]+128) ),2) +
					 Math.pow( ( (int)(r[y-1][x]+128) - (int)(r[y+1][x]+128) ),2) +
					 Math.pow( ( (int)(g[y-1][x]+128) - (int)(g[y+1][x]+128) ),2) +
					 Math.pow( ( (int)(b[y-1][x]+128) - (int)(b[y+1][x]+128) ),2);
		}
		return energy;
	}             // energy of pixel at column x and row y
	
	private void initEnergy2DArray(double[][] energy2D){
		for(int i=0;i<height;i++)
			for(int j=0;j<width;j++) {
				energy2D[i][j] = energy(j,i);
			}
	}
	
	private int oneDindex (int rowIndex, int colIndex) {
		return rowIndex*width+colIndex+1;
	}
	
	private void relax (int v, int w, double weight, int[] preVertex,double[] distanceTo) {
		if(distanceTo[w] > distanceTo[ v ] + weight) {
			distanceTo[w] = distanceTo[ v ] + weight;
			preVertex[w] = v;
		}
	}
	
	private void visit2Dvertex(int rowIndex, int colIndex, double[][] energy2D,int[] preVertex,double[] distanceTo){
		if(rowIndex == height-1) {
			relax(oneDindex(rowIndex,colIndex),distanceTo.length-1,0,preVertex, distanceTo);
		}
		else {
			if(colIndex-1>=0)    relax(oneDindex(rowIndex,colIndex),oneDindex(rowIndex+1,colIndex-1),energy2D[rowIndex+1][colIndex-1],preVertex,distanceTo);
			                     relax(oneDindex(rowIndex,colIndex),oneDindex(rowIndex+1,colIndex),energy2D[rowIndex+1][colIndex],preVertex,distanceTo);
			if(colIndex+1<width) relax(oneDindex(rowIndex,colIndex),oneDindex(rowIndex+1,colIndex+1),energy2D[rowIndex+1][colIndex+1],preVertex,distanceTo);
		}
	}
	
	private void traceBack(int[]sp, int[]preVertex){
		int curVertext = height*width+1;
		for(int i=0;i<height;i++){
			sp[height-i-1]=(preVertex[curVertext]-1) % width;
			curVertext = preVertex[curVertext];
		}
	}
	
	private void transPose(){
		byte[][] newR = new byte[width][height];
		byte[][] newG = new byte[width][height];
		byte[][] newB = new byte[width][height];
		int temp;
		for(int i=0;i<height;i++)
			for(int j=0;j<width;j++) {
				newR[j][i] = r[i][j];
				newG[j][i] = g[i][j];
				newB[j][i] = b[i][j];
			}
		r = newR;
		g = newG;
		b = newB;
		temp = height;
		height = width;
		width = temp;
	}

		   
	public int[] findHorizontalSeam() {
		int[] sp;
		transPose();
		sp = findVerticalSeam();
		transPose();
		return sp;
	}              // sequence of indices for horizontal seam
		   
	public int[] findVerticalSeam() {
		double[][] energy2D = new double[height][width];
		int[] preVertex = new int[height*width+2];
		double[] distanceTo = new double[height*width+2];
		int[] sp=new int[height];
		
		for(int i=0;i<distanceTo.length;i++) {
			distanceTo[i] = Double.POSITIVE_INFINITY;
		}
		distanceTo[0] = 0.0;
		initEnergy2DArray(energy2D);
		
		//visit virtual start point
		for(int j=0;j<width;j++) {
			int oneDindex = oneDindex(0,j);
			distanceTo[ oneDindex ] = energy2D[0][j];
			preVertex[ oneDindex ] = 0;
		}
		
		for(int col = width-1;col>=0;col--) 
			for(int row =0;row<height;row++) {
				int i=row;
				int j=col+i;
				if(i>height-1 || j>width-1) break;
				visit2Dvertex(i, j, energy2D, preVertex, distanceTo);
			}
		
		for(int row=1;row<height;row++)
			for(int col=0;col<width;col++) {
				int j = col;
				int i = row + col;
				if(i>height-1 || j>width-1) break;
				visit2Dvertex(i, j, energy2D, preVertex, distanceTo);
			}
		traceBack(sp,preVertex);
		//StdOut.println("arrive!");
		return sp;
	}                // sequence of indices for vertical seam
	
	private boolean isValidVerticalSeam(int[] seam){
		if(seam.length!=height) return false;
		for(int i=0;i<seam.length;i++) {
			if(seam[i]<0 || seam[i]>width-1) return false;
			if(i>0 && (Math.abs(seam[i]-seam[i-1]))>1) return false;
		}
		return true;
	}
	
	private boolean isValidHorizontalSeam(int[] seam) {
		if(seam.length!=width) return false;
		for(int i=0;i<seam.length;i++) {
			if(seam[i]<0 || seam[i]>height-1) return false;
			if(i>0 && (Math.abs(seam[i]-seam[i-1]))>1) return false;
		}
		return true;
	}
	
	private void verticalRemovePixelFrom2DArray(int r, int c){
		for(int i=r+1;i<height;i++) {
			this.r[i-1][c] = this.r[i][c];
			g[i-1][c] = g[i][c];
			b[i-1][c] = b[i][c];
		}
	}
	
	private void horizontalRemovePixelFrom2DArray(int r, int c){
		for(int i=c+1;i<width;i++) {
			this.r[r][i-1] = this.r[r][i];
			g[r][i-1] = g[r][i];
			b[r][i-1] = b[r][i];
		}
	}
		   
	public void removeHorizontalSeam(int[] seam) {
		if(seam == null) throw new NullPointerException();
		if(!isValidHorizontalSeam(seam)) throw new IllegalArgumentException();
		for(int i=0;i<seam.length;i++) {
			verticalRemovePixelFrom2DArray(seam[i],i);
		}
		height--;
	}  // remove horizontal seam from current picture
		   
	public void removeVerticalSeam(int[] seam) {
		if(seam == null) throw new NullPointerException();
		if(!isValidVerticalSeam(seam)) throw new IllegalArgumentException();
		for(int i=0;i<seam.length;i++) {
			horizontalRemovePixelFrom2DArray(i,seam[i]);
		}
		width--;
	}    // remove vertical seam from current picture
}