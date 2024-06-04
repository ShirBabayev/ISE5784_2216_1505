package unittests.geometries;


import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import primitives.*;
import geometries.*;

class GeometriesTests {

	private List<Intersectable> bodies= new LinkedList<Intersectable>();
	Geometries g1 =new Geometries();
	Geometries g2 =new Geometries();

	/**
	 * {@link geometries.Geometries#Geometries()}
	 */
	@Test
	void testGeometries() {
		
	}

	/**
	 * {@link geometries.Geometries#Geometries(geometries.Intersectable...)}
	 */
	@Test
	void testGeometriesIntersectableArray() {
		
	}

	/**
	 * {@link geometries.Geometries#add(geometries.Intersectable...)}
	 */
	@Test
	void testAdd() {
	}
	
	/**
	 * {@link geometries.Geometries#findIntersections(primitives.Ray)}
	 */
	@Test
	void testFindIntersections() {
		//there are no bodies in the collection(0 points)
		assertNull(g1.findIntersections(new Ray(new Vector(1,1,1), new Point(0,0,0))), "ERROR: The result for a collection with no Geometries is not null");
		g1.add(new Sphere(new Point(2,2,2),1));
		//There are bodies but no intersection points (0 points)
		assertNull(g1.findIntersections(new Ray(new Vector(1,0,0), new Point(0,0,0))), "ERROR: The intersection returns wrong value");
		g2.add(new Sphere(new Point(1,0,0),1));
		var l1=List.of(new Point(0,0,0),new Point(2,0,0));
		//There are intersection points with only one body
		assertEquals(l1, g2.findIntersections(new Ray(new Vector(1,0,0),new Point(-1,0,0))), "ERROR: he intersection with only one body returns wrong value");
		//There are intersection points with not all of the bodies
		g2.add(new Sphere(new Point(2,2,2),1));
		assertEquals(l1, g2.findIntersections(new Ray(new Vector(1,0,0),new Point(-1,0,0))), "ERROR: he intersection with only one body returns wrong value");
		


	
	
	}
	

}
