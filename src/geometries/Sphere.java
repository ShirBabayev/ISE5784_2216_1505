package geometries;

import java.util.List;
import java.util.*;

import primitives.*;
import primitives.Vector;

/**
 * A class that represents a sphere by a point and a radius
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class Sphere extends RadialGeometry {

	/**
	 * the center point of the sphere
	 */
	final private Point center;

	/**
	 * A constructor that initializes the center point and the radius of the sphere
	 * 
	 * @param p      - center point
	 * @param radius - radius
	 */
	public Sphere(Point p, double radius) {
		super(radius);
		center = p;
	}

	@Override
	public Vector getNormal(Point p) {
		return p.subtract(center).normalize();
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		// The ray starts in the center
		if (ray.getHead().equals(center)) {
			// empty list
			List<Point> intersections = new LinkedList<Point>();
			// add the intersection point to the list
			intersections.add(ray.getPoint(radius));
			return intersections;
		}
		// a vector from the head of the ray to the center of the sphere
		Vector u = center.subtract(ray.getHead());
		// tm = v*u. the distance o=between the head of the ray to the closest point on
		// the ray to the center
		double tm = ray.getDirection().dotProduct(u);
		// the distance between the center to the closest point on the ray
		double d = Math.sqrt(u.lengthSquared() - tm * tm);
		// outside the sphere - no intersections
		if (d >= radius)
			return null;
		// the distance between the closest point to the first intersection point
		double th = Math.sqrt(radius * radius - d * d);
		// the further point from the head
		double t1 = Util.alignZero(tm + th);
		// the closer point to the head
		double t2 = Util.alignZero(tm - th);

		/*
		 * //the head of the ray is not included, so t=0 (or close to 0) is the head and
		 * t<0, both are not relevant if(t1<=0 && t2 <=0) return null; List<Point>
		 * intersections = new LinkedList<Point>();//create empty list if (t1>0)
		 * intersections.add(ray.getPoint(t1)); if (t2>0 && t2!=t1)
		 * intersections.add(ray.getPoint(t2)); //sort the intersections points
		 * according to the closeness to the head point of the ray
		 * 
		 * return intersections.stream().sorted(Comparator.comparingDouble(p ->
		 * p.distance(ray.getHead()))) .toList();//sort the list by closeness to the
		 * ray's head. }
		 */

		// the head of the ray is not included, so t=0 (or close to 0) is the head and
		// t<0, both are not relevant
		if (t1 <= 0 && t2 <= 0)
			return null;

		List<Point> intersections = null;// create empty list
		Point p1 = null;
		Point p2 = null;

		if (t1 > 0) {
			p1 = ray.getPoint(t1);
		}
		if (t2 > 0 && t2 != t1) {
			p2 = ray.getPoint(t2);
		}
		if (p1 != null) {
			intersections = new LinkedList<Point>();
			intersections.add(p1);
		}
		if (p2 != null) {
			if (intersections == null) {
				intersections = new LinkedList<Point>();
			}
			intersections.add(p2);
		}

		// sort the intersections points according to the closeness to the head point of
		// the ray
		return intersections.stream().sorted(Comparator.comparingDouble(p -> p.distance(ray.getHead()))).toList();

	}

}
