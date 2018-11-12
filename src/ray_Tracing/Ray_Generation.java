package ray_Tracing;

import main.Constants;
import abstractModel.Point2f;
import abstractModel.Point3f;
import abstractModel.Vector3f;

/**
 * Class that can generate the rays needed to use ray tracing for an image.
 * 
 * @author Geert Van Campenhout
 * @version 2.1
 */
public class Ray_Generation {
	
	Point3f origin;
	int nbOfHorizontalPixels;
	int nbOfVerticalPixels;
	float positionLeftEdgeImage;
	float positionRightEdgeImage;
	float positionTopEdgeImage;
	float positionBottomEdgeImage;
	float distanceToScreen;
	
	/**
	 * Initializes the Ray_Generation object with the given parameters used to generate rays for an image.
	 * 
	 * @param origin	The camera position.
	 * @param nbOfHorizontalPixels	The number of horizontal pixels in the image.
	 * @param nbOfVerticalPixels	The number of vertical pixels in the image.
	 * @param positionLeftEdgeImage	The position of the left edge of the image.
	 * @param positionRightEdgeImage	The position of the right edge of the image.
	 * @param positionTopEdgeImage	The position of the top edge of the image.
	 * @param positionBottomEdgeImage	The position of the bottom edge of the image.
	 * @param distanceToScreen	The distance from the camera position to the screen.
	 */
	public Ray_Generation(Point3f origin, int nbOfHorizontalPixels, int nbOfVerticalPixels, 
			float positionLeftEdgeImage, float positionRightEdgeImage, float positionTopEdgeImage, float positionBottomEdgeImage, float distanceToScreen){
		this.origin = origin;
		this.nbOfHorizontalPixels = nbOfHorizontalPixels;
		this.nbOfVerticalPixels = nbOfVerticalPixels;
		this.positionLeftEdgeImage = positionLeftEdgeImage;
		this.positionRightEdgeImage = positionRightEdgeImage;
		this.positionBottomEdgeImage = positionBottomEdgeImage;
		this.positionTopEdgeImage = positionTopEdgeImage;
		this.distanceToScreen = distanceToScreen;
	}
	
	/** Calculates the coordinates of the pixel at position (i,j) in the raster image. 
	 * 
	 * @param i the x coordinate in pixels
	 * @param j the y coordinate in pixels
	 * @return	Points2f
	 * 		The coordinates of the pixel.
	 */
	public Point2f calcPositionInRasterImage(float i, float j){
		float u = this.positionLeftEdgeImage+((this.positionRightEdgeImage-this.positionLeftEdgeImage)*i/this.nbOfHorizontalPixels);
		float v = this.positionBottomEdgeImage+((this.positionTopEdgeImage-this.positionBottomEdgeImage)*j/this.nbOfVerticalPixels);
		return new Point2f(u,v);
	}
	
	/**
	 * Creates an array containing all parallel rays going through all the pixels in the raster image.
	 * 
	 * @param origin	The origin to calculate with
	 * @param uAxis		The local axis to calculate with. perpendicular to vAxis and wAxis
	 * @param vAxis		The local axis to calculate with. perpendicular to uAxis and wAxis
	 * @param wAxis		The local axis to calculate with. perpendicular to uAxis and vAxis
	 * @return	Ray[][]
	 * 		Ray[i][j] is the ray going through pixel (i,j).
	 */
	public Ray[][] createParallelProjectionRays(Point3f origin,Vector3f uAxis, Vector3f vAxis ,Vector3f wAxis){
		Ray ray[][] = new Ray[this.nbOfHorizontalPixels][this.nbOfVerticalPixels];
		Point2f coord;
		Point3f rayOrigin = new Point3f();
		Vector3f rayDirection = new Vector3f();
		for (int i = 0; i < this.nbOfHorizontalPixels; i++) {
			for (int j = 0; j < this.nbOfVerticalPixels; j++) {
				coord = calcPositionInRasterImage((i+0.5f),(j+0.5f));
				rayOrigin.scaleAdd(coord.x, uAxis, origin);
				rayOrigin.scaleAdd(coord.y, vAxis, rayOrigin);
				rayDirection.negate(wAxis);
				rayDirection.scale(1/rayDirection.length());
				ray[i][j] = new Ray(new Point3f(rayOrigin), new Vector3f(rayDirection));
			}
		}
		return ray;
	}
	
