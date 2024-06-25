package geometries;

import primitives.*;

/**
 * A class that inherits all geometric bodies
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public abstract class Geometry extends Intersectable {
	/**
	 * the material of the geometry
	 */
	private Material material = new Material();
	/**
	 * the emission of the geomerty
	 */
	protected Color emission = Color.BLACK;

	/**
	 * getter function to the emission of the body
	 * 
	 * @return the emission of the body
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * getter function to the material of the body
	 * 
	 * @return the material of the body
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * setter function to the emission of the body
	 * 
	 * @param emission is the color of the body
	 * @return the updated geometry
	 */
	public Geometry setEmission(Color emission) {
		this.emission = emission;
		return this;
	}

	/**
	 * setter function to the material of the body
	 * 
	 * @param material is the material of the body
	 * @return the updated geometry
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}

	/**
	 * Calculation of a vector of unit length perpendicular to the surface at point
	 * 
	 * @param p The point on the surface at which the vector will be perpendicular.
	 * @return a vector perpendicular to the surface of unit length
	 */
	public abstract Vector getNormal(Point p);
}