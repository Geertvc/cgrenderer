package sceneModel.sceneGraph;

import indexed_Neighbour_Triangle_Mesh_With_Edges.Mesh;
import indexed_Neighbour_Triangle_Mesh_With_Edges.Triangle;
import indexed_Neighbour_Triangle_Mesh_With_Edges.Vertex;

import java.util.ArrayList;
import java.util.List;

import ray_Tracing.Ray;
import abstractModel.Functions;
import abstractModel.IntersectionRecord;
import abstractModel.Point3f;

/**
 * Class representing a node in the BoundingBoxTree.
 * 
 * @author Geert Van Campenhout
 */
public class BoundingBoxTreeNode extends BoundingBox{

	AbstractBoundingBox leftChild = null;
	AbstractBoundingBox rightChild = null;
	
	/**
	 * Creates a new BoundingBox.
	 * 
	 * @param name	IT IS IMPORTANT TO GIVE THE BOUNDINGBOX THE EXACT SAME NAME AS THE NAME OF THE MESH OF THE CONTAINED TRIANGLES.
	 * @param min	The minimum coordinates of the BoundingBox in Point3f format.
	 * @param max	The maximal coordinates of the BoundingBox in Point3f format.
	 * @param AXIS	The int representing the AXIS on which the triangles needs to be sorted (0=x-Axis, 1=y-Axis, 2=z-Axis).
	 * @param triangleIndices	The Integer[] representing the containedTriangles.
	 * @param mesh	The mesh which contains the actual Triangle objects.
	 * @param currentLevelOfTree	The level of the tree this BoundingBoxTreeNode belongs to.
	 * @param numberOfTreeLevels	The maximal number of levels the Tree can have.
	 */
	public BoundingBoxTreeNode(String name, Point3f min, Point3f max, int AXIS, Integer[] triangleIndices, Mesh mesh, int currentLevelOfTree, int numberOfTreeLevels){
		super(name, min, max, triangleIndices);
		if(currentLevelOfTree < numberOfTreeLevels){
			int N = triangleIndices.length;
			Vertex[] vertex = mesh.getVertices();
			Triangle[] triangle = mesh.getTriangles();
			if(N<=0){
				throw new IllegalArgumentException("The list of containedTriangles is of length 0");
			} if(N==1){
				//De BoundingBoxTreeNode bevat al alles wat nodig is.
			} else if(N==2){
				Point3f[] triangle0Points = new Point3f[] {
						vertex[triangle[triangleIndices[0]].vertex[0]].coord,
						vertex[triangle[triangleIndices[0]].vertex[1]].coord,
						vertex[triangle[triangleIndices[0]].vertex[2]].coord};
				Point3f min0 = new Point3f();
				Point3f max0 = new Point3f();
				Functions.getExtremeXYZValues(triangle0Points, min0, max0);
				Point3f[] triangle1Points = new Point3f[] {
						vertex[triangle[triangleIndices[1]].vertex[0]].coord,
						vertex[triangle[triangleIndices[1]].vertex[1]].coord,
						vertex[triangle[triangleIndices[1]].vertex[2]].coord};
				Point3f min1 = new Point3f();
				Point3f max1 = new Point3f();
				Functions.getExtremeXYZValues(triangle1Points, min1, max1);
				setLeftChild(new BoundingBox(this.name, min0, max0, new Integer[] {triangleIndices[0]}));
				setRightChild(new BoundingBox(this.name, min1, max1, new Integer[] {triangleIndices[1]}));
			} else{
				float m;
				switch(AXIS){
					case 0:
						m = min.x + ((max.x-min.x)/2);
						break;
					case 1:
						m = min.y + ((max.y-min.y)/2);
						break;
					case 2:
						m = min.z + ((max.z-min.z)/2);
						break;
					default:
						throw new IllegalArgumentException("AXIS must be 0, 1 or 2 and thus cannot be " + AXIS);
				}
				Integer[][] partitionedList = partitionList(m, triangleIndices, AXIS, vertex, triangle);
				int newAxis = (AXIS+1)%3;
				Integer[] leftList = partitionedList[0];
				Integer[] rightList = partitionedList[1];
				//Left child
				if(leftList.length > 0){
					Point3f[] trianglePointsLeft = getPoints(leftList, vertex, triangle);
					Point3f minLeft = new Point3f();
					Point3f maxLeft = new Point3f();
					Functions.getExtremeXYZValues(trianglePointsLeft, minLeft, maxLeft);
					setLeftChild(new BoundingBoxTreeNode(this.name, minLeft, maxLeft, newAxis, leftList, mesh, currentLevelOfTree+1, numberOfTreeLevels));
				}
				//Right child
				if(rightList.length > 0){
					Point3f[] trianglePointsRight = getPoints(rightList, vertex, triangle);
					Point3f minRight = new Point3f();
					Point3f maxRight = new Point3f();
					Functions.getExtremeXYZValues(trianglePointsRight, minRight, maxRight);
					setRightChild(new BoundingBoxTreeNode(this.name, minRight, maxRight, newAxis, rightList, mesh, currentLevelOfTree+1, numberOfTreeLevels));
				}
			}
		} //else{ Tree is deep enough, leave left and right child null}
	}
	
