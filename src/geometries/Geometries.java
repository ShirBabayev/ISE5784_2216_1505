package geometries;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * implementation of Intersectable
 */
public class Geometries implements Intersectable {
	
	/**
	 * a list of geometry bodies
	 */
	private final List<Intersectable> bodies= new LinkedList<Intersectable>();
	
	/**
	 * empty default constructor  
	 */
	public Geometries() {
		
	}
	
	/**
	 * A constructor that accepts an unlimited number of geometric bodies and adds 
	 * them to a list that combines the bodies
	 * @param geometries - list of geometric bodies.
	 */
	public Geometries(Intersectable...geometries) {
		add(geometries);
	}
	
	/**
	 * accepts an unlimited number of geometric bodies and adds 
	 * them to a list that combines the bodies
	 * @param geometries - list of geometric bodies.
	 */
	public void add(Intersectable... geometries) {
		bodies.addAll(Arrays.asList(geometries));
	}
	
	@Override
	public List<Point> findIntersections(Ray ray) {
        List<Point> l = null;
        List<Point> intersectionsOfBody = null;
        //Checks each body separately if it has intersection points with the beam
        for (Intersectable body : bodies) {
        	intersectionsOfBody = body.findIntersections(ray);
            if (intersectionsOfBody != null) {
            	// create a new list because it is the first value
                if(l==null) {                     
                	l = new LinkedList<>();
                }
                //insert the intersections points that were found
                l.addAll(intersectionsOfBody);
            }
        }
        //None of the bodies had a cutoff point
        if(l==null)
        	return null;
        //sort the intersections points according to the closeness to the head point of the ray
        return l.stream().sorted(Comparator.comparingDouble(p -> p.distance(ray.getHead())))
                .toList();
    }
}
