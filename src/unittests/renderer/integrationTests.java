package unittests.renderer;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import geometries.*;
import primitives.*;
import renderer.Camera;

public class integrationTests {

	
	private final Camera.Builder camera=Camera.getBuilder()
			.setLocation(Point.ZERO).setDirection(new Vector(0,1,0), new Vector(0,0,-1));
    Camera camera1 = camera.setVpSize(3, 3).build();
    Camera camera2 = camera.setVpSize(6, 6).build();
    String wrong="Wrong number of intersections";

    /**
     * An auxiliary method for calculating the intersection points 
     * between the rays coming out of the camera and the geometric body
     * @param g is a geometric body
     * @return list of intersection points
     */
    public List<Point> findIntersections(Geometry g) {
		List<Point> intersections = null;
		boolean firstPoint=false;
		//Go through the pixel matrix
		for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
            	//Calculates the intersection points between each ray that passes through a certain pixel and the geometric body
            	var list=g.findIntersections(camera.build().constructRay(3, 3, i, j));
            	if(list!=null)
            		if (firstPoint==false) {
            			intersections=new <Point>LinkedList();
            			firstPoint=true;
            		}
            		intersections.addAll(list);
            }
		}
		return intersections;
	}

	
	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray) and @link renderer.Camera#constructRay(int, int, int, int)}.
	 */
	@Test
	void testCamera_SphereIntsersections() {
		
		//TC01: Only the middle ray intersects the sphere by 2 points
		assertEquals(2,findIntersections(new Sphere(new Point(0, 0, -3),1)).size(),"TC01 "+wrong);
		
		//TC02: every ray intersects twice
		camera.setLocation(new Point(0,0,0.5)).build();
		assertEquals(18,findIntersections(new Sphere(new Point(0, 0, -2.5),2.5)).size(),"TC02 "+wrong);
		
		//TC03: the middle ray intersects twice and all the other rays intersect one time for each
		assertEquals(10,findIntersections(new Sphere(new Point(0, 0, -2),2)).size(),"TC03 "+wrong);
		
		//TC04: every ray intersects one time
		assertEquals(9,findIntersections(new Sphere(new Point(0, 0, -2),4)).size(),"TC04 "+wrong);
		
		//TC05: the rays goes in the opposite side from the sphere 
		assertNull(findIntersections(new Sphere(new Point(0, 0, 1),0.5)).size(),"TC05 "+wrong);
	}
	
	/**
	 * Test method for {@link geometries.Plane#findIntersections(primitives.Ray) and @link renderer.Camera#constructRay(int, int, int, int)}.
	 */
	@Test
	void testCamera_PlaneIntsersections() {
	}
	
	
	
	/**
	 * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray) and @link renderer.Camera#constructRay(int, int, int, int)}.
	 */
	@Test
	void testCamera_TriangleIntsersections() {
		
		//TC31: only the middle ray intersects the triangle
        camera.setLocation(new Point(0,0,0.5)).build();
        assertEquals(1, findIntersections(new Triangle(new Point(0, 1, -2), new Point(-1,-1,-2), new Point(1,-1,-2))).size(), "TC31 "+wrong);

        //TC32: only two rays intersect the triangle
        camera.setLocation(new Point(0,0,1)).build();
        assertEquals(2, findIntersections(new Triangle(new Point(0,20, -2), new Point(-1,-1,-2), new Point(1,-1,-2))).size(), "TC32 "+wrong);

	}
	
}