	/**
	 * Creates an array containing all perspective rays going through all the pixels in the raster image and through the given origin.
	 * 
	 * @param origin	The origin all the rays go through
	 * @param uAxis		The local axis to calculate with. perpendicular to vAxis and wAxis
	 * @param vAxis		The local axis to calculate with. perpendicular to uAxis and wAxis
	 * @param wAxis		The local axis to calculate with. perpendicular to uAxis and vAxis
	 * @return	Ray[][]
	 * 		Ray[i][j] is the ray going through pixel (i,j) and origin.
	 */
	public Ray[][] createPerspectiveProjectionRays(Point3f origin,Vector3f uAxis, Vector3f vAxis ,Vector3f wAxis){
		Ray[][] rays = new Ray[this.nbOfHorizontalPixels][this.nbOfVerticalPixels];
		Point2f coord;
		Vector3f rayDirection = new Vector3f();
		for (int i = 0; i < this.nbOfHorizontalPixels; i++) {
			for (int j = 0; j < this.nbOfVerticalPixels; j++) {
				coord = calcPositionInRasterImage((i+0.5f),(j+0.5f));
				rayDirection.scaleSet(-distanceToScreen, wAxis);
				rayDirection.scaleAdd(coord.x, uAxis, rayDirection);
				rayDirection.scaleAdd(coord.y, vAxis, rayDirection);
				rayDirection.scale(1/rayDirection.length());
				rays[i][j] = new Ray(new Point3f(origin),new Vector3f(rayDirection));
			}
		}
		return rays;
	}
	
	/**
	 * Creates an array containing the parallel rays going through all the pixels in the given row of the raster image.
	 * 
	 * @param row	The row of the image to calculate the rays for.
	 * @param origin	The origin to calculate with
	 * @param uAxis		The local axis to calculate with. perpendicular to vAxis and wAxis
	 * @param vAxis		The local axis to calculate with. perpendicular to uAxis and wAxis
	 * @param wAxis		The local axis to calculate with. perpendicular to uAxis and vAxis
	 * @return	Ray[]
	 * 		Ray[i] is the ray going through pixel (i,row).
	 */
	public Ray[] createParallelProjectionRays(int row, Point3f origin,Vector3f uAxis, Vector3f vAxis ,Vector3f wAxis){
		Ray[] rays = new Ray[this.nbOfHorizontalPixels];
		Point2f coord;
		Point3f rayOrigin = new Point3f();
		Vector3f rayDirection = new Vector3f();
		for (int i = 0; i < this.nbOfHorizontalPixels; i++) {
			coord = calcPositionInRasterImage((i+0.5f),(row+0.5f));
			rayOrigin.scaleAdd(coord.x, uAxis, origin);
			rayOrigin.scaleAdd(coord.y, vAxis, rayOrigin);
			rayDirection.negate(wAxis);
			rayDirection.scale(1/rayDirection.length());
			rays[i] = new Ray(new Point3f(rayOrigin), new Vector3f(rayDirection));
		}
		return rays;
	}

