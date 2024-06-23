package primitives;

import java.util.List;

import geometries.Intersectable.GeoPoint;

/**
 * The class provides a service for various operations on ray based on point and
 * direction
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class Ray {

	/**
	 * the direction of the ray
	 */
	private final Vector direction;
	/**
	 * the head of the ray
	 */
	private final Point head;

	/**
	 * Construct ray at a given point and direction sent to the constructor. If the
	 * direction parameter is not a unit vector - the ray will normalize it
	 * 
	 * @param p   - head point of the ray
	 * @param direction - direction of the ray
	 */
	public Ray(Point p, Vector directoin) {
		head = p;
		this.direction = directoin.normalize();
	}

	/**
	 * getter function for direction of the ray
	 * 
	 * @return normalized direction vector
	 */
	public Vector getDirection() {
		return direction;
	}

	/**
	 * getter function for head point of the ray
	 * 
	 * @return head of Point type
	 */
	public Point getHead() {
		return head;
	}

	/**
	 * The method calculates a point on the line of the ray, at a distance given by
	 * the head of the ray
	 * 
	 * @param t - distance given by the head of the ray
	 * @return point on the line of the ray
	 */
	public Point getPoint(double t) {
		return Util.isZero(t) ? head : head.add(direction.scale(t));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		/* Checking if an object of type ray was received */
		return (obj instanceof Ray other) && direction.equals(other.direction) && head.equals(other.head);
	}

	@Override
	public String toString() {
		return head + "->" + direction;
	}
	
	/**
	 * The function gets a list of intersections point that the rat intersect the geometry body and find the closet point to the head of the point
	 * @param intersections are list of intersection point that the ray intersect the body
	 * @return the closet point to the head of the ray among the intersections point
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
		if (intersections == null) // if there are no points in the list
			return null;
		double distance = Double.POSITIVE_INFINITY;// the biggest value of distance
		GeoPoint closest = null;
		// passes threw all the points in the list and checks for each one
		// if it's closer to the head of the ray, than the current closet point.
		for (GeoPoint p : intersections) {
			double pdh = p.point.distance(head);// p distance head
			// if the distance from p to the head is smaller than the smallest current
			// distance
			if (pdh < distance) {
				distance = pdh;
				closest = p;
			}
		}
		return closest;
	}

	/**
	 * A function that receives a list of points and returns the point closest to
	 * the top of the ray
	 * 
	 * @param intersections are the list of points
	 * @return the point closest to the top of the ray
	 */
	public Point findClosestPoint(List<Point> points) {
		 return points == null || points.isEmpty() ? null
		 : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
		}
}
