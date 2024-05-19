package geometries;

import primitives.*;

/**
 * A class that represents a sphere by a point and a radius
 * @author Hodaya Avidan and Shir Babayev
 */
public class Sphere extends RadialGeometry {
	
	final private Point center;

	/**
	 * A constructor that initializes the center point and the radius of the sphere
	 * @param p - center point
	 * @param rad - radius
	 */
	public Sphere(Point p, double rad) {
		super(rad);
		center = p;
	}

	@Override
	public Vector getNormal(Point p) {
		return null;
	}

}