	/**
	 * Creates an array containing all perspective rays going through all the pixels in the given row of the raster image and through the given origin.
	 * 
	 * @param row	The given row of the image to calculate the rays for.
	 * @param origin	The origin all the rays go through
	 * @param uAxis		The local axis to calculate with. perpendicular to vAxis and wAxis
	 * @param vAxis		The local axis to calculate with. perpendicular to uAxis and wAxis
	 * @param wAxis		The local axis to calculate with. perpendicular to uAxis and vAxis
	 * @return	Ray[]
	 * 		Ray[i] is the ray going through pixel (i,row) and origin.
	 */
	public Ray[] createPerspectiveProjectionRays(int row, Point3f position,
			Vector3f uAxis, Vector3f vAxis, Vector3f wAxis) {
		Ray[] rays = new Ray[this.nbOfHorizontalPixels];
		Point2f coord;
		Vector3f rayDirection = new Vector3f();
		for (int i = 0; i < this.nbOfHorizontalPixels; i++) {
			coord = calcPositionInRasterImage((i+0.5f),(row+0.5f));
			rayDirection.scaleSet(-distanceToScreen, wAxis);
			rayDirection.scaleAdd(coord.x, uAxis, rayDirection);
			rayDirection.scaleAdd(coord.y, vAxis, rayDirection);
			rays[i] = new Ray(new Point3f(origin),new Vector3f(rayDirection));
		}
		return rays;
	}
	
	/**
	 * Creates an array containing the parallel rays going through all the pixels in the given row of the raster image with samples for antialiasing.
	 * 
	 * @param row	The row of the image to calculate the rays for.
	 * @param origin	The origin to calculate with
	 * @param uAxis		The local axis to calculate with. perpendicular to vAxis and wAxis
	 * @param vAxis		The local axis to calculate with. perpendicular to uAxis and wAxis
	 * @param wAxis		The local axis to calculate with. perpendicular to uAxis and vAxis
	 * @return	Ray[][]
	 * 		Ray[i][j] is the ray going through pixel (i,row) as sample j for the anti aliasing.
	 */
	public Ray[][] createParallelProjectionRaysUsingAntiAliasing(int row, Point3f origin,Vector3f uAxis, Vector3f vAxis ,Vector3f wAxis){
		Ray[][] rays = new Ray[this.nbOfHorizontalPixels][Constants.ANTIALIASING];
		Point2f coord;
		Point3f rayOrigin = new Point3f();
		Vector3f rayDirection = new Vector3f();
		for (int i = 0; i < this.nbOfHorizontalPixels; i++) {
			for (int p = 0; p < Constants.ANTIALIASINGPERROW; p++) {
				for (int q = 0; q < Constants.ANTIALIASINGPERROW; q++) {
					double randomValueX = Math.random();
					double randomValueY = Math.random();
					float x = (float) (i + ((p + randomValueX)/Constants.ANTIALIASINGPERROW));
					float y = (float) (row + ((q + randomValueY)/Constants.ANTIALIASINGPERROW));
					coord = calcPositionInRasterImage(x,y);
					rayOrigin.scaleAdd(coord.x, uAxis, origin);
					rayOrigin.scaleAdd(coord.y, vAxis, rayOrigin);
					rayDirection.negate(wAxis);
					rayDirection.scale(1/rayDirection.length());
					rays[i][(p*Constants.ANTIALIASINGPERROW)+q] = new Ray(new Point3f(rayOrigin), new Vector3f(rayDirection));
				}
			}
		}
		return rays;
	}

	/**
	 * Creates an array containing all perspective rays going through all the pixels in the given row of the raster image and through the given origin 
	 * with for each pixel different samples for antialiasing.
	 * 
	 * @param row	The given row of the image to calculate the rays for.
	 * @param origin	The origin all the rays go through
	 * @param uAxis		The local axis to calculate with. perpendicular to vAxis and wAxis
	 * @param vAxis		The local axis to calculate with. perpendicular to uAxis and wAxis
	 * @param wAxis		The local axis to calculate with. perpendicular to uAxis and vAxis
	 * @return	Ray[][]
	 * 		Ray[i][j] is the ray going through pixel (i,row) and origin as jth sample for the antialiasing.
	 */
	public Ray[][] createPerspectiveProjectionRaysUsingAntiAliasing(int row, Point3f position,
			Vector3f uAxis, Vector3f vAxis, Vector3f wAxis) {
		Ray[][] rays = new Ray[this.nbOfHorizontalPixels][Constants.ANTIALIASING];
		Point2f coord;
		Vector3f rayDirection = new Vector3f();
		for (int i = 0; i < this.nbOfHorizontalPixels; i++) {
			for (int p = 0; p < Constants.ANTIALIASINGPERROW; p++) {
				for (int q = 0; q < Constants.ANTIALIASINGPERROW; q++) {
					double randomValueX = Math.random();
					double randomValueY = Math.random();
					float x = (float) (i + ((p + randomValueX)/Constants.ANTIALIASINGPERROW));
					float y = (float) (row + ((q + randomValueY)/Constants.ANTIALIASINGPERROW));
					coord = calcPositionInRasterImage(x,y);
					rayDirection.scaleSet(-distanceToScreen, wAxis);
					rayDirection.scaleAdd(coord.x, uAxis, rayDirection);
					rayDirection.scaleAdd(coord.y, vAxis, rayDirection);
					rays[i][(p*Constants.ANTIALIASINGPERROW)+q] = new Ray(new Point3f(origin),new Vector3f(rayDirection));
				}
			}
		}
		return rays;
	}
	