	private Integer[][] partitionList(float m, Integer[] triangleIndicesToPartition, int AXIS, Vertex[] vertex, Triangle[] triangle){
		List<Integer> smallerThanM = new ArrayList<Integer>();
		List<Integer> greaterOrEqualToM = new ArrayList<Integer>();
		for (int i = 0; i < triangleIndicesToPartition.length; i++) {
			int index = triangleIndicesToPartition[i];
			Triangle t = triangle[index];
			switch(AXIS){
				case 0:
					if(Functions.getXCoordOfIncenterOfTriangle(vertex[t.vertex[0]].coord, vertex[t.vertex[1]].coord, vertex[t.vertex[2]].coord) < m){
						smallerThanM.add(index);
					} else{
						greaterOrEqualToM.add(index);
					}
					break;
				case 1:
					if(Functions.getYCoordOfIncenterOfTriangle(vertex[t.vertex[0]].coord, vertex[t.vertex[1]].coord, vertex[t.vertex[2]].coord) < m){
						smallerThanM.add(index);
					} else{
						greaterOrEqualToM.add(index);
					}
					break;
				case 2:
					if(Functions.getZCoordOfIncenterOfTriangle(vertex[t.vertex[0]].coord, vertex[t.vertex[1]].coord, vertex[t.vertex[2]].coord) < m){
						smallerThanM.add(index);
					} else{
						greaterOrEqualToM.add(index);
					}
					break;
			}
		}
		return new Integer[][] {smallerThanM.toArray(new Integer[0]), greaterOrEqualToM.toArray(new Integer[0])};
	}
	
	private Point3f[] getPoints(Integer[] triangleIndices, Vertex[] vertex, Triangle[] triangle) {
		Point3f[] points = new Point3f[triangleIndices.length*3];
		for (int i = 0; i < triangleIndices.length; i++) {
			int index = triangleIndices[i];
			Triangle t = triangle[index];
			points[i*3] = vertex[t.vertex[0]].coord;
			points[(i*3) + 1] = vertex[t.vertex[1]].coord;
			points[(i*3) + 2] = vertex[t.vertex[2]].coord;
		}
		return points;
	}

	private void setLeftChild(AbstractBoundingBox leftChild){
		this.leftChild = leftChild;
	}
	
	private void setRightChild(AbstractBoundingBox rightChild){
		this.rightChild = rightChild;
	}
	
	/* (non-Javadoc)
	 * @see sceneModel.sceneGraph.BoundingBox#hitContainedTriangles(ray_Tracing.Ray, float, float, indexed_Neighbour_Triangle_Mesh_With_Edges.Triangle[], indexed_Neighbour_Triangle_Mesh_With_Edges.Vertex[])
	 */
	@Override
	public IntersectionRecord hitContainedTriangles(Ray ray, float t0, float t1, Triangle[] triangles, Vertex[] vertices){
		if(hitBox(ray, t0, t1)){
			if(this.leftChild == null){
				if(this.rightChild == null){
					//This BoundingBoxTreeNode has no children so is basically a normal BoundingBox.
					return super.hitContainedTriangles(ray, t0, t1, triangles, vertices);
				} else{
					//RightChild != null, but leftChild is null.
					return this.rightChild.hitContainedTriangles(ray, t0, t1, triangles, vertices);
				}
			} else{
				if(this.rightChild == null){
					//LeftChild != null, but rightChild is null.
					return this.leftChild.hitContainedTriangles(ray, t0, t1, triangles, vertices);
				} else{
					//Both children are not null.
					IntersectionRecord leftHit = this.leftChild.hitContainedTriangles(ray, t0, t1, triangles, vertices);
					IntersectionRecord rightHit = this.rightChild.hitContainedTriangles(ray, t0, t1, triangles, vertices);
					if(leftHit == null){
						if(rightHit == null){
							return null;
						} else{
							return rightHit;
						}
					} else{
						if(rightHit == null){
							return leftHit;
						} else{
							return leftHit.currentT < rightHit.currentT ? leftHit : rightHit;
						}
					}
				}
			}
		} else{
			return null;
		}
			
	}
}
