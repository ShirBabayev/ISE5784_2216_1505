package geometries;

import java.util.List;

import primitives.*;
import static primitives.Util.*;

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
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double distance) {

		var intersection = plane.findGeoIntersections(ray,distance);
		if (intersection == null)
			return null;

		Point head = ray.getHead();
		Vector direction = ray.getDirection();

		// the vectors from the head of the ray to the vertices of the triangle
		Vector v1 = vertices.get(0).subtract(head);
		Vector v2 = vertices.get(1).subtract(head);
		Vector n1 = (v1.crossProduct(v2)).normalize();
		// dot product the direction of the ray with the normals to get the location of
		// each
		double result1 = alignZero(direction.dotProduct(n1));
		if (result1 == 0)
			return null;

		Vector v3 = vertices.get(2).subtract(head);
		Vector n2 = (v2.crossProduct(v3)).normalize();
		double result2 = alignZero(direction.dotProduct(n2));
		if (result1 * result2 <= 0)
			return null;

		Vector n3 = (v3.crossProduct(v1)).normalize();
		double result3 = alignZero(direction.dotProduct(n3));
		if (result1 * result3 <= 0)
			return null;
		
		// if all the results are with the same sign there is an intersection point
		intersection.get(0).geometry = this;
		return intersection;
		//return List.of(new GeoPoint(this, intersection.getFirst()));
	}
}
