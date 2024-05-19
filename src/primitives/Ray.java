package primitives;

/**
 * The class provides a service for various operations on ray based on point and direction
 * @author Hodaya Avidan and Shir Babayev
 */
public class Ray {
	
	private final Vector direction;
	
	private final Point head;

	/**
	 * Initialization of the point and direction of the ray at the point and direction sent to the constructor
	 * @param dir - director
	 * @param p - point
	 */
	public Ray(Vector dir, Point p) {
		head = p;
		direction = dir.normalize();
	}
	/**
	 * getter function for direction of the ray
	 * @return direction of Vector type
	 */
	public Vector getDirection() {return direction;}
	/**
	 * getter function for head point of the ray
	 * @return head of Point type
	 */
	public Point getHead() {return head;}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		/*Checking if an object of type ray was received*/
		return (obj instanceof Ray other) && direction.equals(other.direction) && head.equals(other.head);
	}

	@Override
	public String toString() {
		return "" + head + direction;
	}
}
