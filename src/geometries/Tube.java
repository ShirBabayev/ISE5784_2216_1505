package geometries;

import primitives.*;

/**
 * A class that represents an infinite cylinder by a radius that determines the
 * width of the cylinder and a ray that represents the direction of the cylinder
 * in space
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class Tube extends RadialGeometry {

	/**
	 * Direction of the tube
	 */
	final protected Ray axis;

	/**
	 * A constructor that initializes the tube with radius and ray
	 * 
	 * @param ray      - represents the direction of the cylinder
	 * @param radius - determines the width of the cylinder
	 */
	public Tube(Ray ray, double radius) {
		super(radius);
		axis = ray;
	}

	@Override
	public Vector getNormal(Point p) {
		Vector v=axis.getDirection();
		Point p0=axis.getHead();
		double t=v.dotProduct(p.subtract(p0));
		return p.subtract(p0.add(v.scale(t))).normalize();
	}
}
