package geometries;

import primitives.*;

/**
 * A class that inherits all geometric bodies
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public abstract class Geometry extends Intersectable {
	/**
	 * Calculation of a vector of unit length perpendicular to the surface at point
	 * 
	 * @param p The point on the surface at which the vector will be perpendicular.
	 * @return a vector perpendicular to the surface of unit length
	 */
	protected Color emission=Color.BLACK;
	public Color getEmission() {return emission;}
	public Geometry setEmission(Color emission) {
		this.emission=emission;
		return this;
		}
	public abstract Vector getNormal(Point p);
}