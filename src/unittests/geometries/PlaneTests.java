package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.*;
import geometries.Plane;

/**
 * Unit tests for geometries.Plane class
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
class PlaneTests {
	/** accuracy for the numeric test results */
	private static final double DELTA = 0.00001;
	/** a set of points for the tests */
	private static final Point[] pts = { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
			new Point(-1, 1, 1) };
	/** a plane for the tests */
	private static final Plane plane1 = new Plane(pts[0], pts[1], pts[2]);
	/** a numeric result for several tests */
	private static final double d = Math.sqrt(3) / 3;
	/** an orthogonal vector to plane1 for tests */
	private static final Vector v1 = new Vector(d, d, d);

	/**
	 * Test method for {@link geometries.Plane#Plane(Point, Point, Point)}.
	 */
	@Test
	void testConstructor() {

		// =============== Boundary Values Tests ==================

		/*
		 * TC01: checks 2 points are same throws exception
		 */
		assertThrows(Exception.class, () -> new Plane(pts[0], pts[0], pts[1]),
				"ERROR: 2 points are same does not throw exception");
		assertThrows(Exception.class, () -> new Plane(pts[0], pts[1], pts[1]),
				"ERROR: 2 points are same does not throw exception");
		assertThrows(Exception.class, () -> new Plane(pts[1], pts[0], pts[1]),
				"ERROR: 2 points are same does not throw exception");

		/*
		 * TC02: checks 3 points on a same line throws exception -
		 */
		assertThrows(Exception.class, () -> new Plane(new Point(1, 2, 3), new Point(2, 3, 4), new Point(3, 4, 5)),
				"ERROR: 2 points are same does not throw exception");

	}

	/**
	 * Test method for {@link geometries.Plane#getNormal()}.
	 */
	@Test
	void testGetNormal() {

		// ============ Equivalence Partitions Tests ==============

		/* TC03: Checks that the function returns a result */
		assertDoesNotThrow(() -> plane1.getNormal(), "ERROR: normal of the plane does not work");
		/* TC04: Checks that the function returns a correct result */
		assertEquals(v1, plane1.getNormal(), "ERROR: normal of the plane does not work correctly");
		/*
		 * TC05: Checks that the normal is indeed perpendicular to the vectors on the
		 * plane
		 */
		Vector result = plane1.getNormal();
		for (int i = 0; i < 3; ++i)
			/*
			 * Each time creates a vector from 2 points on the plane and checks that the
			 * normal is indeed perpendicular to this vector
			 */
			assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])),
					"Plane's normal is not orthogonal to one of the vectors");

	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormalPoint() {

		// ============ Equivalence Partitions Tests ==============

		/* TC06: Checks that the function does indeed return a result */
		assertDoesNotThrow(() -> plane1.getNormal(pts[0]), "ERROR: normal of the plane does not work");
		/* TC07: Checks that the function returns a correct result */
		assertEquals(v1, plane1.getNormal(pts[0]), "ERROR: normal of the plane does not work correctly");
		/*
		 * TC08: Checks that the normal is indeed orthogonal to the vectors on the plane
		 */
		Vector result = plane1.getNormal(new Point(0, 0, 1));
		for (int i = 0; i < 3; ++i)
			/*
			 * Each time creates a vector from 2 points on the plane and checks that the
			 * normal is indeed perpendicular to this vector
			 */
			assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])),
					"Plane's normal is not orthogonal to one of the edges");
	}

	/**
	 * Test method for {@link geometries.Plane#findIntersections(Ray ray)}.
	 */
	@Test
	void testFindIntsersections() {

		final Vector v = new Vector(0, 3, 0);
		// ============ Equivalence Partitions Tests ==============

		// TC01: The ray intersects the plane
		var exp1 = List.of(new Point(0.5, 0.5, 0));
		assertEquals(exp1, plane1.findIntersections(new Ray(new Vector(1, 1, 0), Point.ZERO)),
				"The ray intersects the plane returns wrong value");
		// TC02: The ray does not intersect the plane
		assertNull(plane1.findIntersections(new Ray(new Vector(1, 1, 0), new Point(1, 1, 2))),
				"parallel ray to plane does not return null ");

		// =============== Boundary Values Tests ==================

		/* parallel rays to the plane */
		// TC11: The ray out of the plane
		// assertNull( plane1.findIntersections(new Ray(new
		// Vector(0,-3,3),Point.ZERO)),"parallel ray to plane does not return null ");
		// TC12: The ray in contained in the plane
		assertNull(plane1.findIntersections(new Ray(pts[0].subtract(pts[1]), pts[0])),
				"parallel ray to plane does not return null ");

		/* Ray is orthogonal to the plane */
		// TC13: ray start before the plane and intersect it
		double d1 = 0.33333333333335;
		var exp2 = List.of(new Point(d1, d1, d1));
		assertEquals(exp2, plane1.findIntersections(new Ray(new Vector(d, d, d), new Point(0, 0, 0))),
				"ray start before the plane returns wrong value ");
		// TC14: ray start inside the plane
		assertNull(plane1.findIntersections(new Ray(v1, pts[0])), "ray start inside the plane does not return null ");
		// TC15: ray start after the plane and does not intersect it
		assertNull(plane1.findIntersections(new Ray(v1, (Point) v)), "ray start after the plane does not return null ");

		/* Ray start at point that contained in the plane */
		// TC16: ray starts at some point in the plane
		assertNull(plane1.findIntersections(new Ray(v, pts[1])),
				"ray starts at some point in the plane does not return null ");
		// TC17: ray start at q - the point that represents the plane
		assertNull(plane1.findIntersections(new Ray(v, pts[0])),
				"ray start at q - the point that represents the plane does not return null ");

	}

}
