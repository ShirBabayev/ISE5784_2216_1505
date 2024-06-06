package geometries;

import java.util.List;
import primitives.*;
import static primitives.Util.*;

/**
 * A class that represents a finite cylinder by an infinite cylinder and a
 * height that determines the length of the cylinder
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class Cylinder extends Tube {
	/**
	 * the height of the cylinder
	 */
	final private double height;

	/**
	 * A constructor that initializes the cylinder ray, radius and height
	 * 
	 * @param ray    - direction of the cylinder
	 * @param radius - width of the cylinder
	 * @param h      - length of the cylinder
	 */
	public Cylinder(Ray ray, double radius, double h) {
		super(ray, radius);
		height = h;
	}

	@Override
	public Vector getNormal(Point p) {
		Point edge1 = axis.getHead();
		Vector direction = axis.getDirection();

		if (p.equals(edge1))
			return direction.scale(-1);
		// the end of the cylinder
		Point edge2 = edge1.add(direction.scale(height));
		if (p.equals(edge2))
			return direction;

		double t = p.subtract((edge1)).dotProduct(direction);
		if (isZero(t)) // lower base
			return direction.scale(-1);
		if (isZero(t - height)) // upper base
			return direction;

		// the closet point to p on the axis of the cylinder
		// the vector between o to p -> normalized
		return (p.subtract(axis.getPoint(t))).normalize();
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		return null;
	}
}
