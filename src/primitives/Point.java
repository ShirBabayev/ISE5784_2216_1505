package primitives;

/**
 * The department provides a service for various operations on a point with 3
 * coordinates
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class Point {

	/**
	 * A point with 3 double coordinates
	 */
	protected final Double3 xyz;

	/**
	 * the zero point at the head of the coordinate axis (0,0,0)
	 */
	public static final Point ZERO = new Point(0, 0, 0);

	/**
	 * Initializes a point with 3 parameters it received as input
	 * 
	 * @param d1 double number of first coordinate
	 * @param d2 double number of second coordinate
	 * @param d3 double number of third coordinate
	 */
	public Point(double d1, double d2, double d3) {
		xyz = new Double3(d1, d2, d3);
	}

	/**
	 * Inserts the value of d into the point
	 * 
	 * @param d from which we will copy the values
	 */
	public Point(Double3 d) {
		xyz = d;
	}

	/**
	 * getter function for x coordinate
	 * 
	 * @return x coordinate
	 */
	public double getX() {
		return xyz.d1;
	}

	/**
	 * getter function for y coordinate
	 * 
	 * @return y coordinate
	 */
	public double getY() {
		return xyz.d2;
	}

	/**
	 * getter function for z coordinate
	 * 
	 * @return z coordinate
	 */
	public double getZ() {
		return xyz.d3;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		/* Checking if an object of type Point was received */
		return (obj instanceof Point other) && this.xyz.equals(other.xyz);
	}

	@Override
	public String toString() {
		return "" + xyz;
	}

	/**
	 * Adding a vector (containing a point) to a point
	 * 
	 * @param v - vector to add to the point
	 * @return new point by adding the vector to the current point
	 */
	public Point add(Vector v) {
		return new Point(xyz.add(v.xyz));
	}

	/**
	 * Point to point subtraction
	 * 
	 * @param p The point we will subtract from the current point
	 * @return A vector initialized with the point we removed
	 */
	public Vector subtract(Point p) {
		return new Vector(xyz.subtract((p.xyz)));
	}

	/**
	 * Calculate distance between point to point
	 * 
	 * @param p - The point from which the distance is calculated
	 * @return a number representing the distance between the points
	 */
	public double distance(Point p) {
		/* Taking the square root of the squared distance */
		return Math.sqrt(distanceSquared(p));
	}

	/**
	 * Calculates the distance between the points in the square
	 * 
	 * @param p - The point from which the distance is calculated
	 * @return The point from which the distance is calculated
	 */
	public double distanceSquared(Point p) {
		// Calculating the squared distance between 2 points according to the algebraic
		// formula for calculating the squared distance
		double dx = xyz.d1 - p.xyz.d1;
		double dy = xyz.d2 - p.xyz.d2;
		double dz = xyz.d3 - p.xyz.d3;
		return dx * dx + dy * dy + dz * dz;
	}
}
