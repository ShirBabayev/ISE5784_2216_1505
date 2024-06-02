package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import geometries.Plane;

/**
 * Unit tests for geometries.Plane class
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
class PlaneTests {

	/** a set of points for the tests */
	private static final Point[] pts = { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
			new Point(-1, 1, 1) };
	/** a plane for the tests */
	private static final Plane plane1 = new Plane(pts[0], pts[1], pts[2]);
	/** a numeric result for several tests */
	private static final double d = Math.sqrt(3) / 3;

	/**
	 * Test method for {@link geometries.Plane#constructor()}.
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
		assertEquals(new Vector(d, d, d), plane1.getNormal(), "ERROR: normal of the plane does not work correctly");
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
		assertEquals(new Vector(d, d, d), plane1.getNormal(pts[0]),
				"ERROR: normal of the plane does not work correctly");
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
	 * Test method for {@link geometries.plane#findIntsersections}.
	 */
	@Test 
	void testFindIntsersections(){
		
	}

}
