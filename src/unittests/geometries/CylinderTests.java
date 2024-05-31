package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import geometries.*;
import primitives.*;

;

/**
 * Unit tests for geometries.Cylinder class
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
class CylinderTests {

	/**
	 * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		Ray r = new Ray(new Vector(0, 1, 0), new Point(1, 0, 0));
		Cylinder c = new Cylinder(r, 1, 5);
		/**
		 * TC01: point on the side surface
		 */
		// a normal to a point on the side surface of the cylinder
		assertEquals(new Vector(1, 0, 0), c.getNormal(new Point(2, 1, 0)),
				"ERROR: point on the side surface returns a wrong result");
		/**
		 * TC02: point on the bottom base
		 */
		// a normal to a point on the bottom base of the cylinder
		assertEquals(new Vector(0, -1, 0), c.getNormal(new Point(1.5, 0, 0)),
				"ERROR: point on the bottom base returns a wrong result");
		// a normal to a point on the top base of the cylinder
		assertEquals(new Vector(0, 1, 0), c.getNormal(new Point(1.5, 5, 0)),
				"ERROR: point on the top base returns a wrong result");

		// ============ Boundary Tests ==============

		/**
		 * TC03: checks the point on the boundary between the side surface and the
		 * bottom of the cylinder returns correct result.
		 */

		// base bottom
		assertEquals(new Vector(0, -1, 0), c.getNormal(new Point(2, 0, 0)),
				"ERROR: point on the boundary between the side surface and the bottom returns a wrong result");
		// top bottom
		assertEquals(new Vector(0, 1, 0), c.getNormal(new Point(2, 5, 0)),
				"ERROR: point on the boundary between the side surface and the top bottom returns a wrong result");
		/**
		 * TC04: point is same to the center point of the bottom
		 */
		// base bottom
		assertEquals(r.getDirection().scale(-1), c.getNormal(new Point(1, 0, 0)),
				"ERROR: point is center of the bottom returns a wrong result");
		// top bottom
		assertEquals(r.getDirection(), c.getNormal(r.getHead().add(r.getDirection().scale(5))),
				"ERROR: point is center of the top bottom returns a wrong result");

	}

}