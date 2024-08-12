package geometries;

import java.util.List;

import primitives.*;
import static primitives.Util.*;

/**
 * A class that represents a surface by a point and a normal vector to the point
 * perpendicular to the surface
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class Plane extends Geometry {

	/**
	 * a point on the plane
	 */
	final private Point q;
	/**
	 * a normal to the plane
	 */
	final private Vector normal;

	/**
	 * A constructor that initializes the surface by 3 points and calculates the
	 * normal to the plane
	 * 
	 * @param p1 first double point
	 * @param p2 second double point
	 * @param p3 third double point
	 * @throws IllegalArgumentException if some of points are convergent or the
	 *                                  points are co-linear
	 */
	public Plane(Point p1, Point p2, Point p3) {
		normal = ((p2.subtract(p1)).crossProduct(p3.subtract(p1))).normalize();
		q = p1;
		if (bvh) {
			boundingBox = null;
		}
	}

	/**
	 * A constructor that accepts a point and a vector as parameters and initializes
	 * the plane with a point and a vector with direction as the unit length
	 * parameter
	 * 
	 * @param p - point in the plane
	 * @param v - A vector vertical to the surface
	 */
	public Plane(Point p, Vector v) {
		q = p;
		normal = v.normalize();
		if (bvh) {
			boundingBox = null;
		}
	}

	/**
	 * A function that returns the normal value of this plane
	 * 
	 * @return normal value of this plane
	 */
	public Vector getNormal() {
		return normal;
	}

	@Override
	public Vector getNormal(Point p) {
		return normal;
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double distance) {
		double nv = normal.dotProduct(ray.getDirection());
		if (isZero(nv))
			return null;

		Vector qMinusHead;
		try {
			// t is the distance from the head of the ray to the intersection point
			qMinusHead = q.subtract(ray.getHead());
		}
		// In case an exception was thrown from trying to create the 0 vector
		catch (IllegalArgumentException ignore) {
			return null;
		}

		double t = normal.dotProduct(qMinusHead) / nv;
		// If t is negative than the body is in the opposite direction from the ray or
		// there is no point of
		// intersection
		return t <= 0 || alignZero(t - distance) > 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
	}

}
