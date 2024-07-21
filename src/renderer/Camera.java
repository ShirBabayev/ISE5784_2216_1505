package renderer;

import static primitives.Util.isZero;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Random;

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
	 * The distance from the camera to the focal plane. This is used to determine
	 * the focus point for depth of field calculations.
	 */
	private double focalDistance = 0;

	/**
	 * The radius of the camera's aperture. A non-zero aperture radius will result
	 * in depth of field effects.
	 */
	private double apertureRadius = 0;

	/**
	 * The square root of the grid size used for anti-aliasing. This determines the
	 * number of rays cast per pixel for better image quality.
	 */
	private int sqrtGridSize = 1;

	/**
	 * The list of points on the aperture. These points are used to construct rays
	 * for depth of field calculations.
	 */
	private List<Point> apperturePoints = null;

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
	 * Gets the focal distance of the camera.
	 *
	 * @return the focal distance
	 */
	public double getFocalDistance() {
		return focalDistance;
	}

	/**
	 * Gets the aperture radius of the camera.
	 *
	 * @return the aperture radius
	 */
	public double getApertureRadius() {
		return apertureRadius;
	}

	/**
	 * Gets the square root of the grid size of the aperture window.
	 *
	 * @return the square root of the grid size
	 */
	public int getSqrtGridSize() {
		return sqrtGridSize;
	}

	/**
	 * Finds the ray from the camera to the specified pixel.
	 * 
	 * @param nX The number of pixels in the x-axis.
	 * @param nY The number of pixels in the y-axis.
	 * @param j  The x-coordinate of the pixel.
	 * @param i  The y-coordinate of the pixel.
	 * @return a ray from the source through the specified pixel
	 * @throws IllegalArgumentException if nX or nY is zero
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
	 * 
	 * @return the camera with painted view plane
	 */
	public Camera renderImage() {
		// runs trough all the pixels of the view plain
		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();
		for (int i = 0; i < nY; i++)
			for (int j = 0; j < nX; j++)
				// for each pixel cast a ray
				castRays(nX, nY, j, i);
		return this;
	}

	/**
	 * Casts rays through a specific pixel on the image. Depending on the aperture
	 * radius, it can handle depth of field.
	 *
	 * @param nX the number of pixels in the x-direction
	 * @param nY the number of pixels in the y-direction
	 * @param j  the x-coordinate of the pixel
	 * @param i  the y-coordinate of the pixel
	 */
	private void castRays(int nX, int nY, int j, int i) {
		Color color=Color.BLACK;
		Ray ray = constructRay(nX, nY, j, i);
		
		// With depth of field
		if (sqrtGridSize > 1) {

			Point focalPoint = calculateFocalPoint(ray);
			Point sourcePoint;
			Ray secondaryRay;

			int k = 0;
			for (; k < apperturePoints.size(); k++) {
				sourcePoint = apperturePoints.get(k);
				secondaryRay = new Ray(sourcePoint, focalPoint.subtract(sourcePoint));
				color = color.add(rayTracer.traceRay(secondaryRay));
			}
			
			color = color.scale(1d / k);

		} else
			color = rayTracer.traceRay(ray);
		
		colorPixel(j, i, color);

	}

	/**
	 * a function that calculate the focal point for every pixel, the focal point is on the focal plane
	 * @param ray is the main ray from the center of the aperture
	 * @return focal point on the focal plane
	 */
	private Point calculateFocalPoint(Ray ray) {
		double cosA = vTo.dotProduct(ray.getDirection());
		double dij = focalDistance / cosA;
		return p0.add(ray.getDirection().scale(dij));
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
		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();
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
	 * Colors a specific pixel on the image.
	 *
	 * @param j     the x-coordinate of the pixel
	 * @param i     the y-coordinate of the pixel
	 * @param color the color to set for the pixel
	 */
	private void colorPixel(int j, int i, Color color) {
		// Color the pixel
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
			// if one og them is zero
			if (isZero(h * w))
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
		 * Sets the focal distance for the camera.
		 *
		 * @param distance the focal distance to set
		 * @return a camera with updated FocalDistance
		 */
		public Builder setFocalDistance(double distance) {
			camera.focalDistance = distance;
			return this;
		}

		/**
		 * Sets the aperture radius for the camera.
		 *
		 * @param radius the aperture radius to set
		 * @return a camera with updated ApertureRadius
		 */
		public Builder setApertureRadius(double radius) {
			camera.apertureRadius = radius;
			return this;
		}

		/**
		 * Sets the square root of the grid size for the camera. The amount of squares
		 * that will be in the aperture window in length and width. *
		 * 
		 * @param size the square root of the grid size to set
		 * @return a camera with updated SqrtGridSize
		 */
		public Builder setSqrtGridSize(int size) {
			camera.sqrtGridSize = size;
			return this;
		}

		/**
		 * A function that creates a given amount of points randomly distributed around
		 * the center point of the camera in a given radius range
		 * 
		 * @param radius   is radius of the aperture window around the center of the
		 *                 camera
		 * @param gridSize is the amount of squares we will create in the length and
		 *                 width of the aperture window. There will be gridSize*gridSize
		 *                 squares in total
		 * @return A list of points within the aperture window that fall within the
		 *         given radius around the center of the camera
		 */
		private List<Point> constructBeamPoints(double radius, int gridSize) {
			List<Point> points = new ArrayList<>();
			// Aperture window size
			double windowSize = radius * 2;
			// Grid step size along each axis
			double gridStep = windowSize / gridSize;

			// Loop through the grid and create random points within each grid square
			Random random = new Random();
			Point p = camera.p0;
			for (int i = 0; i < gridSize; i++) {
				for (int j = 0; j < gridSize; j++) {
					// Compute the location of the point within the current grid square
					p = camera.p0;
					p = p.add(camera.vRight.scale(-radius + i * gridStep + random.nextDouble() * gridStep));
					p = p.add(camera.vUp.scale(-radius + j * gridStep + random.nextDouble() * gridStep));

					// Add the point to the list
					points.add(p);
				}
			}

			// Filter the points to keep only those inside the circle of radius `radius`
			// around P0
			List<Point> filteredPoints = new ArrayList<>();
			for (Point point : points) {
				if (camera.p0.distance(point) <= radius) {
					filteredPoints.add(point);
				}
			}

			return filteredPoints;
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
			if (!isZero(camera.vTo.dotProduct(camera.vUp)))
				throw new IllegalArgumentException("the vectors are not orthogonal");
			camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

			if (isZero(camera.height))
				throw new MissingResourceException(missing, "Camera", h);
			if (camera.height < 0)
				throw new IllegalArgumentException(h + illegalArgument);
			if (isZero(camera.width))
				throw new MissingResourceException(missing, "Camera", w);
			if (camera.width < 0)
				throw new IllegalArgumentException(w + illegalArgument);
			if (isZero(camera.distance))
				throw new MissingResourceException(missing, "Camera", d);
			if (camera.distance < 0)
				throw new IllegalArgumentException(d + illegalArgument);

			if (camera.imageWriter == null)
				throw new MissingResourceException(missing, "Camera", "imageWriter");
			if (camera.rayTracer == null)
				throw new MissingResourceException(missing, "Camera", "rayTracer");

			if (!isZero(camera.apertureRadius) && camera.sqrtGridSize > 1 && !isZero(camera.focalDistance))
				camera.apperturePoints = constructBeamPoints(camera.apertureRadius, camera.sqrtGridSize);

			try {
				return (Camera) camera.clone();
			} catch (CloneNotSupportedException ignore) {
				// these is zero probability for the code to get here
				return null;
			}

		}

	}

}
