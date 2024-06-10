package unittests.primitives;

import primitives.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * a class of tests for ray
 */
class RayTests {

	/**
	 * Test method for {@link primitives.Ray#getPoint(double)}.
	 */
	@Test
	void testGetPoint() {
		/** a ray for the tests */
		Ray ray = new Ray(Point.ZERO, new Vector(1, 0, 0));
		// ============ Equivalence Partitions Tests ==============
		// TC01: a positive distance
		assertEquals(new Point(5, 0, 0), ray.getPoint(5),
				"ERROR: the point at this distance from the head point is wrong");
		// TC02: a negative distance
		assertEquals(new Point(-5, 0, 0), ray.getPoint(-5),
				"ERROR: the point at this distance from the head point is wrong");
		// =============== Boundary Values Tests ==================
		// TC11: no distance from the head of the ray
		assertEquals(Point.ZERO, ray.getPoint(0), "ERROR: the point at this distance from the head point is wrong");

	}

}
