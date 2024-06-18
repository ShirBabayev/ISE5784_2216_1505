package unittests.primitives;

import primitives.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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

	/**
	 * Test method for {@link primitives.Ray#findClosestPoint(List)}.
	 */
	@Test
	void testFindClosestPoint() {

		Point p100 = new Point(1, 0, 0);
		Point p200 = new Point(2, 0, 0);
		Point p300 = new Point(3, 0, 0);
		Vector v100 = new Vector(1, 0, 0);
		List<Point> list = List.of(p100, p200, p300); // הפונקציה מקבלת רשימה ולכן שמנו את הנקודות ברשימה
		// ============ Equivalence Partitions Tests ==============
		// EP01: closest point is in the middle of the list
		assertEquals(p200, new Ray(new Point(2.1, 0, 0), v100).findClosestPoint(list),
				"closest point is in the middle of the list");

		// =============== Boundary Values Tests ==================

		// BV01: empty list
		assertNull(new Ray(new Point(2.1, 0, 0), v100).findClosestPoint(null), "empty list");

		// BV02: first point in the list
		assertEquals(p100, new Ray(new Point(1.1, 0, 0), v100).findClosestPoint(list), "first point in the list");

		// BV03: last point in the list
		assertEquals(p300, new Ray(new Point(3.1, 0, 0), v100).findClosestPoint(list), "last point in the list");
	}

}
