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
	
	Point p1 = new Point(1, 2, 3);
	Point p2 = new Point(2, 4, 6);
	Point p3 = new Point(3, 5, 6);
	Plane plane1= new Plane(p1,p2,p3);
	Plane plane2= new Plane(p2,p3,p1);

	/**
	 * Test method for {@link geometries.Plane#getNormal()}.
	 */
	@Test
	void testGetNormal() {
		assertEquals(plane1.getNormal(), new Vector(-2/Math.sqrt(5),1/Math.sqrt(5),0), "ERROR: normal of the plane does not work correctly");
		assertEquals(plane1.getNormal(), new Vector(-2/Math.sqrt(5),1/Math.sqrt(5),0), "ERROR: normal of the plane does not work correctly");

		
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormalPoint() {
	}

}
