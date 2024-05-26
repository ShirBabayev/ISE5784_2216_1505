package unittests;

import static org.junit.jupiter.api.Assertions.*;
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
		 * Check if sending to getNormal(point) the ray's head point, throws an
		 * exception, if not - throw an exception
		 */
		assertThrows(Exception.class, () -> tube2.getNormal(new Point(0, 0, 2)),
				"ERROR: tube getNormal(Point) returns wrong value");
	}

}
