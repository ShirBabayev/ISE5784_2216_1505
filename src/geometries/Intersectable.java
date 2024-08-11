package geometries;


import java.util.List;

import primitives.*;

/**
 * Interface for finding intersection points of a ray with the geometric body
 *
 * @author Hodaya Avidan and Shir Babayev
 */
public abstract class Intersectable {
	
	protected static boolean bvh = false;

    BoundingBox boundingBox = null;

	
	/**
	 * a class representing a geometric point - every geoPoint contains a 3
	 * coordinate point and a geometry body that the point is on it.
	 */
	public static class GeoPoint {
		/**
		 * geometry body that the point is on it.
		 */
		public Geometry geometry;
		/**
		 * 3 coordinate point - exists on the geometry
		 */
		public Point point;

		/**
		 * A constructor that construct the geoPoint by geometry body and point
		 * 
		 * @param g is geometry body
		 * @param p is a point on the body
		 */
		public GeoPoint(Geometry g, Point p) {
			geometry = g;
			point = p;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			/* Checking if an object of type Point was received */
			return (obj instanceof GeoPoint other) //
					&& this.geometry == other.geometry && this.point.equals(other.point);
		}

		@Override
		public String toString() {
			return "" + point + " " + geometry;
		}
	}
	
	public class BoundingBox {
		   
		 protected double xMin;
		 protected double xMax;

		 protected double yMin;
		 protected double yMax;

		 protected double zMin;
		 protected double zMax;


	    public BoundingBox() {

	         this.xMin=Double.NEGATIVE_INFINITY;
	          this.xMax= Double.MAX_VALUE;

	        this.yMin=Double.NEGATIVE_INFINITY;
	        this.yMax= Double.MAX_VALUE;

	        this.zMin=Double.NEGATIVE_INFINITY;
	        this.zMax= Double.MAX_VALUE;

	    }

	   
	    public BoundingBox(double xMin,double yMin,double zMin,double xMax,double yMax,double zMax) {
	    
	       this.xMin=xMin;
	       this.yMin=yMin;
	       this.zMin=zMin;
	       
	       this.xMax=xMax;
	       this.yMax=yMax;
	       this.zMax=zMax;
	    }

	    protected boolean intersect(Ray ray, double dis) {
			Point head = ray.getHead();
			double xHead = head.getX();
			double yHead = head.getY();
			double zHead = head.getZ();
			Vector direction = ray.getDirection();
			double xDirection = direction.getX();
			double yDirection = direction.getY();
			double zDirection = direction.getZ();

			double disMin = Double.NEGATIVE_INFINITY;
			double disMax = Double.POSITIVE_INFINITY;


			if (xDirection > 0) {
				disMin = (xMin - xHead) / xDirection; 
				disMax = (xMax - xHead) / xDirection;
			} else if (xDirection < 0) {
				disMin = (xMax - xHead) / xDirection;
				disMax = (xMin - xHead) / xDirection;
			}

			double disMinY = Double.NEGATIVE_INFINITY;
			double disMaxY = Double.POSITIVE_INFINITY;
			if (yDirection > 0) {
				disMinY = (yMin - yHead) / yDirection;
				disMaxY = (yMax - yHead) / yDirection;
			} else if (yDirection < 0) {
				disMinY = (yMax - yHead) / yDirection;
				disMaxY = (yMin - yHead) / yDirection;
			}

			
			if ((disMin > disMaxY) || (disMinY > disMax))
				return false;

			if (disMinY > disMin)
				disMin = disMinY;
			if (disMaxY < disMax)
				disMax = disMaxY;

			double disMinZ = Double.NEGATIVE_INFINITY;
			double disMaxZ = Double.POSITIVE_INFINITY;
			if (zDirection > 0) {
				disMinZ = (zMin - zHead) / zDirection;
				disMaxZ = (zMax - zHead) / zDirection;
			} else if (zDirection < 0) {
				disMinZ = (zMax - zHead) / zDirection;
				disMaxZ = (zMin - zHead) / zDirection;
			}

			
			return disMin <= disMaxZ && disMinZ <= disMax;
		}
	}
	
	public static void setBvh() {
		Intersectable.bvh = true;
	}

	/**
	 * The functions gets a ray and find a list of points that intersects the
	 * geometry body
	 * 
	 * @param ray is a ray that intersect (or not) the geometry body
	 * @return list of intersections point
	 */
	public final List<GeoPoint> findGeoIntersections(Ray ray,double maxDistance) {
		return boundingBox != null && !boundingBox.intersect(ray, maxDistance) ? null : findGeoIntersectionsHelper(ray, maxDistance);
		}

	/**
	 * The functions gets a ray and find a list of points that intersects the
	 * geometry body
	 * 
	 * @param ray is a ray that intersect (or not) the geometry body
	 * @return list of intersections point
	 */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance);
	
	
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
	}
	
	/**
	 * calculates the points where the ray intersects with the geometry
	 * 
	 * @param ray - the ray that Intersects with the geometry
	 * @return geoList list of intersection point between the ray and the geometry
	 */
	public final List<Point> findIntersections(Ray ray) {
		var geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}


}