	/**
	 * Creates an array containing all perspective rays going through all the pixels in the given row of the raster image and through the given origin
	 * with samples for each pixel for the depth of field effect and samples for each pixel for anitaliasing.
	 * 
	 * @param row	The given row of the image to calculate the rays for.
	 * @param origin	The origin all the rays go through
	 * @param uAxis		The local axis to calculate with. perpendicular to vAxis and wAxis
	 * @param vAxis		The local axis to calculate with. perpendicular to uAxis and wAxis
	 * @param wAxis		The local axis to calculate with. perpendicular to uAxis and vAxis
	 * @return	Ray[][][]
	 * 		Ray[d][i][j] is the dth ray (for depth of field effect) going through pixel (i,row) and origin as jth sample for the antialiasing.
	 */
	public Ray[][] createPerspectiveProjectionRaysUsingDepthOfField(int row, Point3f position,
			Vector3f uAxis, Vector3f vAxis, Vector3f wAxis) {
		Ray[][] rays = new Ray[this.nbOfHorizontalPixels][Constants.SAMPLESPERPIXEL];
		Point2f coord;
		Vector3f rayDirection = new Vector3f();
		System.out.println(this.origin);
		for (int i = 0; i < this.nbOfHorizontalPixels; i++) {
			coord = calcPositionInRasterImage((i+0.5f),(row+0.5f));
			rayDirection.scaleSet(-distanceToScreen, wAxis);
			rayDirection.scaleAdd(coord.x, uAxis, rayDirection);
			rayDirection.scaleAdd(coord.y, vAxis, rayDirection);
			Ray normalRay = new Ray(this.origin, new Vector3f(rayDirection));
			Point3f focusPoint = calculateFocusPoint(normalRay, wAxis, Constants.DISTANCETOLENS);
			for (int d = 0; d < Constants.SAMPLESPERPIXEL; d++) {
				coord = calcPositionInRasterImage((i+0.5f),(row+0.5f));
				rayDirection.scaleSet(-distanceToScreen, wAxis);
				rayDirection.scaleAdd(coord.x, uAxis, rayDirection);
				rayDirection.scaleAdd(coord.y, vAxis, rayDirection);
				rays[i][d] = getPerturbatedRayWithFocusPoint(focusPoint, this.origin, uAxis, vAxis);
			}
		}
		return rays;
	}
	
