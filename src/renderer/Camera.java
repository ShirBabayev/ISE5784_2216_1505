package renderer;

import static primitives.Util.isZero;
import java.util.MissingResourceException;
import primitives.*;

/**
 * a class represents a camera with a point of view on an image
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class Camera implements Cloneable {
	/**
	 * the point of view of the camera
	 */
	private Point p0 = null;
	/**
	 * the right vector X axis, this is a vector to the right direction. vRight is
	 * the orthogonal vector to vTo and vUp
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
	 * An object intended for creating the image and coloring it
	 */
	private ImageWriter imageWriter = null;
	/**
	 * An object designed to calculate the pixel color
	 */
	private RayTracerBase rayTracer = null;

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
	 * 
	 * @return a new object of Builder
	 */

	public static Builder getBuilder() {
		return new Builder();
	}

	/**
	 * getter function of the distance
	 * 
	 * @return the position of the camera
	 */
	public Point getLocation() {
		return p0;
	}

	/**
	 * a function that finds the ray from the camera to the pixel
	 * 
	 * @param nX - The number of pixels in the x-axis.
	 * @param nY - The number of pixels in the y-axis.
	 * @param j  - The x-coordinate of the pixel.
	 * @param i  - The y-coordinate of the pixel.
	 * @return a ray out of the camera through the pixel
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		if (isZero(nY) || isZero(nX)) {
			throw new IllegalArgumentException("It is impossible to divide by 0");
		}
		// pc is the center point of the view plane (since vTo is going straight to the
		// middle of the view plane)
		Point pc = p0.add(vTo.scale(distance));
		// the height of each pixel
		double rY = height / nY;
		// the width of each pixel
		double rX = width / nX;

		// Calculate the x and y coordinates of the pixel in the view plane
		double yi = -(i - ((nY - 1) / 2d)) * rY;
		double xj = (j - ((nX - 1) / 2d)) * rX;

		// Start with the center of the view plane
		Point pij = pc;
		// Update the pixel's location using the calculated x and y offsets
		if (!isZero(xj))
			pij = pij.add(vRight.scale(xj));
		if (!isZero(yi))
			pij = pij.add(vUp.scale(yi));
		Vector vij = pij.subtract(p0);
		// Return a normalized ray starting from the camera position towards the pixel
		return new Ray(p0, vij);
	}

	/**
	 * A function that goes through all the pixels of the view plane and casts a ray
	 * on each one
	 * @return the camera with painted view plane
	 */
	public Camera renderImage() {
		// runs trough all the pixels of the view plain
		int nX=imageWriter.getNx();
		int nY=imageWriter.getNy();		
		for (int i = 0; i < nY; i++)
			for (int j = 0; j < nX; j++)
				// for each pixel cast a ray
				castRay(nX, nY, j, i);
		return this;
	}

	/**
	 * A function responsible for creating a network of lines
	 * 
	 * @param interval is the length of the sides of the squares in the grid
	 * @param color    is the color of the grid lines
	 * 
	 * @return the camera with painted grid
	 */
	public Camera printGrid(int interval, Color color) {
		int nX=imageWriter.getNx();
		int nY=imageWriter.getNy();		
		for (int j = 0; j < nX; j++)
			for (int i = 0; i < nY; i++)
				if (isZero(j % interval) || isZero(i % interval))
					imageWriter.writePixel(j, i, color);
		return this;
	}

	/**
	 * A function that creates the final image
	 */
	public void writeToImage() {
		imageWriter.writeToImage();
	}

	/**
	 * A function that produces a ray according to the given parameters, calculates
	 * the color that should be painted the pixel and paints it.
	 * 
	 * @param nX - The number of pixels in the x-axis.
	 * @param nY - The number of pixels in the y-axis.
	 * @param j  - The x-coordinate of the pixel.
	 * @param i  - The y-coordinate of the pixel.
	 */
	private void castRay(int nX, int nY, int j, int i) {
		// create a ray that constructs with the pixel
		Ray ray = constructRay(nX, nY, j, i);
		// finds the color where the ray intersects
		Color color = rayTracer.traceRay(ray);
		// color the pixel
		imageWriter.writePixel(j, i, color);
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
			// ensure that the vectors are orthogonal
			if (!isZero(vTo.dotProduct(vUp)))
				throw new IllegalArgumentException("the vectors are not orthogonal");

			camera.vUp = vUp.normalize();
			camera.vTo = vTo.normalize();
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
			//if one og them is zero
			if (isZero(h*w))
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
			if (isZero(d))
				throw new IllegalArgumentException("distance from camera to the view plane is wrong");
			camera.distance = d;
			return this;
		}

		/**
		 * A setter function that allows updating the ray tracer
		 * 
		 * @param rt is the ray tracer
		 * @return a camera with updated ray tracer
		 */
		public Builder setRayTracer(RayTracerBase rt) {
			camera.rayTracer = rt;
			return this;
		}

		/**
		 * A setter function that allows updating of image Writer
		 * 
		 * @param imageWriter With it we will update the image Writer
		 * @return a camera with updated image Writer
		 */
		public Builder setImageWriter(ImageWriter imageWriter) {
			camera.imageWriter = imageWriter;
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

			if (isZero(camera.height))
				throw new MissingResourceException(missing, "Camera", h);

			if (isZero(camera.width))
				throw new MissingResourceException(missing, "Camera", w);

			if (isZero(camera.distance))
				throw new MissingResourceException(missing, "Camera", d);

			if (camera.height < 0)
				throw new IllegalArgumentException(h + illegalArgument);

			if (camera.width < 0)
				throw new IllegalArgumentException(w + illegalArgument);

			if (camera.distance < 0)
				throw new IllegalArgumentException(d + illegalArgument);

			if (!isZero(camera.vTo.dotProduct(camera.vUp)))
				throw new IllegalArgumentException("the vectors are not orthogonal");

			camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

			if (camera.imageWriter == null)
				throw new MissingResourceException(missing, "Camera", "imageWriter");

			if (camera.rayTracer == null)
				throw new MissingResourceException(missing, "Camera", "rayTracer");

			try {
				return (Camera) camera.clone();
			} catch (CloneNotSupportedException ignore) {
				// these is zero probability for the code to get here
				return null;
			}

		}

	}

}
