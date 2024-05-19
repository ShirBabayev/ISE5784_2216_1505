package geometries;

import primitives.*;

/**
 * A class that inherits all geometric bodies
 * @author Hodaya Avidan and Shir Babayev
 */
public interface Geometry {
	/**
	 * Calculation of a vector of unit length perpendicular to the surface at point p
	 * @param p The point on the surface at which the vector will be perpendicular.
	 * @return A vector perpendicular to the surface of unit length
	 */
	public Vector getNormal(Point p);
}
