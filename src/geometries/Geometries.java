package geometries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import primitives.Ray;

/**
 * implementation of Intersectable
 */
public class Geometries extends Intersectable {

	/**
	 * a list of geometry bodies
	 */
	private final List<Intersectable> bodies = new LinkedList<Intersectable>();

	/**
	 * empty default constructor
	 */
	public Geometries() {
	}

	/**
	 * A constructor that accepts an unlimited number of geometric bodies and adds
	 * them to a list that combines the bodies
	 * 
	 * @param geometries - list of geometric bodies.
	 */
	public Geometries(Intersectable... geometries) {
		add(geometries);
	}

	/**
	 * accepts an unlimited number of geometric bodies and adds them to a list that
	 * combines the bodies
	 * 
	 * @param geometries - list of geometric bodies.
	 */
	public void add(Intersectable... geometries) {
		bodies.addAll(Arrays.asList(geometries));
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		List<GeoPoint> l = null;
		// Checks each body separately if it has intersection points with the beam
		for (Intersectable body : bodies) {
			var intersectionsOfBody = body.findGeoIntersections(ray);
			if (intersectionsOfBody != null) {
				// create a new list because it is the first value
				if (l == null)
					l = new LinkedList<>(intersectionsOfBody);
				else
					// insert the intersections points that were found
					l.addAll(intersectionsOfBody);
			}
		}
		return l;
	}
}
