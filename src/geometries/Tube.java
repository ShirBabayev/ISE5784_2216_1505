/**
 * 
 */
package geometries;

import primitives.*;

/**
 * A class that represents an infinite cylinder by a radius that determines the width of the cylinder 
 * and a ray that represents the direction of the cylinder in space
 * @author Hodaya Avidan and Shir Babayev
 */
public class Tube extends RadialGeometry {
	
	final protected Ray axis;

	/**
	 * A constructor that initializes the tube with radius and ray
	 * @param r -  represents the direction of the cylinder
	 * @param rad - determines the width of the cylinder
	 */
	public Tube(Ray r, double rad) {
		super(rad);
		axis = r;
	}

	@Override
	public Vector getNormal(Point p) {
		return null;
	}
}
