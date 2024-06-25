package geometries;

import java.util.List;

import primitives.*;
import static primitives.Util.*;

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
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		Point head = ray.getHead();
		// The ray starts in the center
		if (head.equals(center))
			return List.of(new GeoPoint(this, ray.getPoint(radius)));

		// a vector from the head of the ray to the center of the sphere
		Vector u = center.subtract(head);
		// tm = v*u. the distance o=between the head of the ray to the closest point on
		// the ray to the center
		double tm = ray.getDirection().dotProduct(u);
		// the squared distance between the center to the closest point on the ray
		double dSquared = u.lengthSquared() - tm * tm;
		double thSquared = radiusSquared - dSquared;
		// outside the sphere - no intersections
		if (alignZero(thSquared) <= 0)
			return null;

		// the distance between the closest point to the first intersection point
		double th = Math.sqrt(thSquared);
		// the further point from the head
		double t2 = Util.alignZero(tm + th);
		if (t2 <= 0)
			return null;

		// the closer point to the head
		double t1 = Util.alignZero(tm - th);
		return t1 <= 0 //
				? List.of(new GeoPoint(this, ray.getPoint(t2))) //
				: List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
	}

}
