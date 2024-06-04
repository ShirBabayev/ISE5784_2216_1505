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
	/*
	 * @Override public List<Point> findGeoIntersectionsHelper(Ray ray) { // Find
	 * the intersection point with the triangle's plane var planeIntersections =
	 * this.plane.findIntersections(ray); if (planeIntersections == null) { return
	 * null; // No intersection with the plane, so no intersection with the triangle
	 * } // Calculate barycentric coordinates Vector v1 =
	 * vertices.get(0).subtract(ray.getHead()).normalize(); Vector v2 =
	 * vertices.get(1).subtract(ray.getHead()).normalize(); Vector v3 =
	 * vertices.get(2).subtract(ray.getHead()).normalize();
	 * 
	 * Vector n1 = v1.crossProduct(v2).normalize(); Vector n2 =
	 * v2.crossProduct(v3).normalize(); Vector n3 = v3.crossProduct(v1).normalize();
	 * 
	 * double ans1 = ray.getDirection().dotProduct(n1); double ans2 =
	 * ray.getDirection().dotProduct(n2); double ans3 =
	 * ray.getDirection().dotProduct(n3);
	 * 
	 * if((ans1>0 && ans2>0 && ans3>0) || (ans1<0 && ans2<0 && ans3<0)) { //return
	 * this.plane.findGeoIntersectionsHelper(ray);
	 * 
	 * //reassigning geo of geoPoint to be the triangle, because it calls the
	 * funcitom //of the plane, which does not have an emission color List<Point>
	 * result = this.plane.findGeoIntersectionsHelper(ray); for (Point gp : result)
	 * { gp.geometry = this; } return result; } else { return null; }
	 */

	@Override
	public List<Point> findIntersections(Ray ray){
	    List<Point> intersections = null;// new LinkedList<Point>();
		 // Triangle vertices
	    
	    Point p1 = vertices.get(0);
	    Point p2 = vertices.get(1);
	    Point p3 = vertices.get(2);
	    
	    // Edges of the triangle
	    Vector edge1 = p2.subtract(p1);
	    Vector edge2 = p3.subtract(p1);
	    
	    // Calculate determinants
	    Vector h = ray.getDirection().crossProduct(edge2);
	    double a = edge1.dotProduct(h);
	    
	    // Check if ray is parallel to the triangle
	    if (a > -1e-8 && a < 1e-8) {
	        return null;
	    }
	    
	    double f = 1.0 / a;
	    Vector s = ray.getHead().subtract(p1);
	    double u = f * (s.dotProduct(h));
	    
	    if (u < 0.0 || u > 1.0) {
	        return null;
	    }
	    
	    Vector q = s.crossProduct(edge1);
	    double v = f * (ray.getDirection().dotProduct(q));
	    
	    if (v < 0.0 || u + v > 1.0) {
	        return null;
	    }
	    
	    // Calculate t to find out where the intersection point is on the line
	    double t = f * (edge2.dotProduct(q));
	    
	    if (t > 1e-8) { // ray intersection
	        Point intersectionPoint = ray.getHead().add(ray.getDirection().scale(t));
	        intersections.add(intersectionPoint);
	        return intersections;
	    } 
	    else { // This means that there is a line intersection but not a ray intersection.
	        return null;
	    }
		//return null; }
	}
}
