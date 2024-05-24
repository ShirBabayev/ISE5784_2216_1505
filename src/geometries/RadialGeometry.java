package geometries;

/**
 * The class that inherits classes that contain some kind of circle
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public abstract class RadialGeometry implements Geometry {

	/**
	 * Radius - the distance between the center of the circle of the shape and its
	 * circumference
	 */
	final protected double radius;
	/** The squared radius - for better performance */
	final protected double radiusSquared;
	
	/**
	 * A constructor that initializes the radius in a parameter
	 * 
	 * @param r The value at which the radius is initialized
	 */
	public RadialGeometry(double r) {
		radius = r;
		radiusSquared = r * r;
	}
}
