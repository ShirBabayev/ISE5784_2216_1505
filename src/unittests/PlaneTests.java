package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;

import geometries.Plane;

import test.Plane;


/**
 * Unit tests for geometries.Plane class
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
class PlaneTests {
	
	
	Point[] pts = { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
	Plane plane1= new Plane(pts[0],pts[1],pts[2]);
	double d=Math.sqrt(3)/3;


	/**
	 * Test method for {@link geometries.Plane#getNormal()}.
	 */
	@Test
	void testGetNormal() {
		
		assertDoesNotThrow(()->plane1.getNormal(),"ERROR: normal of the plane does not work");
		assertEquals(new Vector(d,d,d), plane1.getNormal(), "ERROR: normal of the plane does not work correctly");
		Vector result = plane1.getNormal();
		for (int i = 0; i < 3; ++i)
			assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])),
					"Plane's normal is not orthogonal to one of the edges");
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormalPoint() {
		assertDoesNotThrow(()->plane1.getNormal(pts[0]),"ERROR: normal of the plane does not work");
		assertEquals(new Vector(d,d,d), plane1.getNormal(pts[0]), "ERROR: normal of the plane does not work correctly");
		Vector result = plane1.getNormal(new Point(0, 0, 1));
		for (int i = 0; i < 3; ++i)
			assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])),
					"Plane's normal is not orthogonal to one of the edges");
	}

}
