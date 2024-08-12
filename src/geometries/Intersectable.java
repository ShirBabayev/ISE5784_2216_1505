package geometries;

import java.util.List;

import primitives.*;

/**
 * Interface for finding intersection points of a ray with the geometric body
 *
 * @author Hodaya Avidan and Shir Babayev
 */
public abstract class Intersectable {

	/**
	 * boolean field foe bvh improvement
	 */
	protected static boolean bvh = false;
	/**
	 * a box that represents border around the intersectable
	 */
	protected BoundingBox boundingBox = null;

	/**
	 * a class representing a geometric point - every geoPoint contains a 3
	 * coordinate point and a geometry body that the point is on it.
	 */
	public static class GeoPoint {
		/**
		 * geometry body that the point is on it.
		 */
		public Geometry geometry;
		/**
		 * 3 coordinate point - exists on the geometry
		 */
		public Point point;

		/**
		 * A constructor that construct the geoPoint by geometry body and point
		 * 
		 * @param g is geometry body
		 * @param p is a point on the body
		 */
		public GeoPoint(Geometry g, Point p) {
			geometry = g;
			point = p;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			/* Checking if an object of type Point was received */
			return (obj instanceof GeoPoint other) //
					&& this.geometry == other.geometry && this.point.equals(other.point);
		}

		@Override
		public String toString() {
			return "" + point + " " + geometry;
		}
	}

	/**
	 * a class that represents boundaries in 3D by 3D box
	 */
	public class BoundingBox {
		/**
		 * minimum value for x axis
		 */
		protected double xMin;
		/**
		 * maximum value for x axis
		 */
		protected double xMax;

		/**
		 * minimum value for y axis
		 */
		protected double yMin;
		/**
		 * maximum value for y axis
		 */
		protected double yMax;

		/**
		 * minimum value for z axis
		 */
		protected double zMin;
		/**
		 * maximum value for z axis
		 */
		protected double zMax;

		/**
		 * constructor that initializes the minimum and maximum values to infinite
		 * values
		 */
		public BoundingBox() {

			this.xMin = Double.POSITIVE_INFINITY;
			this.xMax = -Double.NEGATIVE_INFINITY;

			this.yMin = Double.POSITIVE_INFINITY;
			this.yMax = Double.NEGATIVE_INFINITY;

			this.zMin = Double.POSITIVE_INFINITY;
			this.zMax = Double.NEGATIVE_INFINITY;

		}

		/**
		 * constructor that initializes the minimum and maximum values
		 * 
		 * @param xMin minimum value for x axis
		 * @param yMin minimum value for y axis
		 * @param zMin minimum value for z axis
		 * @param xMax maximum value for x axis
		 * @param yMax maximum value for y axis
		 * @param zMax maximum value for z axis
		 */
		public BoundingBox(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {

			this.xMin = xMin;
			this.yMin = yMin;
			this.zMin = zMin;

			this.xMax = xMax;
			this.yMax = yMax;
			this.zMax = zMax;
		}

		/**
		 * function that check if the ray intersect the box
		 * 
		 * @param ray is a ray from the camera
		 * @return true if the ray intersect the box and false if not
		 */
		protected boolean intersect(Ray ray) {
			Point head = ray.getHead();
			double xHead = head.getX();
			double yHead = head.getY();
			double zHead = head.getZ();
			Vector direction = ray.getDirection();
			double xDirection = direction.getX();
			double yDirection = direction.getY();
			double zDirection = direction.getZ();

			double disMin = Double.NEGATIVE_INFINITY;
			double disMax = Double.POSITIVE_INFINITY;

			if (xDirection > 0) {
				// xMin=xHead + disMin*xDirection
				disMin = (xMin - xHead) / xDirection;
				disMax = (xMax - xHead) / xDirection;
			} else if (xDirection < 0) {
				disMin = (xMax - xHead) / xDirection;
				disMax = (xMin - xHead) / xDirection;
			}

			double disMinY = Double.NEGATIVE_INFINITY;
			double disMaxY = Double.POSITIVE_INFINITY;
			if (yDirection > 0) {
				// yMin=yHead + disMinY*yDirection
				disMinY = (yMin - yHead) / yDirection;
				disMaxY = (yMax - yHead) / yDirection;
			} else if (yDirection < 0) {
				disMinY = (yMax - yHead) / yDirection;
				disMaxY = (yMin - yHead) / yDirection;
			}

			// check if the ray is inside the box by the "distance" from each border
			if ((disMin > disMaxY) || (disMinY > disMax))
				return false;

			if (disMinY > disMin)
				disMin = disMinY;
			if (disMaxY < disMax)
				disMax = disMaxY;

			double disMinZ = Double.NEGATIVE_INFINITY;
			double disMaxZ = Double.POSITIVE_INFINITY;
			if (zDirection > 0) {
				// zMin=zHead + disMinZ*zDirection
				disMinZ = (zMin - zHead) / zDirection;
				disMaxZ = (zMax - zHead) / zDirection;
			} else if (zDirection < 0) {
				disMinZ = (zMax - zHead) / zDirection;
				disMaxZ = (zMin - zHead) / zDirection;
			}

			return disMin <= disMaxZ && disMinZ <= disMax;
		}
	}

	/**
	 * turn on the bvh improvement
	 */
	public static void setBvh() {
		Intersectable.bvh = true;
	}

	/**
	 * The functions gets a ray and find a list of points that intersects the
	 * geometry body
	 * 
	 * @param ray         is a ray that intersect (or not) the geometry body
	 * @param maxDistance is the maximum distance between bodies inside a box.
	 * @return list of intersections point
	 */
	public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		return boundingBox != null && !boundingBox.intersect(ray) ? null : findGeoIntersectionsHelper(ray, maxDistance);
	}

	/**
	 * The functions gets a ray and find a list of points that intersects the
	 * geometry body
	 * 
	 * @param ray         is a ray that intersect (or not) the geometry body
	 * @param maxDistance is the maximum distance between bodies inside a box.
	 * @return list of intersections point
	 */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

	/**
	 * The functions gets a ray and find a list of points that intersects the
	 * geometry body
	 * 
	 * @param ray ray is a ray that intersect (or not) the geometry body
	 * @return list of intersections point
	 */
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
	}

	/**
	 * calculates the points where the ray intersects with the geometry
	 * 
	 * @param ray - the ray that Intersects with the geometry
	 * @return geoList list of intersection point between the ray and the geometry
	 */
	public final List<Point> findIntersections(Ray ray) {
		var geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}

}