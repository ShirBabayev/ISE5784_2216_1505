package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;

import primitives.Point;
import primitives.Vector;


/**
 * Unit tests for geometries.Sphere class
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		Sphere sphere=new Sphere(new Point(1,1,1),2);
		// ============ Equivalence Partitions Tests ==============
		assertEquals(new Vector(1,0,0), sphere.getNormal(new Point(3,1,1)), "ERROR: sphere getNormal(Point) returns wrong value");
		// =============== Boundary Values Tests ==================

			
	}

}
