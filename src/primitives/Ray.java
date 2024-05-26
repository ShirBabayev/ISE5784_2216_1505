package primitives;

/**
 * The class provides a service for various operations on ray based on point and
 * direction
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class Ray {

	/**
	 * 	the direction of the ray
	 */
	private final Vector direction;
	/**
	 * 	the head of the ray
	 */
	private final Point head;

	/**
	 * Construct ray at a given point and direction sent to the constructor. If the
	 * direction parameter is not a unit vector - the ray will normalize it
	 * 
	 * @param dir - direction of the ray
	 * @param p   - head point of the ray
	 */
	public Ray(Vector dir, Point p) {
		head = p;
		direction = dir.normalize();
	}

	/**
	 * getter function for direction of the ray
	 * 
	 * @return normalized direction vector
	 */
	public Vector getDirection() {
		return direction;
	}

	/**
	 * getter function for head point of the ray
	 * 
	 * @return head of Point type
	 */
	public Point getHead() {
		return head;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		/* Checking if an object of type ray was received */
		return (obj instanceof Ray other) && direction.equals(other.direction) && head.equals(other.head);
	}

	@Override
	public String toString() {
		return head + "->" + direction;
	}
}
