package unittests;

import geometries.Triangle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;

import primitives.Vector;

/**
 * Unit tests for geometries.Triangle class
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
class TriangleTests {

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {

		// ============ Equivalence Partitions Tests ==============

		Point[] pts = { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
		Triangle triangle = new Triangle(pts[0], pts[1], pts[2]);
		double d = Math.sqrt(3) / 3;
		/* TC01: Checks that the function does indeed return a correct result */
		assertEquals(new Vector(d, d, d), triangle.getNormal(new Point(0, 0, 1)),
				"ERROR: Triangle getNormal(Point) returns wrong value");
		Vector result = triangle.getNormal(new Point(0, 0, 1));
		/*TC02: Checks that the normal is indeed perpendicular to the vectors on the triangle*/
		for (int i = 0; i < 3; ++i)
			/* Each time creates a vector from 2 points on the plane and checks that the normal
			  is indeed orthogonal to this vector*/
			assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])),
					"Plane's normal is not orthogonal to one of the edges");

	}

}
