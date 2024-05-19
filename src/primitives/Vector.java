package primitives;

/**
 * A class that represents a vector by a point with 3 coordinates and provides various operations on the vector
 * @author Hodaya Avidan and Shir Babayev
 */
public class Vector extends Point {

	/**
	 * A constructor that receives 3 double points and initializes them with the 3 coordinates of the point
	 * @param d1 - first double point
	 * @param d2 - second double point
	 * @param d3 - third double point
	 */
	public Vector(double d1, double d2, double d3) {
		super(d1, d2, d3);
		if (ZERO.equals(this))
			throw new IllegalArgumentException("Zero vector is illegal");
	}

	/**
	 * A constructor that accepts a point with 3 coordinates and initializes the point of the current vector
	 * @param d - point with 3 coordinates
	 */
	public Vector(Double3 d) {
		super(d);
		if (ZERO.equals(this))
			throw new IllegalArgumentException("Zero vector is illegal");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		/*Checking if an object of type vector was received*/
		return (obj instanceof Vector other) && this.xyz.equals(other.xyz);
	}

	@Override
	public String toString() {
		return super.toString();
	}

	/**
	 * Calculating the length of the vector - the distance from the point to the origin of the axes in the square
	 * @return The length of the vector squared
	 */
	public double lengthSquared() {
		return this.dotProduct(this);
	}

	/**
	 * Calculating the length of the vector - the distance from the point to the origin of the axes
	 * @return The length of the vector
	 */
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}

	/**
	 * Adding a vector and a vector by adding the point values
	 * @param vector - The vector that is added to the current vector
	 */
	public Vector add(Vector vector) {
		return new Vector(this.xyz.add(vector.xyz));
	}

	/**
	 * The function performs vector multiplication by a scalar
	 * @param d The scalar in which we will multiply the vector
	 * @return A new vector with the same direction but twice as long as the scalar
	 */
	public Vector scale(double d) {
		/*Multiplying the point by a scalar*/
		return new Vector(this.xyz.scale(d));
	}

	/**
	 * The function performs a scalar multiplication
	 * @param v The vector with which the current vector will be multiplied
	 * @return A number of type double for calculating the product
	 */
	public double dotProduct(Vector v) {
		/*Multiplying a coordinate by the coordinate of the respective vectors*/
		return this.xyz.d1 * v.xyz.d1 + this.xyz.d2 * v.xyz.d2 + this.xyz.d3 * v.xyz.d3;
	}

	/**
	 * The function performs vector multiplication
	 * @param v The vector with which the current vector will be multiplied
	 * @return A new vector - normal - perpendicular to the 2 multiplied vectors
	 */
	public Vector crossProduct(Vector v) {
		/*Calculating a vector product algebraically*/
		return new Vector(this.xyz.d2 * v.xyz.d3 - this.xyz.d3 * v.xyz.d2,
				this.xyz.d3 * v.xyz.d1 - this.xyz.d1 * v.xyz.d3, this.xyz.d1 * v.xyz.d2 - this.xyz.d2 * v.xyz.d1);
	}

	/**
	 * A function that performs normalization of a vector
	 * @return A new unit vector with the same direction as the current vector
	 */
	public Vector normalize() {
		/*Dividing each coordinate by the length of the vector*/
		return new Vector(this.xyz.d1 / this.length(), this.xyz.d2 / this.length(), this.xyz.d3 / this.length());
	}
}
