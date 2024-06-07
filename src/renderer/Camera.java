package renderer;

import static primitives.Util.*;

import java.util.MissingResourceException;

import primitives.*;

/**
 * a class represents a camera with a point of view on an image
 */
public class Camera implements Cloneable {
	/**
	 * the point of view of the camera
	 */
	private Point p0 = null;
	/**
	 * the right vector X axis
	 */
	private Vector vRight = null;
	/**
	 * the up vector Y axis
	 */
	private Vector vUp = null;
	/**
	 * the to vector Z axis
	 */
	private Vector vTo = null;
	/**
	 * the height of the view plane
	 */
	private double height = 0;
	/**
	 * the width of the view plane
	 */
	private double width = 0;
	/**
	 * the distance between the camera to the view plane
	 */
	private double distance = 0;

	/**
	 * default constructor with no parameters
	 */
	private Camera() {

	}

	/**
	 * getter function of the source point of the camera
	 * 
	 * @return the point of the camera view
	 */
	public Point getP0() {
		return p0;
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
	 * 
	 * @return
	 */
	public static Builder getBuilder() {
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

	/**
	 * class that build a camera and enable settings to the fields of the camera
	 */
	public static class Builder {

		/**
		 * a camera for setting
		 */
		private final Camera camera;

		/**
		 * a constructor that build the camera according to the parameter c
		 * 
		 * @param c is a camera to construct the camera
		 */

		public Builder(Camera c) {
			camera = c;
		}

		/**
		 * 
		 * a method that set the location of the point of view of the camera
		 * 
		 * @param p0 is the point of view of the camera
		 * @return
		 */
		public Builder setLocation(Point p) {
			camera.p0 = p;
			return this;
		}

		/**
		 * a method that set the direction of the camera
		 * 
		 * @param vTo is a vector with direction to the center of the view plane
		 * @param vUp is a vector with direction to the up
		 * @return
		 */
		public Builder setDirection(Vector vTo, Vector vUp) {
			if (isZero(vTo.dotProduct(vUp))) {
				camera.vUp = vUp.normalize();
				camera.vTo = vTo.normalize();
			} else {
				throw new IllegalArgumentException("2 vectors are not orthogonal");
			}
			return this;
		}

		/**
		 * a method that set the size of the view plane
		 * 
		 * @param w is a width of the view plane
		 * @param h is a height of the view plane
		 * @return
		 */
		public Builder setVpSize(double w, double h) {
			if (h == 0 || w == 0)
				throw new IllegalArgumentException("size of view plane is wrong");
			camera.height = h;
			camera.width = w;
			return this;
		}

		/**
		 * a method that set the location of the view plane
		 * 
		 * @param d is a distance from the point of the location of the camera to the
		 *          view plane
		 * @return
		 */
		public Builder setVpDistance(double d) {
			if (d == 0)
				throw new IllegalArgumentException("distance from camera to the view plane is wrong");
			camera.distance = d;
			return this;
		}

		/**
		 * checks the validity of all fields of the camera
		 * 
		 * @return the camera after building
		 */
		public Camera build() {
			String missing = "ERROR: there is missing value";
			String h = "height";
			String w = "width";
			String d = "distance";
			String illegalArgument = "is illegal";

			if (camera.p0 == null)
				throw new MissingResourceException(missing, "Camera", "p0");

			if (camera.vUp == null)
				throw new MissingResourceException(missing, "Camera", "vUp");

			if (camera.vTo == null)
				throw new MissingResourceException(missing, "Camera", "vTo");

			if (camera.height == 0)
				throw new MissingResourceException(missing, "Camera", h);

			if (camera.width == 0)
				throw new MissingResourceException(missing, "Camera", w);

			if (camera.distance == 0)
				throw new MissingResourceException(missing, "Camera", d);

			if (camera.height < 0)
				throw new IllegalArgumentException(h + illegalArgument);

			if (camera.width < 0)
				throw new IllegalArgumentException(w + illegalArgument);

			if (camera.distance < 0)
				throw new IllegalArgumentException(d + illegalArgument);
			camera.vRight = camera.vUp.crossProduct(camera.vTo).normalize();
			try {
			return (Camera) camera.clone();
			}
			catch(CloneNotSupportedException ex) {
				throw new RuntimeException("does not succeed to create a copy to camera");
			}
		}

	}

}
