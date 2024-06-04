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
		if(ray.getHead().equals(center))
        {
	        List<Point> intersections = new LinkedList<Point>();
            intersections.add(center.add(ray.getDirection().scale(radius)));
            return intersections;
        }

        Vector u= center.subtract(ray.getHead()); //a vector from the head of the ray to the center of the sphere
        double tm = ray.getDirection().dotProduct(u);// tm = v*u
        double d = Math.sqrt(u.lengthSquared()-tm*tm);//d=sqrt(|u|^2-tm^2
        if(d>=radius)
            return null;
        double th = Math.sqrt(radius*radius - d*d);
        double t1 = tm + th;
        double t2 = tm - th;

        if(t1<=0 && t2 <=0)
            return null;
        List<Point> intersections = new LinkedList<Point>();
        if (t1>0)
        	intersections.add(ray.getHead().add(ray.getDirection().scale(t1)));
        if (t2>0 && t2!=t1)
            intersections.add(ray.getHead().add(ray.getDirection().scale(t2)));
        //sort the intersections points according to the closeness to the head point of the ray
        return intersections.stream().sorted(Comparator.comparingDouble(p -> p.distance(ray.getHead())))
        .toList();

    }


}
