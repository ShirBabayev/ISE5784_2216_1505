package geometries;

import primitives.*;
/**
 * A class that represents a surface by a point and a normal vector to the point perpendicular to the surface
 * @author Hodaya Avidan and Shir Babayev
 */
public class Plane implements Geometry {
	
	final private Point q;
	
	final private Vector normal;

	/**
	 * A constructor that initializes the surface by 3 points and calculates the normal to the plane
	 * @param p1 first double point
	 * @param p2 second double point
	 * @param p3 third double point
	 */
	public Plane(Point p1, Point p2, Point p3) {
		normal = null;
		q = p1;
	}

	/**
	 * A constructor that accepts a point and a vector as parameters and initializes the plane 
	 * with a point and a vector with direction as the unit length parameter
	 * @param p - point in the plane
	 * @param v - A vector perpendicular to the surface
	 */
	public Plane(Point p, Vector v) {
		q = p;
		normal = v.normalize();
	}

	/**
	 * A function that returns the normal value of this plane
	 * @return normal value of this plane
	 */
	public Vector getNormal() {
		return normal;
	}

	@Override
	public Vector getNormal(Point p) {
		return normal;
	}

}
