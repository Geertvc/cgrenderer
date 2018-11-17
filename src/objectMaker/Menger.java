package objectMaker;

import java.util.ArrayList;


/**
 * The middle of menger's ground plane is 0,0,0.
 * 
 * @author Thypo
 */
public class Menger extends GeometricObject{
	float h;
	ArrayList<Cube> cubes;
	
	public Menger(float scale, int indexOfObject, int depth){
		super(new Point3f[1], new int[1][3], indexOfObject);
		this.basicVertices = getTotalVertices(scale, depth);
		this.indices = getTotalIndices();
	}
	
	public Menger(float scale, int indexOfObject, int depth, Point3f offset){
		this(scale, indexOfObject, depth);
		this.translate(offset);
	}
	
	private Point3f[] getTotalVertices(float scale, int depth) {
		this.h = calcHeight(scale);
		this.cubes = getCubesRecursive(depth, new Point3f(), scale);
		
		int nbofverticesinCube = this.cubes.get(0).basicVertices.length;
		Point3f[] totalVertices = new Point3f[this.cubes.size()*nbofverticesinCube];
		for (int i = 0; i < this.cubes.size(); i++) {
			Cube curCube = this.cubes.get(i);
			for (int j = 0; j < nbofverticesinCube; j++) {
				totalVertices[i*nbofverticesinCube + j] = curCube.basicVertices[j];
			}
		}
		return totalVertices;
	}

	private int[][] getTotalIndices() {
		
		for (int i = 0; i < this.cubes.size(); i++) {
			this.cubes.get(i).setIndexOfObject(i);
		}
		
		int[][] basicIndices = Cube.getIndices();
		int nbOfIndices = basicIndices.length;
		int nbofverticesinCube = this.cubes.get(0).basicVertices.length;
		System.out.println("indices: " + nbofverticesinCube);
		this.indices = new int[this.cubes.size()*nbOfIndices][3];
		for (int i = 0; i < this.cubes.size(); i++) {
			for (int j = 0; j < basicIndices.length; j++) {
				this.indices[i*nbOfIndices+j][0] = basicIndices[j][0] + i*nbofverticesinCube;
				this.indices[i*nbOfIndices+j][1] = basicIndices[j][1] + i*nbofverticesinCube;
				this.indices[i*nbOfIndices+j][2] = basicIndices[j][2] + i*nbofverticesinCube;
			}
		}
		return this.indices;
	}
	
	private ArrayList<Cube> getCubesRecursive(int level, Point3f startPoint, float scale){
		ArrayList<Cube> list = new ArrayList<Cube>();
		if(level == 0){
			list.add(new Cube(scale, 0, startPoint));
		} else{
			Point3f newStart = new Point3f(startPoint);
			/** The ground plane. */
			//The one that starts at the same point.
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			newStart.addSet(startPoint, new Point3f(scale/3, 0, 0));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			newStart.addSet(startPoint, new Point3f(2*scale/3, 0, 0));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			newStart.addSet(startPoint, new Point3f(0, scale/3, 0));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			newStart.addSet(startPoint, new Point3f(2*scale/3, scale/3, 0));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			newStart.addSet(startPoint, new Point3f(0, 2*scale/3, 0));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			newStart.addSet(startPoint, new Point3f(scale/3, 2*scale/3, 0));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			newStart.addSet(startPoint, new Point3f(2*scale/3, 2*scale/3, 0));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			/** The second plane. */
			newStart.addSet(startPoint, new Point3f(0, 0, scale/3));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			newStart.addSet(startPoint, new Point3f(2*scale/3, 0, scale/3));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			newStart.addSet(startPoint, new Point3f(0, 2*scale/3, scale/3));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			newStart.addSet(startPoint, new Point3f(2*scale/3, 2*scale/3, scale/3));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			/** The third plane. */
			newStart.addSet(startPoint, new Point3f(0, 0, 2*scale/3));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			newStart.addSet(startPoint, new Point3f(scale/3, 0, 2*scale/3));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			newStart.addSet(startPoint, new Point3f(2*scale/3, 0, 2*scale/3));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			newStart.addSet(startPoint, new Point3f(0, scale/3, 2*scale/3));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			newStart.addSet(startPoint, new Point3f(2*scale/3, scale/3, 2*scale/3));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			newStart.addSet(startPoint, new Point3f(0, 2*scale/3, 2*scale/3));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			newStart.addSet(startPoint, new Point3f(scale/3, 2*scale/3, 2*scale/3));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
			newStart.addSet(startPoint, new Point3f(2*scale/3, 2*scale/3, 2*scale/3));
			list.addAll(getCubesRecursive(level-1, newStart, scale/3));
			
		}
		return list;
	}


	private static float calcHeight(float l) {
		return (float) (l/Math.sqrt(2.0));
	}
}
