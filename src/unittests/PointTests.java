package unittests;
import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

/**
 * Unit tests for primitives.Point class
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class PointTests {
	Point p1 = new Point(1, 2, 3);
	Point p2 = new Point(2, 4, 6);
	Point p3 = new Point(2, 4, 5);

	Vector v1 = new Vector(1, 2, 3);
	Vector v1Opposite = new Vector(-1, -2, -3);
	
	// ============ Equivalence Partitions Tests ==============
	
	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		// TC01: test Adding a vector to a point if it returns the right result
		// Add vector to point
		assertEquals(p1.add(v1), p2, "ERROR: (point + vector) = other point does not work correctly");
		// TC02: test Adding an opposite vector to a point if it returns the ZERO point
		assertEquals(p1.add(v1Opposite), Point.ZERO,
				"ERROR: (point + vector) = center of coordinates does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {
		// TC11: test Subtract two points returns the right vector
		assertEquals(p2.subtract(p1), v1, "ERROR: (point2 - point1) does not work correctly");
		// TC12: test zero vector Exception accepted from Subtracting a point from
		// itself:
		assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1),
				"ERROR: (point - itself) does not throw an exception");// or "throws wrong exception"

	}

	// =============== Boundary Values Tests ==================
	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {
		// TC21: test that point distance to itself returns ZERO point
		assertEquals(Point.ZERO, p1.distance(p1), "ERROR: point distance to itself is not zero");
		// TC22: test that distance between a point to itself returns the right result
		// (ZERO)
		assertEquals(Point.ZERO, p1.distance(p3) - 3, "ERROR: distance between points to itself is wrong");
		assertEquals(Point.ZERO, p3.distance(p1) - 3, "ERROR: distance between points to itself is wrong");
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
		// TC31: test that point squared distance to itself returns ZERO point
		// distances
		// assertEquals(p1.distanceSquared(p1),Point.ZERO,"ERROR: point squared distance
		// to itself is not zero");
		assertEquals(Point.ZERO, p1.distanceSquared(p1), "ERROR: point squared distance to itself is not zero");
		// TC32: test that when squared distance between points is wrong it returns the
		// right result
		// assertEquals(p1.distanceSquared(p3) - 9,Point.ZERO,"ERROR: squared distance
		// between points is wrong");
		assertEquals(Point.ZERO, p1.distanceSquared(p3) - 9, "ERROR: squared distance between points is wrong");
		// assertEquals(p3.distanceSquared(p1) - 9,Point.ZERO,"ERROR: squared distance
		// between points is wrong");
		assertEquals(Point.ZERO, p3.distanceSquared(p1) - 9, "ERROR: squared distance between points is wrong");
	}

}
