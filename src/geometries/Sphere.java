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
		if (bvh) {
			this.boundingBox = new BoundingBox(center.getX() - radius, center.getY() - radius, center.getZ() - radius,
					center.getX() + radius, center.getY() + radius, center.getZ() + radius);
		}

	}

	@Override
	public Vector getNormal(Point p) {
		return p.subtract(center).normalize();
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double distance) {
		Point head = ray.getHead();
		// The ray starts in the center
		if (head.equals(center))
			return alignZero(radius) > distance ? null : List.of(new GeoPoint(this, ray.getPoint(radius)));

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
		double t2 = alignZero(tm + th);
		if (t2 <= 0)
			return null;

		// the closer point to the head
		double t1 = alignZero(tm - th);
		double t1MinusDis = alignZero(t1 - distance);
		if (t2 <= 0 || t1MinusDis > 0)
			return null;

		if (alignZero(t2 - distance) > 0)
			// 2nd point is after the distance
			return t1 <= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t1)));

		return t1 <= 0 //
				? List.of(new GeoPoint(this, ray.getPoint(t2))) //
				: List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
	}

}
