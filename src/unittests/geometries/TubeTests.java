package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import geometries.Tube;

/**
 * Unit tests for geometries.Tube class
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {

		// ============ Equivalence Partitions Tests ==============
		// TC01: Check getNormal validity
		Tube tube1 = new Tube(new Ray(new Vector(0, 1, 0), new Point(1, 0, 0)), 1);
		/*
		 * check whether the normal to the tube returns the wanted result, if not -
		 * throw an exception
		 */
		assertEquals(new Vector(-1, 0, 0), tube1.getNormal(new Point(0, 1, 0)),
				"ERROR: tube getNormal(Point) returns wrong value");

		// =============== Boundary Values Tests ==================

		// TC02: Ensure there is an exception
		Tube tube2 = new Tube(new Ray(new Vector(0, 0, 1), new Point(0, 0, 2)), 1);
		/*
		 * When connecting the point to the head of the ray of the cylinder axis makes a
		 * 90 c angle with the axis
		 */
		assertEquals(new Vector(0, 1, 0), tube2.getNormal(new Point(0, 1, 2)),
				"ERROR: Incorrect value at 90C angle of the radius with the axis");
	}

	/**
	 * Test method for {@link geometries.Tube#findIntersections(Ray ray)}.
	 */
	@Test
	void testFindIntsersections() {

	}

}