	/**
	 * Creates an array containing all perspective rays going through all the pixels in the given row of the raster image and through the given origin
	 * with samples for each pixel for the depth of field effect and samples for each pixel for anitaliasing.
	 * 
	 * @param row	The given row of the image to calculate the rays for.
	 * @param origin	The origin all the rays go through
	 * @param uAxis		The local axis to calculate with. perpendicular to vAxis and wAxis
	 * @param vAxis		The local axis to calculate with. perpendicular to uAxis and wAxis
	 * @param wAxis		The local axis to calculate with. perpendicular to uAxis and vAxis
	 * @return	Ray[][][]
	 * 		Ray[d][i][j] is the dth ray (for depth of field effect) going through pixel (i,row) and origin as jth sample for the antialiasing.
	 */
	public Ray[][][] createPerspectiveProjectionRaysUsingDepthOfFieldAndAntiAliasing(int row, Point3f position,
			Vector3f uAxis, Vector3f vAxis, Vector3f wAxis) {
		Ray[][][] rays = new Ray[this.nbOfHorizontalPixels][Constants.SAMPLESPERPIXEL][Constants.ANTIALIASING];
		Point2f coord;
		Vector3f rayDirection = new Vector3f();
		for (int i = 0; i < this.nbOfHorizontalPixels; i++) {
			coord = calcPositionInRasterImage((i+0.5f),(row+0.5f));
			rayDirection.scaleSet(-distanceToScreen, wAxis);
			rayDirection.scaleAdd(coord.x, uAxis, rayDirection);
			rayDirection.scaleAdd(coord.y, vAxis, rayDirection);
			Ray normalRay = new Ray(this.origin, new Vector3f(rayDirection));
			Point3f focusPoint = calculateFocusPoint(normalRay, wAxis, Constants.DISTANCETOLENS);
			for (int d = 0; d < Constants.SAMPLESPERPIXEL; d++) {
				for (int p = 0; p < Constants.ANTIALIASINGPERROW; p++) {
					for (int q = 0; q < Constants.ANTIALIASINGPERROW; q++) {
						double randomValueX = Math.random();
						double randomValueY = Math.random();
						float x = (float) (i + ((p + randomValueX)/Constants.ANTIALIASINGPERROW));
						float y = (float) (row + ((q + randomValueY)/Constants.ANTIALIASINGPERROW));
						coord = calcPositionInRasterImage(x,y);
						rayDirection.scaleSet(-distanceToScreen, wAxis);
						rayDirection.scaleAdd(coord.x, uAxis, rayDirection);
						rayDirection.scaleAdd(coord.y, vAxis, rayDirection);
						rays[i][d][(p*Constants.ANTIALIASINGPERROW)+q] = getPerturbatedRayWithFocusPoint(focusPoint, this.origin, uAxis, vAxis);
//						if(rays[i][d][(p*Constants.ANTIALIASINGPERROW)+q]==null)
//						System.out.println("ray is null");
					}
				}
			}
		}
		return rays;
	}

	public Ray[][][] createParallelProjectionRaysUsingDepthOfFieldAndAntiAliasing(
			int row, Point3f position, Vector3f uAxis, Vector3f vAxis,
			Vector3f wAxis) {
		Ray[][][] rays = new Ray[this.nbOfHorizontalPixels][Constants.SAMPLESPERPIXEL][Constants.ANTIALIASING];
		Point2f coord;
		Point3f rayOrigin = new Point3f();
		Vector3f rayDirection = new Vector3f();
		for (int i = 0; i < this.nbOfHorizontalPixels; i++) {
			coord = calcPositionInRasterImage((i+0.5f),(row+0.5f));
			rayDirection.scaleSet(-distanceToScreen, wAxis);
			rayDirection.scaleAdd(coord.x, uAxis, rayDirection);
			rayDirection.scaleAdd(coord.y, vAxis, rayDirection);
			Ray normalRay = new Ray(this.origin, new Vector3f(rayDirection));
			Point3f focusPoint = calculateFocusPoint(normalRay, wAxis, Constants.DISTANCETOLENS);
			for (int d = 0; d < Constants.SAMPLESPERPIXEL; d++) {
				for (int p = 0; p < Constants.ANTIALIASINGPERROW; p++) {
					for (int q = 0; q < Constants.ANTIALIASINGPERROW; q++) {
						double randomValueX = Math.random();
						double randomValueY = Math.random();
						float x = (float) (i + ((p + randomValueX)/Constants.ANTIALIASINGPERROW));
						float y = (float) (row + ((q + randomValueY)/Constants.ANTIALIASINGPERROW));
						coord = calcPositionInRasterImage(x,y);
						rayOrigin.scaleAdd(coord.x, uAxis, origin);
						rayOrigin.scaleAdd(coord.y, vAxis, rayOrigin);
						rayDirection.negate(wAxis);
						rayDirection.scale(1/rayDirection.length());
						rays[i][d][(p*Constants.ANTIALIASINGPERROW)+q] = getPerturbatedRayWithFocusPoint(focusPoint, rayOrigin, uAxis, vAxis);
					}
				}
			}
		}
		return rays;
	}

