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
		assertEquals(new Vector(1, 0, 0), c.getNormal(new Point(2, 1, 0)), "Cylinder.getNormal() result is wrong 1");
		// a normal to a point on the side surface of the cylinder
		assertEquals(new Vector(0, -1, 0), c.getNormal(new Point(1.5, 0, 0)), "Cylinder.getNormal() result is wrong 2");
		// a normal to a point on the bottom base of the cylinder
		assertEquals(new Vector(0, 1, 0), c.getNormal(new Point(1.5, 5, 0)), "Cylinder.getNormal() result is wrong 3");
		// a normal to a point on the top base of the cylinder

		// ============ Boundary Tests ==============
		assertEquals(new Vector(0, -1, 0), c.getNormal(new Point(2, 0, 0)), "Cylinder.getNormal() result is wrong 4");
		// a normal to a point on the boundary between the side surface and the bottom
		// base of the cylinder.
		assertEquals(new Vector(0, 1, 0), c.getNormal(new Point(2, 5, 0)), "Cylinder.getNormal() result is wrong 5");
		// a normal to a point on the boundary between the side surface and the top base
		// of the cylinder.
	}

}