package objectMaker;

public class Cube extends GeometricObject{
	float l;
	
	public Cube(float l, int indexOfObject){
		super(getBasicVertices(l), getIndices(), indexOfObject);
	}
	
	public Cube(float l, int indexOfObject, Point3f offset){
		this(l, indexOfObject);
		this.translate(offset);
	}

	public static int[][] getIndices() {
		int[][] indices = new int[12][3];
		//Bovenvlak
		indices[0] = new int[] {3,7,5};
		indices[1] = new int[] {3,5,1};
		//Rechtervlak
		indices[2] = new int[] {6,4,5};
		indices[3] = new int[] {6,5,7};
		//Linkervlak
		indices[4] = new int[] {2,3,1};
		indices[5] = new int[] {2,1,0};
		//Achtervlak
		indices[6] = new int[] {0,5,4};
		indices[7] = new int[] {0,1,5};
		//Voorvlak
		indices[8] = new int[] {2,6,7};
		indices[9] = new int[] {2,7,3};
		//ondervlak
		indices[10] = new int[] {0,4,6};
		indices[11] = new int[] {0,6,2};
		return indices;
	}

	public static Point3f[] getBasicVertices(float l) {
		Point3f[] basicVertices = new Point3f[8];
		basicVertices[0] = new Point3f(0,  0, 0);
		basicVertices[1] = new Point3f(0,  0, l);
		basicVertices[2] = new Point3f(0,  l, 0);
		basicVertices[3] = new Point3f(0,  l, l);
		basicVertices[4] = new Point3f(l,  0, 0);
		basicVertices[5] = new Point3f(l,  0, l);
		basicVertices[6] = new Point3f(l,  l, 0);
		basicVertices[7] = new Point3f(l,  l, l);
		return basicVertices;
	}
}
