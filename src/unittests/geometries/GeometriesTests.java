package unittests.geometries;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*;
import primitives.Vector;
import geometries.*;

/**
 * Unit tests for geometries.Geometries class
 * 
 * @author Shir Babayev and Hodaya Avidan
 */
class GeometriesTests {

	/**
	 * first collection of bodies for tests
	 */
	Geometries g1 =new Geometries();
	/**
	 * second collection of bodies for tests
	 */
	Geometries g2 =new Geometries();
		
	/**
	 * {@link geometries.Geometries#findIntersections(primitives.Ray)}
	 */
	@Test
	void testFindIntersections() {
		
		
 		
		 // =============== Boundary Values Tests ==================
		
		//TC01: There are no bodies in the collection(0 points)
		assertNull(g1.findIntersections(new Ray(new Vector(1,1,1), new Point(0,0,0))), "ERROR: The result for a collection with no Geometries is not null");
			
		//TC02: There are intersection points with only one body
		g1.add(new Sphere(new Point(2,2,2),1));
		//There are bodies but no intersection points (0 points)
		assertNull(g1.findIntersections(new Ray(new Vector(1,0,0), new Point(0,0,0))), "ERROR: The intersection returns wrong value");
			
		//TC03: There are intersection points with only one body
		g2.add(new Sphere(new Point(1,0,0),1), new Sphere(new Point(10,0,0),1));
		assertEquals(2,g2.findIntersections(new Ray(new Vector(0,0,1),new Point(1,0,-2))).size(), "ERROR: he intersection with only one body returns wrong value");
		
		//TC04: There are intersections with all the geometries
 		assertEquals(4, (g2.findIntersections(new Ray(new Vector(1,0,0),new Point(-1,0,0))).size()), "ERROR: the intersection with all bodies returns wrong value");
		
		// ============ Equivalence Partitions Tests ==============
 		
		//TC05: intersection with some geometries
 		g2.add(new Sphere(new Point(2,2,2),1));
 		
 		//There are intersection points with some bodies but not all of them
 		assertEquals(4, (g2.findIntersections(new Ray(new Vector(1,0,0),new Point(-1,0,0))).size()), "ERROR: he intersection with 2 bodies returns wrong value");
 		

	
	
	}
	

}
