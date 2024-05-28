package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

/**
 * Unit tests for primitives.Point class
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class PointTests {
	/** accuracy for the numeric test results */
	private static final double DELTA = 0.00001;
	/** a point for the tests */
	private static final Point p1 = new Point(1, 2, 3);
	/** a point for the tests */
	private static final Point p2 = new Point(2, 4, 6);
	/** a point for the tests */
	private static final Point p3 = new Point(2, 4, 5);

	/** a vector for the tests */
	private static final Vector v1 = new Vector(1, 2, 3);
	/** a vector for the tests */
	private static final Vector v1Opposite = new Vector(-1, -2, -3);

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {

		// ============ Equivalence Partitions Tests ==============

		// TC01: test Adding a vector to a point if it returns the right result Add
		// vector to point
		assertEquals(p1.add(v1), p2, "ERROR: (point + vector) = other point does not work correctly");

		// =============== Boundary Values Tests ==================

		// TC02: test Adding an opposite vector to a point if it returns the ZERO point
		assertEquals(p1.add(v1Opposite), Point.ZERO,
				"ERROR: (point + vector) = center of coordinates does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {

		// ============ Equivalence Partitions Tests ==============

		/* TC11: test Subtract two points returns the right vector */
		assertEquals(p2.subtract(p1), v1, "ERROR: (point2 - point1) does not work correctly");

		// =============== Boundary Values Tests ==================

		/*
		 * TC12: test zero vector Exception accepted from Subtracting a point from
		 * itself
		 */
		assertThrows(Exception.class, () -> p1.subtract(p1), "ERROR: (point - itself) does not throw an exception");
		/*
		 * TC13: test zero vector correct Exception accepted from Subtracting a point
		 * from itself
		 */
		assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1),
				"ERROR: (point - itself) does not throw an exception");

	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {

		// =============== Boundary Values Tests ==================

		// TC21: test that point distance to itself returns ZERO point
		assertEquals(0, p1.distance(p1), DELTA, "ERROR: point distance to itself is not zero");

		// ============ Equivalence Partitions Tests ==============

		// TC22: test that distance between a point to itself returns the right result
		// (ZERO)
		assertEquals(3, p1.distance(p3), DELTA, "ERROR: distance between points to itself is wrong");
		assertEquals(3, p3.distance(p1), DELTA, "ERROR: distance between points to itself is wrong");
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
		// =============== Boundary Values Tests ==================

		// TC31: test that point squared distance to itself returns ZERO point distances
		assertEquals(0, p1.distanceSquared(p1), DELTA, "ERROR: point squared distance to itself is not zero");

		// ============ Equivalence Partitions Tests ==============

		// TC32: test that when squared distance between points is wrong it returns the
		// right result between points is wrong");
		assertEquals(9, p1.distanceSquared(p3), DELTA, "ERROR: squared distance between points is wrong");
		assertEquals(9, p3.distanceSquared(p1), DELTA, "ERROR: squared distance between points is wrong");
	}

}
