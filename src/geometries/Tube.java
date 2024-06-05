package geometries;

import java.util.List;
import primitives.*;
import static primitives.Util.*;
import primitives.Util;

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
	 * A constructor that initializes the tube with a ray and a radius
	 * 
	 * @param ray    - represents the direction of the cylinder
	 * @param radius - determines the width of the cylinder
	 */
	public Tube(Ray ray, double radius) {
		super(radius);
		axis = ray;
	}

	@Override
	public Vector getNormal(Point p) {
		Vector v = axis.getDirection();
		Point p0 = axis.getHead();
		// a dot product between the direction vector and p-p0
		// calculates the length between the head of the axis to the center of the tube
		double t = v.dotProduct(p.subtract(p0));// v*(p-p0)

		// Calculate projection of the point P on the axis
		Point o = isZero(t) ? p0 : axis.getPoint(t);

		// subtract the edge of the tube from its center
		return p.subtract(o).normalize();// p-(p0+v*t)
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		return null;
	}
	// Method to calculate intersection points between the cylinder and a given ray
	/*
	 * @Override public List<Point> findIntersections(Ray ray) {
	 * 
	 * // Extract the origin and direction of the ray Point rayOrigin =
	 * ray.getHead(); Vector rayDirection = ray.getDirection();
	 * 
	 * // Calculate the discriminant of the quadratic equation double[] abc =
	 * Util.discriminantParam(rayDirection, rayOrigin, ray, radius);
	 * 
	 * double discriminant = abc[1] * abc[1] - 4 * abc[0] * abc[2];
	 * 
	 * // If the discriminant is negative, the ray does not intersect the cylinder
	 * if (discriminant < 0) { return null; }
	 * 
	 * // Calculate the roots of the quadratic equation double t1 = (-abc[1] -
	 * Math.sqrt(discriminant)) / (2 * abc[0]); double t2 = (-abc[1] +
	 * Math.sqrt(discriminant)) / (2 * abc[0]);
	 * 
	 * // Calculate the intersection points Point intersectionPoint1 =
	 * ray.getHead().add(ray.getDirection().scale(t1)); Point intersectionPoint2 =
	 * ray.getHead().add(ray.getDirection().scale(t2));
	 * 
	 * // Add the intersection points to the list return List.of(intersectionPoint1,
	 * intersectionPoint2); }
	 */
}
