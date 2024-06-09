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
	 * the right vector X axis, this is a vector to the right direction. vRight is the orthogonal vector to vTo and vUp
	 */
	private Vector vRight = null;
	/**
	 * the up vector Y axis, this is a vector on the positive direction going up.
	 */
	private Vector vUp = null;
	/**
	 * the to vector Z axis, this is a vector towards the view plane 
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
	 * getter function of the Builder camera
	 * @return a new object of Builder
	 */

	public static Builder getBuilder() {
		return new Builder();
	}

	/**
	 * getter function of the distance
	 * @return the position of the camera
	 */
	public Point getLocation() {
		return p0;
	}
	
	/**
	 * a function that finds the ray from the camera to the pixel
	 * @param nX - The number of pixels in the x-axis.
	 * @param nY - The number of pixels in the y-axis.
	 * @param j - The x-coordinate of the pixel.
	 * @param i - The y-coordinate of the pixel.
	 * @return a ray out of the camera through the pixel
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		 if (isZero(distance)) {
	            throw new IllegalArgumentException("Distance can't be 0");
	        }
		 if (isZero(nY) || isZero(nX)) {
	            throw new IllegalArgumentException("It is impossible to divide by 0");
	        }
		//pc is the center point of the view plane, since vTo is going straight to the middle of the view plane
		Point pc= p0.add(vTo.scale(distance));
		//the height of each pixel
		double rY=height/nY;
		//the width of each pixel
		double rX=width/nX;
		
	    //Calculate the x and y coordinates of the pixel in the view plane
		double xj = -1*(j - ((nX - 1) / 2d)) * rX;
        double yi = (i - ((nY - 1) / 2d)) * rY;
       
       // Start with the center of the view plane
		Point pij=pc;
		
		//When the matrix has an odd number of rows and \or columns of pixels*/
		if(isZero(width%2) && isZero(height%2)) { 
			pij = pij.add(vRight.scale(xj).add(vUp.scale(yi)));
		}
		else {
	    // Update the pixel's location using the calculated x and y offsets
		if (!isZero(xj))
           pij = pij.add(vRight.scale(xj));
        if (!isZero(yi))
           pij = pij.add(vUp.scale(yi));
		}
		
   		// Return a normalized ray starting from the camera position towards the pixel
        return new Ray(pij.subtract(p0).normalize(),p0);
	}


	/**
	 * class that build a camera and enable settings to the fields of the camera
	 */
	public static class Builder {

		/**
		 * a camera for setting
		 */
		private final Camera camera;

		 /***
         * Constructor for Camera in Builder
         */
        public Builder() {
            this.camera = new Camera();
        }
        
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
		 * @param p is the point of view of the camera
		 * @return a camera with updated location
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
		 * @return a camera with updated direction
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
		 * @return a camera with updated view plane size
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
		 * @return a camera with updated distance
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
