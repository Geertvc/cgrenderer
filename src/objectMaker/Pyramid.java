package objectMaker;

public class Pyramid extends GeometricObject{
	float l;
	float h;
	
	public Pyramid(float l, int indexOfObject){
		super(getBasicVertices(l, calcHeight(l)), getIndices(), indexOfObject);
		this.h = calcHeight(l);
	}
	
	public Pyramid(float l, int indexOfObject, Point3f offset){
		this(l, indexOfObject);
		this.translate(offset);
	}

	private static float calcHeight(float l) {
		return (float) (l/Math.sqrt(2.0));
	}

	public static int[][] getIndices() {
		int[][] indices = new int[6][3];
		indices[0] = new int[] {0,1,2};
		indices[1] = new int[] {0,3,1};
		indices[2] = new int[] {0,2,4};
		indices[3] = new int[] {2,1,4};
		indices[4] = new int[] {1,3,4};
		indices[5] = new int[] {3,0,4};
		return indices;
	}

	public static Point3f[] getBasicVertices(float l, float h) {
		float halfL = l/2;
		Point3f[] basicVertices = new Point3f[5];
		basicVertices[0] = new Point3f(0,  0, 0);
		basicVertices[1] = new Point3f(l,  l, 0);
		basicVertices[2] = new Point3f(l,  0, 0);
		basicVertices[3] = new Point3f(0,  l, 0);
		basicVertices[4] = new Point3f(halfL,  halfL, h);
		return basicVertices;
	}
}
