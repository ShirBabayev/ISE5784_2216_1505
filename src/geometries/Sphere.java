package geometries;
import java.util.List;
import java.util.*;

import primitives.*;
import primitives.Vector;

/**
 * A class that represents a sphere by a point and a radius
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class Sphere extends RadialGeometry {

	/**
	 * the center point of the sphere
	 */
	final private Point center;

	/**
	 * A constructor that initializes the center point and the radius of the sphere
	 * 
	 * @param p      - center point
	 * @param radius - radius
	 */
	public Sphere(Point p, double radius) {
		super(radius);
		center = p;
	}

	@Override
	public Vector getNormal(Point p) {
		return p.subtract(center).normalize();
	}
	
	@Override
	public List<Point> findIntersections(Ray ray){
		if(ray.getHead().equals(center))//The ray starts in the center
        {
	        List<Point> intersections = new LinkedList<Point>();//empty list
            intersections.add(ray.getPoint(radius));//add the intersection point to the list
            return intersections;
        }

        Vector u= center.subtract(ray.getHead()); //a vector from the head of the ray to the center of the sphere
        double tm = ray.getDirection().dotProduct(u);// tm = v*u
        double d = Math.sqrt(u.lengthSquared()-tm*tm);//d=sqrt(|u|^2-tm^2
        if(d>=radius)//outside the sphere - no intersections
            return null;
        
        double th = Math.sqrt(radius*radius - d*d);
        double t1 = tm + th;
        double t2 = tm - th;
        
        //the head of the ray is not included, so t=0 (or close to 0) is the head and t<0, both are not relevant
        if(t1<=0.0001 && t2 <=0.0001)
            return null;
        List<Point> intersections = new LinkedList<Point>();//create empty list
        if (t1>0.0001)
        	intersections.add(ray.getPoint(t1));
        if (t2>0.0001 && t2!=t1)
            intersections.add(ray.getPoint(t2));
        //sort the intersections points according to the closeness to the head point of the ray

        return intersections.stream().sorted(Comparator.comparingDouble(p -> p.distance(ray.getHead())))
        .toList();//sort the list by closeness to the ray's head.

    }


}
