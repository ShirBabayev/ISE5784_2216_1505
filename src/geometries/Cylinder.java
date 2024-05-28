package geometries;

import primitives.*;

/**
 * A class that represents a finite cylinder by an infinite cylinder and a
 * height that determines the length of the cylinder
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class Cylinder extends Tube {
	/**
	 * the height of the cylinder
	 */
	final private double height;

	/**
	 * A constructor that initializes the cylinder ray, radius and height
	 * 
	 * @param r      - direction of the cylinder
	 * @param radius - width of the cylinder
	 * @param h      - length of the cylinder
	 */
	public Cylinder(Ray ray, double radius, double h) {
		super(ray, radius);
		height = h;
	}

	@Override
	public Vector getNormal(Point p) {
		Point edge1=axis.getHead();
		Vector direction=axis.getDirection();
		//Plane plane = new Plane(head, direction);
		Vector v1 = edge1.subtract(p);
		if ((v1.dotProduct(direction)) == 0) // the vectors are orthogonal - p is on the cylinder edge
			return (direction.scale(-1)).normalize();//the opposite direction - normalized
		Point edge2 = edge1.add(direction.normalize().scale(height));//the end of the cylinder
		v1 = edge2.subtract(p);
		if ((v1.dotProduct(direction)) == 0) // the vectors are orthogonal
			return (direction).normalize();
		Vector v = p.subtract((edge1));//a vector from the first edge to p
		double t = direction.dotProduct(v);//the projection of vector v to the axis direction
		Point o = edge1.add(direction.scale(t));// the closet point to p on the cylinder 
		return (p.subtract(o)).normalize();//the vector between o to p -> normalized
		
	}
}
