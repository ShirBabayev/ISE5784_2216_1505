package geometries;

import java.util.List;
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
		// a dot product between the direction vector and p-p0
		// calculates the length between the head of the axis to the center of the tube
		double t = axis.getDirection().dotProduct(p.subtract(axis.getHead()));// v*(p-p0)
		// Calculate projection of the point P on the axis
		// subtract the edge of the tube from its center
		return p.subtract(axis.getPoint(t)).normalize();// p-(p0+v*t)
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
		return null;
	}
}
