package geometries;

import primitives.*;

/**
 * A class that represents a finite cylinder by an infinite cylinder and a
 * height that determines the length of the cylinder
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class Cylinder extends Tube {

	final private double height;

	/**
	 * A constructor that initializes the cylinder ray, radius and height
	 * 
	 * @param r   - direction of the cylinder
	 * @param rad - width of the cylinder
	 * @param h   - length of the cylinder
	 */
	public Cylinder(Ray r, double rad, double h) {
		super(r, rad);
		height = h;
	}

	@Override
	public Vector getNormal(Point p) {
		return axis.getDirection().normalize();
	}
}
