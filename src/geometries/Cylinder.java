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
		Plane plane = new Plane(_ray.getPoint(), _ray.getVector());
    Vector v1 = _ray.getPoint().subtract(p);
    if((v1.dotProduct(_ray.getVector()))==0) //the vectors are orthogonal
        return (_ray.getVector().scale(-1)).normalize();
    Point3D p1 = _ray.getPoint().add(_ray.getVector().normalized().scale(_height));
    v1 = p1.subtract(p);
    if((v1.dotProduct(_ray.getVector()))==0) //the vectors are orthogonal
        return (_ray.getVector()).normalize();
    Vector v = p.subtract((_ray.getPoint()));
    double t = _ray.getVector().dotProduct(v);
    Point3D o = _ray.getPoint().add(_ray.getVector().scale(t));
    Vector n = (p.subtract(o)).normalize();
    return n;
}}
