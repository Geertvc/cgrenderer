package objectMaker;

public class GeometricObject {
	public Point3f[] basicVertices;
	public int[][] indices;
	private int indexOfObject;
	
	public GeometricObject(Point3f[] basicVertices, int[][] indices, int indexOfObject){
		this.basicVertices = basicVertices;
		this.indices = indices;
		this.indexOfObject = indexOfObject;
		for (int i = 0; i < indices.length; i++) {
			int[] mesh = indices[i];
			for (int j = 0; j < mesh.length; j++) {
				mesh[j] += indexOfObject*basicVertices.length;
			}
		}
	}
	
	public GeometricObject(Point3f[] basicVertices, int[][] indices, int indexOfObject, Point3f offset){
		this(basicVertices, indices, indexOfObject);
		this.translate(offset);
	}
	
	public void translate(float x, float y, float z){
		Point3f point;
		for (int i = 0; i < basicVertices.length; i++) {
			point = basicVertices[i];
			point.x += x;
			point.y += y;
			point.z += z;
		}
	}
	
	public void translate(Point3f point){
		translate(point.x, point.y, point.z);
	}
	
	public void setIndexOfObject(int indexOfObject){
		int formerIndex = this.indexOfObject;
		int nbOfVertices = this.basicVertices.length;
		for (int i = 0; i < indices.length; i++) {
			int[] mesh = indices[i];
			for (int j = 0; j < mesh.length; j++) {
				mesh[j] += (indexOfObject*nbOfVertices) - (formerIndex*nbOfVertices);
			}
		}
		this.indexOfObject = indexOfObject;
	}
	
	public int getIndexOfObject(){
		return this.indexOfObject;
	}
}
