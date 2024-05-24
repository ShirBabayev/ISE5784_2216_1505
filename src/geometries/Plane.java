package geometries;

import primitives.*;

/**
 * A class that represents a surface by a point and a normal vector to the point
 * perpendicular to the surface
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class Plane implements Geometry {

	final private Point q;

	final private Vector normal;

	/**
	 * A constructor that initializes the surface by 3 points and calculates the
	 * normal to the plane
	 * 
	 * @param p1 first double point
	 * @param p2 second double point
	 * @param p3 third double point
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
	 * @param v - A vector perpendicular to the surface
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
		if( ((normal.getXyz().getD1()) * ( (p.getXyz().getD1()) - (q.getXyz().getD1()) ) - 
				((normal.getXyz().getD2()) * ( (p.getXyz().getD2()) - (q.getXyz().getD2()) )) + 
				((normal.getXyz().getD3()) * ( (p.getXyz().getD3()) - (q.getXyz().getD3()) )))==0){
			return normal;
		}
		throw new IllegalArgumentException("ERROR: the point is not in the range of the plane");	
	}

}
