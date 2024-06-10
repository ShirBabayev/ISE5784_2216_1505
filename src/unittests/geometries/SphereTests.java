package unittests.geometries;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;

import primitives.*;

/**
 * Unit tests for geometries.Sphere class
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
class SphereTests {

	/**
	 * a point for test
	 */
	private final Point p100 = new Point(1, 0, 0);

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {

		// ============ Equivalence Partitions Tests ==============

		/* TC01: Check getNormal validity */
		Sphere sphere = new Sphere(new Point(1, 1, 1), 2);
		/*
		 * TC02: check whether the normal to the sphere returns the wanted result, if
		 * not - throw an exception
		 */
		assertEquals(new Vector(1, 0, 0), sphere.getNormal(new Point(3, 1, 1)),
				"ERROR: sphere getNormal(Point) returns wrong value");
	}

	/**
	 * Test method for {@link geometries.Sphere#findIntersections(Ray ray)}.
	 */
	@Test
	void testFindIntsersections() {

		final Sphere sphere = new Sphere(p100, 1);
		final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
		final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
		final var exp = List.of(gp1, gp2);
		final Vector v310 = new Vector(3, 1, 0);
		final Vector v110 = new Vector(1, 1, 0);
		final Point p01 = new Point(-1, 0, 0);
		// ============ Equivalence Partitions Tests ==============

		// TC01: Ray's line is outside the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere doesn't return null");

		// TC02: Ray starts before and crosses the sphere (2 points)
		final var result1 = sphere.findIntersections(new Ray(p01, v310)).stream()
				.sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
		assertEquals(2, result1.size(), "Wrong number of points");
		assertEquals(exp, result1, "Ray crosses sphere return wrong value");

		// TC03: Ray starts inside the sphere (1 point)
		var l1 = List.of(new Point(1.9797958971132712, 0, 0.2));
		assertEquals(l1, sphere.findIntersections(new Ray(new Point(1.5, 0, 0.2), new Vector(1, 0, 0))),
				"Ray crosses sphere return wrong value");

		// TC04: Ray starts after the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), v110)),
				"Ray's line out of sphere return wrong value");

		// =============== Boundary Values Tests ==================

		// **** Group: Ray's line crosses the sphere (but not the center)
		// TC11: Ray starts at sphere and goes inside (1 points)
		var l11 = List.of(new Point(1, 1, 0));
		assertEquals(l11, sphere.findIntersections(new Ray(new Point(0, 0, 0), v110)),
				"Ray starts at sphere and goes inside return wrong value");

		// TC12: Ray starts at sphere and goes outside (0 points)

		assertNull(sphere.findIntersections(new Ray(new Point(1, 1, 0), v110)),
				"Ray starts at sphere and goes outside does not return null");

		// **** Group: Ray's line goes through the center
		// TC13: Ray starts before the sphere (2 points)
		var l3 = List.of(new Point(0, 0, 0), new Point(2, 0, 0));
		assertEquals(l3, sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0))),
				"Ray starts before the sphere returns wrong value");

		// TC14: Ray starts at sphere and goes inside (1 points)
		var l2 = List.of(new Point(2, 0, 0));
		assertEquals(l2, sphere.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0))),
				"Ray starts at sphere and goes inside returns wrong value");

		// TC15: Ray starts inside (1 points)
		assertEquals(l2, sphere.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0))),
				"Ray starts inside the sphere returns wrong value");

		// TC16: Ray starts at the center (1 points)
		assertEquals(l2, sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(1, 0, 0))),
				"Ray starts at the center of the sphere returns wrong value");

		// TC17: Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),
				"Ray starts at sphere and goes outside does not return null");

		// TC18: Ray starts after sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0))),
				"Ray starts after sphere, does not return null");

		// **** Group: Ray's line is tangent to the sphere (all tests 0 points)
		// TC19: Ray starts before the tangent point
		assertNull(sphere.findIntersections(new Ray(new Point(0, -1, 0), new Vector(0, 1, 0))),
				"Ray starts before the tangent point, does not return null");
		// TC20: Ray starts at the tangent point
		assertNull(sphere.findIntersections(new Ray(new Point(0, 0, 0), new Vector(0, 1, 0))),
				"Ray starts at the tangent point, does not return null");
		// TC21: Ray starts after the tangent point
		assertNull(sphere.findIntersections(new Ray(new Point(0, 1, 0), new Vector(0, 1, 0))),
				"Ray starts after the tangent point, does not return null");

		// **** Group: Special cases
		// TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's
		// center line
		assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(0, 0, 1))),
				"Ray's line is outside, ray is orthogonal to ray start to sphere's center line, does not return null");

	}

}
