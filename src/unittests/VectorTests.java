package unittests.primitivesTests;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import primitives.Double3;
import primitives.Point;
import primitives.Vector;

/**
 * Unit tests for primitives.Vector class
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class VectorTests {

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	private static final Point p1 = new Point(1, 2, 3);
	private static final Point p2 = new Point(2, 4, 6);
	private static final Point p3 = new Point(2, 4, 5);

	private static final Vector v1 = new Vector(1, 2, 3);
	private static final Vector v1Opposite = new Vector(-1, -2, -3);
	private static final Vector v2 = new Vector(-2, -4, -6);
	private static final Vector v3 = new Vector(0, 3, -2);
	private static final Vector v4 = new Vector(1, 2, 2);

	@Test
	void testConstructor() {
		assertThrows(Exception.class, () -> new Vector(0, 0, 0), "ERROR: zero vector does not throw an exception");
		assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0),
				"ERROR: zero vector does not throw a correct exception");

		assertThrows(IllegalArgumentException.class, () -> new Vector(Double3.ZERO),
				"ERROR: zero vector does not throw an exception");
	}

	@Test
	void testLengthSquared() {
		assertEquals(v4.lengthSquared(), 9, "ERROR: lengthSquared() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {

		assertEquals(v4.length(), 3, "ERROR: length() wrong value");

	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		
		assertThrows(Exception.class, ()->v1.add(v1Opposite), "ERROR: result of zero vector does not throw an exception");
		assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite),
				"ERROR: result of zero vector does not throw a correct exception");

		assertThrows(Exception.class, ()->v1.subtract(v1), "ERROR: Vector - itself does not throw an exception");
		assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1),
				"ERROR: Vector + itself throws wrong exception");

		assertEquals(v1.add(v2), v1Opposite, "ERROR: Vector + Vector does not work correctly");
		assertEquals(v1.subtract(v2), new Vector(3, 6, 9), "ERROR: Vector + Vector does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		assertEquals(v1.dotProduct(v3), 0, "ERROR: dotProduct() for orthogonal vectors is not zero");
		assertEquals(v1.dotProduct(v2) + 28, 0, "ERROR: dotProduct() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		assertThrows(Exception.class, () -> v1.crossProduct(v2),
				"ERROR: crossProduct() for parallel vectors does not throw an exception");
		Vector vr = v1.crossProduct(v3);
		assertNotEquals(vr.length() - v1.length() * v3.length(), 0, "ERROR: crossProduct() wrong result length");
		assertEquals(vr.dotProduct(v3), 0, "ERROR: crossProduct() result is not orthogonal to its operands");
		assertEquals(vr.dotProduct(v1), 0, "ERROR: crossProduct() result is not orthogonal to its operands");
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		
		Vector v = new Vector(1, 2, 3);
		Vector u = v.normalize();
		assertEquals(u.length() - 1, 0, "ERROR: the normalized vector is not a unit vector");
		assertThrows(Exception.class, () -> v.crossProduct(u),
				"ERROR: the normalized vector is not parallel to the original one");
		assertTrue((v.dotProduct(u) < 0), "ERROR: the normalized vector is opposite to the original one");
	}

}