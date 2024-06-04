package geometries;
import java.util.List;

import primitives.*;

/**
 * A class that represents a surface by a point and a normal vector to the point
 * perpendicular to the surface
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class Plane implements Geometry {

	/**
	 * a point on the plane
	 */
	@SuppressWarnings("unused") // TODO remove it after q will be used
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
	public List<Point> findIntersections(Ray ray){
		try {
		//@param t is the number of times the ray must be multiplied for it to cut the geometric body	
		double t=normal.dotProduct(q.subtract(ray.getHead()))/(normal.dotProduct(ray.getDirection()));
		//The body is in the opposite direction from the ray or there is no point of intersection
		if(t<0||Util.isZero(t)==true)
			return null;
		//else:
		return List.of(ray.getPoint(t)); 
		}
		//In case an exception was thrown from trying to create the 0 vector
		catch(Exception ex) {
			return null;
		}
		
	 }

}
