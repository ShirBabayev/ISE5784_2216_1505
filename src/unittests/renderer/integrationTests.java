package unittests.renderer;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import geometries.*;
import primitives.*;
import primitives.Vector;

import renderer.Camera;

public class integrationTests {

	
	/**
	 * a camera for tests
	 */
	  private final Camera.Builder camera = Camera.getBuilder()
	            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
	            .setVpDistance(1).setVpSize(3, 3);

	  /**
	   * an string message for the tests
	   */
	  private final String wrong="Wrong number of intersections";

	  
	  
    /**
     * An auxiliary method for calculating the intersection points 
     * between the rays coming out of the camera and the geometric body
     * @param g is a geometric body
     * @return counter of intersection points
     */
    public int findIntersections(Geometry g) {
    	 int counter = 0;
    	 List<Point> results;
         for (int i = 0; i < 3; i++) {
             for (int j = 0; j < 3; j++) {
                 results = g.findIntersections(camera.build().constructRay(3, 3, j, i));
                 if (results != null)
                     counter += results.size();
             }
         }
         return counter;
	}

	
	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray) and @link renderer.Camera#constructRay(int, int, int, int)}.
	 */
	@Test
	void testCamera_SphereIntsersections() {

		//TC01: Only the middle ray intersects the sphere by 2 points
		camera.setLocation(Point.ZERO).build();
		assertEquals(2,findIntersections(new Sphere(new Point(0, 0, -3),1)),"TC01 "+wrong);

		//TC02: every ray intersects twice
		camera.setLocation(new Point(0,0,0.5)).build();
		assertEquals(18,findIntersections(new Sphere(new Point(0, 0, -2.5),2.5)),"TC02 "+wrong);

		//TC03: the middle ray intersects twice and all the other rays intersect one time for each
		assertEquals(10,findIntersections(new Sphere(new Point(0, 0, -2),2)),"TC03 "+wrong);

		//TC04: every ray intersects one time
		assertEquals(9,findIntersections(new Sphere(new Point(0, 0, -2),4)),"TC04 "+wrong);

		//TC05: the rays goes in the opposite side from the sphere 
		assertEquals(0,findIntersections(new Sphere(new Point(0, 0, 1),0.5)),"TC05 "+wrong);
	}
	
	/**
	 * Test method for {@link geometries.Plane#findIntersections(primitives.Ray) and @link renderer.Camera#constructRay(int, int, int, int)}.
	 */
	@Test
	void testCamera_PlaneIntsersections() {
		//TC01: First test case
        camera.setLocation(new Point(0,0,1));
        assertEquals(9, findIntersections(new Plane(new Point(0, 0, -1), new Point(1, 0, -1), new Point(0, 1, -1))),"Wrong number of intersections in case 1");

        //TC02: Second test case
        assertEquals(9, findIntersections(new Plane(new Point(0, 0, -2), new Point(-3,0,0), new Point(-3,2,0))), "Wrong number of intersections in case 2");

        //TC03: Third test case
        assertEquals(6, findIntersections(new Plane(new Point(0, 0, -4), new Point(-3,0,0), new Point(-3,2,0))), "Wrong number of intersections in case 3");
  }
	
	
	
	/**
	 * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray) and @link renderer.Camera#constructRay(int, int, int, int)}.
	 */
	@Test
	void testCamera_TriangleIntsersections() {
		
		//TC31: only the middle ray intersects the triangle
        camera.setLocation(new Point(0,0,0.5)).build();
        assertEquals(1, findIntersections(new Triangle(new Point(0, 1, -2), new Point(-1,-1,-2), new Point(1,-1,-2))), "TC31 "+wrong);

        //TC32: only two rays intersect the triangle
        camera.setLocation(new Point(0,0,1)).build();
        assertEquals(2, findIntersections(new Triangle(new Point(0,20, -2), new Point(-1,-1,-2), new Point(1,-1,-2))), "TC32 "+wrong);

	}
	
}
