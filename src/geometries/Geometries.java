package geometries;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * 
 */
public class Geometries implements Intersectable {
	
	private final List<Intersectable> bodies= new LinkedList<Intersectable>();
	
	public Geometries() {
		
	}
	public Geometries(Intersectable...geometries) {
		
	}
	public void add(Intersectable... geometries) {
		
	}
	@Override
	public List<Point> findIntersections(Ray ray) {
        List<Point> l = null;
        List<Point> intersectionsOfBody = null;
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
        if(l==null)
        	return null;
        //sort the intersections points according to the closeness to the head point of the ray
        return l.stream().sorted(Comparator.comparingDouble(p -> p.distance(ray.getHead())))
                .toList();
    }
}
