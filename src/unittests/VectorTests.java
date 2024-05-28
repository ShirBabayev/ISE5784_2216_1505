package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Double3;
import primitives.Vector;

/**
 * Unit tests for primitives.Vector class
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class VectorTests {
	/** accuracy for the numeric test results */
	private static final double DELTA = 0.00001;
	/** a vector for the tests */
	private static final Vector v1 = new Vector(1, 2, 3);
	/** a vector for the tests */
	private static final Vector v1Opposite = new Vector(-1, -2, -3);
	/** a vector for the tests */
	private static final Vector v2 = new Vector(-2, -4, -6);
	/** a vector for the tests */
	private static final Vector v3 = new Vector(0, 3, -2);
	/** a vector for the tests */
	private static final Vector v4 = new Vector(1, 2, 2);

	/** Test method for {@link primitives.Vector}. */
	@Test
	void testConstructors() {

		// =============== Boundary Values Tests ==================

		/*
		 * TC01: Checks if an exception is thrown when creating vector 0 with the
		 * parameters (0,0,0)
		 */
		assertThrows(Exception.class, () -> new Vector(0, 0, 0), "ERROR: zero vector does not throw an exception");
		/*
		 * TC02: Checks if an exception of the correct type is thrown when creating
		 * vector 0 with the parameters (0,0,0)
		 */
		assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0),
				"ERROR: zero vector does not throw a correct exception");
		/*
		 * TC03: Checks if an exception is thrown when creating vector 0 with the
		 * parameters (Double3)
		 */
		assertThrows(Exception.class, () -> new Vector(Double3.ZERO), "ERROR: zero vector does not throw an exception");
		/*
		 * TC04: Checks if an exception of the correct type is thrown when creating
		 * vector 0 with the parameters (Double3)
		 */
		assertThrows(IllegalArgumentException.class, () -> new Vector(Double3.ZERO),
				"ERROR: zero vector does not throw an exception");
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {

		// ============ Equivalence Partitions Tests ==============
		/*
		 * TC05 Checks if the squared length of the vector did return a correct result
		 */
		assertEquals(9, v4.lengthSquared(), DELTA, "ERROR: lengthSquared() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {

		// ============ Equivalence Partitions Tests ==============
		/* TC06 Checks if the length of the vector did return a correct result */
		assertEquals(3, v4.length(), DELTA, "ERROR: length() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {

		// =============== Boundary Values Tests ==================
		/*
		 * TC07: Checks whether adding the opposite of itself to a vector throws an
		 * error. An error should be thrown because a zero vector is created.
		 */
		assertThrows(Exception.class, () -> v1.add(v1Opposite),
				"ERROR: result of zero vector does not throw an exception");
		/*
		 * TC08: Checks whether adding the opposite of itself to a vector throws an
		 * correct error. An error should be thrown because a zero vector is created.
		 */
		assertThrows(IllegalArgumentException.class, () -> v1.add(v1Opposite),
				"ERROR: result of zero vector does not throw a correct exception");
		/*
		 * TC09: Checks if subtracting a vector from itself throws an error because a
		 * zero vector is created.
		 */
		assertThrows(Exception.class, () -> v1.subtract(v1), "ERROR: Vector - itself does not throw an exception");
		/*
		 * TC10: Checks if subtracting a vector from itself throws an correct error
		 * because a zero vector is created.
		 */
		assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1),
				"ERROR: Vector + itself throws wrong exception");

		// ============ Equivalence Partitions Tests ==============
		/* TC11: Checks if adding 2 vectors returns a correct result */
		assertEquals(v1Opposite, v1.add(v2), "ERROR: Vector + Vector does not work correctly");
		/* TC12: Checks if subtraction 2 vectors returns a correct result */
		assertEquals(new Vector(3, 6, 9), v1.subtract(v2), "ERROR: Vector - Vector does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {

		// ============ Equivalence Partitions Tests ==============

		/*
		 * TC13: Checks that multiplying a vector by a scalar returns a correct result
		 */
		assertEquals(v1Opposite, v1.scale(-1), "ERROR: Vector*scalar does not work correctly");

		// =============== Boundary Values Tests ==================

		/*
		 * TC14: Checks that multiplying a vector by a scalar 0 throw an exception
		 * because the result is a zero vector
		 */
		assertThrows(Exception.class, () -> v1.scale(0), "ERROR: zero vector does not throw an exception");
		/*
		 * TC15: Checks that multiplying a vector by a scalar 0 throw a correct
		 * exception because the result is a zero vector
		 */
		assertThrows(IllegalArgumentException.class, () -> v1.scale(0),
				"ERROR: zero vector does not throw a correct exception");

	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {

		// =============== Boundary Values Tests ==================

		/* TC16: Checks if a scalar product of perpendicular vectors returns 0 */
		assertEquals(0, v1.dotProduct(v3), DELTA, "ERROR: dotProduct() for orthogonal vectors is not zero");

		// ============ Equivalence Partitions Tests ==============

		/* TC17: Checks if a scalar product of vectors returns a correct result */
		assertEquals(-28, v1.dotProduct(v2), DELTA, "ERROR: dotProduct() returns wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {

		// =============== Boundary Values Tests ==================

		/*
		 * TC18: Checks if an error is thrown when doing vector multiplication between
		 * parallel vectors
		 */
		assertThrows(Exception.class, () -> v1.crossProduct(v2),
				"ERROR: crossProduct() for parallel vectors does not throw an exception");
		Vector vr = v1.crossProduct(v3);
		/*
		 * TC19: Checks that the vector product takes into account the 90C angle
		 * between the orthogonal vectors
		 */
		assertEquals(vr.length(), v1.length() * v3.length(), DELTA,
				"ERROR: crossProduct() returns wrong result length");
		/* TC20: Checks whether a dot product of 2 orthogonal vectors returns 0 */
		assertEquals(0, vr.dotProduct(v3), DELTA, "ERROR: crossProduct() result is not orthogonal to its operands");
		/* TC21: Checks whether a dot product of 2 orthogonal vectors returns 0 */
		assertEquals(0, vr.dotProduct(v1), DELTA, "ERROR: crossProduct() result is not orthogonal to its operands");

		// ============ Equivalence Partitions Tests ==============

		/* TC22: Checks that cross product returns a correct result */
		assertEquals(new Vector(10, -2, -3), v3.crossProduct(v4),
				"ERROR: crossProduct() result is not orthogonal to its operands");// returns wrong result

	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {

		// ============ Equivalence Partitions Tests ==============

		Vector v = new Vector(1, 2, 3);
		Vector u = v.normalize();
		/* TC23: Checks that the function does return a vector of unit length */
		assertEquals(1, u.length(), DELTA, "ERROR: the normalized vector is not a unit vector");
		/*
		 * TC24: Checks that a vector returns in the right direction and not the reverse
		 * of the vector itself
		 */
		assertFalse((v.dotProduct(u) < 0), "ERROR: the normalized vector is opposite to the original one");

		// =============== Boundary Values Tests ==================

		/*
		 * TC25: Checks that an error is thrown when doing a crossProduct of 2 parallel
		 * vectors because the result is the zero vector
		 */
		assertThrows(Exception.class, () -> v.crossProduct(u),
				"ERROR: the normalized vector is not parallel to the original one");
	}

}