	public Ray[][] createParallelProjectionRaysUsingDepthOfField(int row,
			Point3f position, Vector3f uAxis, Vector3f vAxis, Vector3f wAxis) {
		Ray[][] rays = new Ray[this.nbOfHorizontalPixels][Constants.SAMPLESPERPIXEL];
		Point2f coord;
		Point3f rayOrigin = new Point3f();
		Vector3f rayDirection = new Vector3f();
		for (int i = 0; i < this.nbOfHorizontalPixels; i++) {
			coord = calcPositionInRasterImage((i+0.5f),(row+0.5f));
			rayDirection.scaleSet(-distanceToScreen, wAxis);
			rayDirection.scaleAdd(coord.x, uAxis, rayDirection);
			rayDirection.scaleAdd(coord.y, vAxis, rayDirection);
			Ray normalRay = new Ray(this.origin, new Vector3f(rayDirection));
			Point3f focusPoint = calculateFocusPoint(normalRay, wAxis, Constants.DISTANCETOLENS);
			for (int d = 0; d < Constants.SAMPLESPERPIXEL; d++) {
				coord = calcPositionInRasterImage((i+0.5f),(row+0.5f));
				rayOrigin.scaleAdd(coord.x, uAxis, origin);
				rayOrigin.scaleAdd(coord.y, vAxis, rayOrigin);
				rayDirection.negate(wAxis);
				rayDirection.scale(1/rayDirection.length());
				rays[i][d] = getPerturbatedRayWithFocusPoint(focusPoint, rayOrigin, uAxis, vAxis);
			}
		}
		return rays;
	}
	
	private Point3f getPerturbatedOrigin(Point3f normalOrigin, Vector3f uAxis, Vector3f vAxis){
		float randomValueU = (float) Math.random()*Constants.LENSSIZE;
		float randomValueV = (float) Math.random()*Constants.LENSSIZE;
		Point3f perturbatedOrigin = new Point3f(normalOrigin);
		perturbatedOrigin.scaleAdd(randomValueU, uAxis, perturbatedOrigin);
		perturbatedOrigin.scaleAdd(randomValueV, vAxis, perturbatedOrigin);
		return perturbatedOrigin;
	}
	
	protected Point3f calculateFocusPoint(Ray normalRay, Vector3f wAxis, float distanceToLens){
		float wXa = wAxis.dotProduct(new Vector3f(normalRay.origin));
//		System.out.println("wXa= " + wXa);
		Vector3f Q = new Vector3f();
		Q.scaleAdd(-distanceToLens, wAxis, normalRay.origin);
//		System.out.println("Q= " + Q);
		float wXq = wAxis.dotProduct(Q);
//		System.out.println("wXq= " + wXq);
		float wXdir = wAxis.dotProduct(normalRay.direction);
//		System.out.println("wXdir= " + wXdir);
		float t = -(wXa - wXq)/wXdir;
//		System.out.println("t= " + t);
		Point3f focalPoint = new Point3f();
		focalPoint.scaleAdd(t, normalRay.direction, normalRay.origin);
		return focalPoint;
	}
	
	private Ray getPerturbatedRayWithFocusPoint(Point3f focusPoint, Point3f normalOrigin, Vector3f uAxis, Vector3f vAxis){
		Point3f perturbatedOrigin = getPerturbatedOrigin(normalOrigin, uAxis, vAxis);
		return new Ray(perturbatedOrigin, focusPoint);
	}
}
