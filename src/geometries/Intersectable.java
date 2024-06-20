package geometries;

import java.util.List;
import primitives.*;

/**
 * Interface for finding intersection points of a ray with the geometric body
 *
 * @author Hodaya Avidan and Shir Babayev
 */
public abstract class Intersectable {
	/**
	 * calculates the points where the ray intersects with the geometry
	 * 
	 * @param ray - the ray that Intersects with the geometry
	 * @return a list of intersection point between the ray and the geometry
	 */
	public abstract List<Point> findIntersections(Ray ray);
	
	public static class GeoPoint {
		 public Geometry geometry;
		 public Point point;
		 
		 public GeoPoint(Geometry g, Point p) {
			 geometry=g;
			 point=p;
		 }
		 @Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				/* Checking if an object of type Point was received */
				return (obj instanceof GeoPoint other) 
						&& this.geometry.equals(other.geometry)
						&&this.point.equals(other.point);
			}
		 @Override
			public String toString() {
				return "" + point + " " + geometry;
			}
	}
	public List<GeoPoint> findGeoIntersections(Ray ray){
		return findGeoIntersectionsHelper(ray);
		}
	protected abstract  List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}