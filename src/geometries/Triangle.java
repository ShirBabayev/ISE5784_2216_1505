package geometries;

import primitives.Point;

/**
 * A class that represents a triangle by a polygon with 3 vertices
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class Triangle extends Polygon {
	
	/**
	 * construct the triangle by 3 vertexes.
	 * @param p1 first vertex
	 * @param p2 second vertex
	 * @param p3 third vertex
	 */
	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3);
	}
}
