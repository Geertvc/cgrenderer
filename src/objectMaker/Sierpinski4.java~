package objectMaker;

import java.util.ArrayList;


/**
 * The middle of the pyramid's ground plane is 0,0,0.
 * 
 * @author Thypo
 */
public class Sierpinski4 extends GeometricObject{
	float h;
	ArrayList<Pyramid> pyramids;
	
	public Sierpinski4(float scale, int indexOfObject, int depth){
		super(new Point3f[1], new int[1][3], indexOfObject);
		this.basicVertices = getTotalVertices(scale, depth);
		this.indices = getTotalIndices();
	}
	
	public Sierpinski4(float scale, int indexOfObject, int depth, Point3f offset){
		this(scale, indexOfObject, depth);
		this.translate(offset);
	}
	
	private Point3f[] getTotalVertices(float scale, int depth) {
		this.h = calcHeight(scale);
		this.pyramids = getPyramidsRecursive(depth, new Point3f(), scale);
		
		int nbofverticesinPyramid = this.pyramids.get(0).basicVertices.length;
		Point3f[] totalVertices = new Point3f[this.pyramids.size()*nbofverticesinPyramid];
		for (int i = 0; i < this.pyramids.size(); i++) {
			Pyramid curPyramid = this.pyramids.get(i);
			for (int j = 0; j < nbofverticesinPyramid; j++) {
				totalVertices[i*nbofverticesinPyramid + j] = curPyramid.basicVertices[j];
			}
		}
		return totalVertices;
	}

	private int[][] getTotalIndices() {
		
		for (int i = 0; i < this.pyramids.size(); i++) {
			this.pyramids.get(i).setIndexOfObject(i);
		}
		
		int[][] basicIndices = Pyramid.getIndices();
		int nbOfIndices = basicIndices.length;
		int nbofverticesinPyramid = this.pyramids.get(0).basicVertices.length;
		System.out.println("indices: " + nbofverticesinPyramid);
		this.indices = new int[this.pyramids.size()*nbOfIndices][3];
		for (int i = 0; i < this.pyramids.size(); i++) {
			for (int j = 0; j < basicIndices.length; j++) {
				this.indices[i*nbOfIndices+j][0] = basicIndices[j][0] + i*nbofverticesinPyramid;
				this.indices[i*nbOfIndices+j][1] = basicIndices[j][1] + i*nbofverticesinPyramid;
				this.indices[i*nbOfIndices+j][2] = basicIndices[j][2] + i*nbofverticesinPyramid;
			}
		}
		return this.indices;
	}
	
	private ArrayList<Pyramid> getPyramidsRecursive(int level, Point3f startPoint, float scale){
		ArrayList<Pyramid> list = new ArrayList<Pyramid>();
		if(level == 0){
			list.add(new Pyramid(scale, 0, startPoint));
		} else{
			Point3f newStart = new Point3f(startPoint);
			//The one that starts at the same point.
			list.addAll(getPyramidsRecursive(level-1, newStart, scale/2));
			
			newStart.addSet(startPoint, new Point3f(scale/2, 0, 0));
			list.addAll(getPyramidsRecursive(level-1, newStart, scale/2));
			
			newStart.addSet(startPoint, new Point3f(0, scale/2, 0));
			list.addAll(getPyramidsRecursive(level-1, newStart, scale/2));
			
			newStart.addSet(startPoint, new Point3f(scale/2, scale/2, 0));
			list.addAll(getPyramidsRecursive(level-1, newStart, scale/2));
			
			newStart.addSet(startPoint, new Point3f(scale/4, scale/4, scale*h/2));
			list.addAll(getPyramidsRecursive(level-1, newStart, scale/2));
		}
		return list;
	}


	private static float calcHeight(float l) {
		return (float) (l/Math.sqrt(2.0));
	}
}
