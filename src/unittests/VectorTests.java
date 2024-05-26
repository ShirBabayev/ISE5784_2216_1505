package unittests;

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

	
	private static final Point p1 = new Point(1, 2, 3);
	private static final Point p2 = new Point(2, 4, 6);
	private static final Point p3 = new Point(2, 4, 5);

	private static final Vector v1 = new Vector(1, 2, 3);
	private static final Vector v1Opposite = new Vector(-1, -2, -3);
	private static final Vector v2 = new Vector(-2, -4, -6);
	private static final Vector v3 = new Vector(0, 3, -2);
	private static final Vector v4 = new Vector(1, 2, 2);

	/** Test method for {@link primitives.Vector#Vector(primitives.Point...)}. */
	@Test
	void testConstructor() {
		
		// =============== Boundary Values Tests ==================
		
		/*TC01: Checks if an exception is thrown when creating vector 0 with the parameters (0,0,0)*/
		assertThrows(Exception.class, () -> new Vector(0, 0, 0), "ERROR: zero vector does not throw an exception");
		/*TC02: Checks if an exception of the correct type is thrown when creating vector 0 with the parameters (0,0,0)*/
		assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0),
				"ERROR: zero vector does not throw a correct exception");
		/*TC03: Checks if an exception is thrown when creating vector 0 with the parameters (Double3)*/
		assertThrows(Exception.class, () -> new Vector(Double3.ZERO), "ERROR: zero vector does not throw an exception");
		/*TC04: Checks if an exception of the correct type is thrown when creating vector 0 with the parameters (Double3)*/
		assertThrows(IllegalArgumentException.class, () -> new Vector(Double3.ZERO),
				"ERROR: zero vector does not throw an exception");
	}
	
	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		
		// ============ Equivalence Partitions Tests ==============
		/*TC05 Checks if the squared length of the vector did return a correct result*/
		assertEquals(v4.lengthSquared(), 9, "ERROR: lengthSquared() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		
		// ============ Equivalence Partitions Tests ==============
		/*TC06 Checks if the length of the vector did return a correct result*/
		assertEquals(v4.length(), 3, "ERROR: length() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		
		// =============== Boundary Values Tests ==================
		/*TC07: Checks whether adding the opposite of itself to a vector throws an error. An error should be thrown because a zero vector is created.*/
		assertThrows(Exception.class, ()->v1.add(v1Opposite), "ERROR: result of zero vector does not throw an exception");
		/*TC08: Checks whether adding the opposite of itself to a vector throws an correct error. An error should be thrown because a zero vector is created.*/
		assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite),
				"ERROR: result of zero vector does not throw a correct exception");
		/*TC09: Checks if subtracting a vector from itself throws an error because a zero vector is created.*/
		assertThrows(Exception.class, ()->v1.subtract(v1), "ERROR: Vector - itself does not throw an exception");
		/*TC10: Checks if subtracting a vector from itself throws an correct error because a zero vector is created.*/
		assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1),
				"ERROR: Vector + itself throws wrong exception");

		// ============ Equivalence Partitions Tests ==============
		/*TC11: Checks if adding 2 vectors returns a correct result*/
		assertEquals(v1.add(v2), v1Opposite, "ERROR: Vector + Vector does not work correctly");
		/*TC12: Checks if subtraction 2 vectors returns a correct result*/
		assertEquals(v1.subtract(v2), new Vector(3, 6, 9), "ERROR: Vector - Vector does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		
		// ============ Equivalence Partitions Tests ==============
		
		/*TC13: Checks that multiplying a vector by a scalar returns a correct result*/
		assertEquals(v1.scale(-1), v1Opposite, "ERROR: Vector*scalar does not work correctly");
		
		// =============== Boundary Values Tests ==================

		/*TC14: Checks that multiplying a vector by a scalar 0 throw an exception because the result is a zero vector*/
		assertThrows(Exception.class,()->v1.scale(0), "ERROR: zero vector does not throw an exception");
		/*TC15: Checks that multiplying a vector by a scalar 0 throw a correct exception because the result is a zero vector*/
		assertThrows(IllegalArgumentException.class,()->v1.scale(0), "ERROR: zero vector does not throw a correct exception");

	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		
		// =============== Boundary Values Tests ==================

		/*TC16: Checks if a scalar product of perpendicular vectors returns 0*/
		assertEquals(v1.dotProduct(v3), 0, "ERROR: dotProduct() for orthogonal vectors is not zero");
		
		// ============ Equivalence Partitions Tests ==============

		/*TC17: Checks if a scalar product of vectors returns a correct result*/
		assertEquals(v1.dotProduct(v2) + 28, 0, "ERROR: dotProduct() returns wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		
		// =============== Boundary Values Tests ==================

		/*TC18: Checks if an error is thrown when doing vector multiplication between parallel vectors*/
		assertThrows(Exception.class, () -> v1.crossProduct(v2),
				"ERROR: crossProduct() for parallel vectors does not throw an exception");
		Vector vr = v1.crossProduct(v3);
		/*TC19: Checks that the vector product takes into account the right angle between the perpendicular vectors*/
		assertNotEquals(vr.length() - v1.length() * v3.length(), 0, "ERROR: crossProduct() returns wrong result length");
		/*TC20: Checks whether a scalar product of 2 perpendicular vectors returns 0*/
		assertEquals(vr.dotProduct(v3), 0, "ERROR: crossProduct() result is not orthogonal to its operands");
		/*TC21: Checks whether a scalar product of 2 perpendicular vectors returns 0*/
		assertEquals(vr.dotProduct(v1), 0, "ERROR: crossProduct() result is not orthogonal to its operands");
		
		// ============ Equivalence Partitions Tests ==============

		/*TC22: Checks that vector multiplication returns a correct result*/
		assertEquals(v3.crossProduct(v4), new Vector(10,-2,-3), "ERROR: crossProduct() result is not orthogonal to its operands");

	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		
		// ============ Equivalence Partitions Tests ==============

		Vector v = new Vector(1, 2, 3);
		Vector u = v.normalize();
		/*TC23: Checks that the function does return a vector of unit length*/
		assertEquals(u.length() - 1, 0, "ERROR: the normalized vector is not a unit vector");
		/*TC24: Checks that a vector returns in the right direction and not the reverse of the vector itself*/
		assertFalse((v.dotProduct(u) < 0), "ERROR: the normalized vector is opposite to the original one");
		/*TC25: Checks that an error is thrown when doing a vector multiplication of 2 parallel vectors because the result is the zero vector*/
		assertThrows(Exception.class, () -> v.crossProduct(u), "ERROR: the normalized vector is not parallel to the original one");
	}

}