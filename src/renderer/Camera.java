package renderer;

import primitives.*;

/**
 * a class represents a camera with a point of view on an image
 */
public class Camera implements Cloneable {
	/**
	 * the point of view of the camera
	 */
	private Point viewPoint;
	/**
	 * the right vector
	 */
	private Vector vRight;
	/**
	 * the up vector
	 */
	private Vector vUp;
	/**
	 * the to vector
	 */
	private Vector vTo;
	/**
	 * 
	 */
	private double height = 0;
	/**
	 * 
	 */
	private double width = 0;
	/**
	 * 
	 */
	private double distance = 0;

	/**
	 * 
	 */
	public static class Builder {

		/**
		 * 
		 */
		private final Camera camera;

		/**
		 * constructor for a builder
		 * 
		 * @param c is a camera to construct the camera
		 */
		public Builder(Camera c) {
			camera = c;
		}
	}

	/**
	 * getter function of the source point of the camera
	 * 
	 * @return the point of the camera view
	 */
	public Point getViewPoint() {
		return viewPoint;
	}

	/**
	 * getter function of the right vector
	 * 
	 * @return the right vector
	 */
	public Vector getVRight() {
		return vRight;
	}

	/**
	 * getter function of the up vector
	 * 
	 * @return the up vector
	 */
	public Vector getVUp() {
		return vUp;
	}

	/**
	 * getter function of the to vector
	 * 
	 * @return the to vector
	 */
	public Vector getVTo() {
		return vTo;
	}

	/**
	 * getter function of the height
	 * 
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * getter function of the width
	 * 
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * getter function of the distance
	 * 
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * default constructor with no parameters
	 */
	private Camera() {

	}

	/**
	 * 
	 * @return
	 */
	public Builder getBuilder() {
		return null;
	}

	/**
	 * 
	 * @param nX
	 * @param nY
	 * @param j
	 * @param i
	 * @return
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		return null;
	}
}
