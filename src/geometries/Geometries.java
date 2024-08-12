package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Ray;

/**
 * implementation of Intersectable
 */
public class Geometries extends Intersectable {

	/**
	 * a list of final geometry bodies
	 */
	private final List<Intersectable> bodies = new LinkedList<Intersectable>();

	/**
	 * a list of infinite geometry bodies
	 */
	private final List<Intersectable> infiniteBodies = new LinkedList<>();

	/**
	 * empty default constructor
	 */
	public Geometries() {
	}

	/**
	 * A constructor that accepts an unlimited number of geometric bodies and adds
	 * them to a list that combines the bodies
	 * 
	 * @param geometries - unlimited number of geometric bodies.
	 */
	public Geometries(Intersectable... geometries) {
		add(geometries);
	}

	/**
	 * A constructor that accepts an list of geometric bodies and adds them to a
	 * list that combines the bodies
	 * 
	 * @param geometries - list of geometric bodies.
	 */
	public Geometries(List<Intersectable> geometries) {
		add(geometries);
	}

	/**
	 * accepts an unlimited number of geometric bodies and adds them to a list that
	 * combines the bodies
	 * 
	 * @param geometries - unlimited number of geometric bodies.
	 */
	public void add(Intersectable... geometries) {
		add(List.of(geometries));
	}

	/**
	 * accepts an list of geometric bodies and adds them to a list that combines the
	 * bodies
	 * 
	 * @param geometries - list of geometric bodies.
	 */
	public void add(List<Intersectable> geometries) {
		if (!bvh) {
			bodies.addAll(geometries);
		}

		for (Intersectable g : geometries) {
			if (g.boundingBox == null) {
				infiniteBodies.add(g);
				boundingBox = null;
			} else {
				if (infiniteBodies.isEmpty()) {
					if (boundingBox == null)
						boundingBox = new BoundingBox();

					// check x
					if (g.boundingBox.xMin < boundingBox.xMin)
						boundingBox.xMin = g.boundingBox.xMin;
					if (g.boundingBox.xMax > boundingBox.xMax)
						boundingBox.xMax = g.boundingBox.xMax;

					// y
					if (g.boundingBox.yMin < boundingBox.yMin)
						boundingBox.yMin = g.boundingBox.yMin;
					if (g.boundingBox.yMax > boundingBox.yMax)
						boundingBox.yMax = g.boundingBox.yMax;

					// z
					if (g.boundingBox.zMin < boundingBox.zMin)
						boundingBox.zMin = g.boundingBox.zMin;
					if (g.boundingBox.zMax > boundingBox.zMax)
						boundingBox.zMax = g.boundingBox.zMax;
				}
				bodies.add(g);
			}
		}
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

		List<GeoPoint> l = null;
		// Checks each body separately if it has intersection points with the beam
		for (Intersectable body : bodies) {
			var intersectionsOfBody = body.findGeoIntersections(ray, maxDistance);
			if (intersectionsOfBody != null) {
				// create a new list because it is the first value
				if (l == null)
					l = new LinkedList<>(intersectionsOfBody);
				else
					// insert the intersections points that were found
					l.addAll(intersectionsOfBody);
			}
		}

		for (Intersectable body : infiniteBodies) {
			var intersectionsOfBody = body.findGeoIntersections(ray, maxDistance);
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

	/**
	 * create the hierarchy and put into the right boxes
	 */
	public void setBVH() {
		double x = boundingBox.xMax - boundingBox.xMin;
		double y = boundingBox.yMax - boundingBox.yMin;
		double z = boundingBox.zMax - boundingBox.zMin;
		// which axis is more central in the scene
		setBVH(y > x && y > z ? 1 : z > x && z > y ? 2 : 0, 3);
	}

	/**
	 * create the hierarchy and put into bounding boxes
	 * 
	 * @param axis  is the central axis that we divide according to.
	 * @param count is the depth of the hierarchy
	 */
	private void setBVH(int axis, int count) {
		//if the bvh is turn on
		if (!bvh || count == 0)
			return;
		// min amount of geometries in a box
		if (bodies.size() > 4) {
			if (boundingBox == null) {
				var finites = new Geometries(bodies);
				bodies.clear();
				this.add(finites.bodies);
				return;
			}

			var l = new Geometries();
			var m = new Geometries();
			var r = new Geometries();
			double midX = (boundingBox.xMax + boundingBox.xMin) / 2;
			double midY = (boundingBox.yMax + boundingBox.xMin) / 2;
			double midZ = (boundingBox.zMax + boundingBox.zMin) / 2;
			switch (axis) {
			case 0:
				for (var g : bodies) {
					if (g.boundingBox.xMin > midX)
						r.add(g);
					else if (g.boundingBox.xMax < midX)
						l.add(g);
					else
						m.add(g);
				}
				break;
			case 1:
				for (var g : bodies) {
					if (g.boundingBox.yMin > midY)
						r.add(g);
					else if (g.boundingBox.yMax < midY)
						l.add(g);
					else
						m.add(g);
				}
				break;
			case 2:
				for (var g : bodies) {
					if (g.boundingBox.zMin > midZ)
						r.add(g);
					else if (g.boundingBox.zMax < midZ)
						l.add(g);
					else
						m.add(g);
				}
				break;
			}

			int nextAxis = (axis + 1) % 3;
			int lsize = l.bodies.size();
			int msize = m.bodies.size();
			int rsize = r.bodies.size();
			bodies.clear();
			if (lsize <= 2 || msize + rsize == 0) {
				this.add(l.bodies);
				if (msize + rsize == 0)
					this.setBVH(nextAxis, count - 1);
			} else {
				bodies.add(l);
			}

			if (msize <= 2 || lsize + rsize == 0) {
				this.add(m.bodies);
				if (lsize + rsize == 0)
					this.setBVH(nextAxis, count - 1);
			} else {
				bodies.add(m);
			}

			if (rsize <= 2 || lsize + msize == 0) {
				this.add(r.bodies);
				if (lsize + msize == 0)
					this.setBVH(nextAxis, count - 1);
			} else {
				bodies.add(r);
			}
		}

		for (var geo : this.bodies)
			if (geo instanceof Geometries geos)
				geos.setBVH();
		return;
	}

}
