package unittests;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import geometries.Tube;

/**
 * Unit tests for geometries.Tube class
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		
		// ============ Equivalence Partitions Tests ==============
		
		Tube tube1 = new Tube(new Ray(new Vector(0, 1, 0), new Point(1, 0, 0)), 1);
		
		assertEquals(new Vector(-1, 0, 0), tube1.getNormal(new Point(0, 1, 0)),
				"ERROR: tube getNormal(Point) returns wrong value");
		
		// =============== Boundary Values Tests ==================

		/*שולחים כנקודה לפונקציה גט נורמל את הנקודה של ראש קרן*/
		Tube tube2 = new Tube(new Ray(new Vector(0, 0, 1), new Point(0, 0, 2)), 1);
		
		assertThrows(Exception.class,()->tube2.getNormal(new Point(0, 0, 2)),
				"ERROR: tube getNormal(Point) returns wrong valurgfedsavdse");

	}

}
