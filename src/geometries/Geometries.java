package geometries;

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
	
	
	public List<Point> findIntersections(Ray ray) {
		return null;
	}
}
