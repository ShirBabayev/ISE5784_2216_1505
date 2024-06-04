package geometries;

import java.util.List;

import primitives.*;
import primitives.Ray;

/**
 * A class that represents a triangle by a polygon with 3 vertices
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class Triangle extends Polygon {

	/**
	 * construct the triangle by 3 vertexes.
	 * 
	 * @param p1 first vertex
	 * @param p2 second vertex
	 * @param p3 third vertex
	 */
	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3);
	}
	
	@Override
	public List<Point> findIntersections(Ray ray) {
		
		// Triangle vertices
		Point p1 = vertices.get(0);
		Point p2 = vertices.get(1);
		Point p3 = vertices.get(2);

		Vector v1 = p1.subtract(ray.getHead());
		Vector v2 = p2.subtract(ray.getHead());
		Vector v3 = p3.subtract(ray.getHead());

		try {
			v1.crossProduct(v2);
			v2.crossProduct(v3);
			v3.crossProduct(v1);
		} catch (Exception ex) {
			return null;
		}
		Vector n1 = (v1.crossProduct(v2)).normalize();
		Vector n2 = (v2.crossProduct(v3)).normalize();
		Vector n3 = (v3.crossProduct(v1)).normalize();

		double result1 = ray.getDirection().dotProduct(n1);
		double result2 = ray.getDirection().dotProduct(n2);
		double result3 = ray.getDirection().dotProduct(n3);

		if ((result1 > 0 && result2 > 0 && result3 > 0) || (result1 < 0 && result2 < 0 && result3 < 0)) {
			List<Point> intersections = plane.findIntersections(ray);
			// for (Point p : intersections) {
			// p.geometry = this; }
			return intersections;
		} 
		else {
			return null;
		}
	}
}
