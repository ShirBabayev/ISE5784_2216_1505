package unittests.renderer;

import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
//import scene.Scene;
import scene.Scene;

/**
 * Testing Camera Class
 * 
 * @author Dan
 */
class CameraTests {
	/** Camera builder for the tests */
	private final Camera.Builder cameraBuilder = Camera.getBuilder()
			.setRayTracer(new SimpleRayTracer(new Scene("Test"))).setImageWriter(new ImageWriter("Test", 1, 1))
			.setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0)).setVpDistance(10);

	/**
	 * Test method for {@link renderer.Camera#constructRay(int, int, int, int)}.
	 */
	@Test
	void testConstructRay() {
		final String badRay = "Bad ray";

		// ============ Equivalence Partitions Tests ==============
		// EP01: 4X4 Inside (1,1)
		Camera camera1 = cameraBuilder.setVpSize(8, 8).build();
		assertEquals(new Ray(Point.ZERO, new Vector(1, -1, -10)), camera1.constructRay(4, 4, 1, 1), badRay);

		// =============== Boundary Values Tests ==================
		// BV01: 4X4 Corner (0,0)
		assertEquals(new Ray(Point.ZERO, new Vector(3, -3, -10)), camera1.constructRay(4, 4, 0, 0), badRay);

		// BV02: 4X4 Side (0,1)
		assertEquals(new Ray(Point.ZERO, new Vector(1, -3, -10)), camera1.constructRay(4, 4, 1, 0), badRay);

		// BV03: 3X3 Center (1,1)
		Camera camera2 = cameraBuilder.setVpSize(6, 6).build();
		assertEquals(new Ray(Point.ZERO, new Vector(0, 0, -10)), camera2.constructRay(3, 3, 1, 1), badRay);

		// BV04: 3X3 Center of Upper Side (0,1)
		assertEquals(new Ray(Point.ZERO, new Vector(0, -2, -10)), camera2.constructRay(3, 3, 1, 0), badRay);

		// BV05: 3X3 Center of Left Side (1,0)
		assertEquals(new Ray(Point.ZERO, new Vector(2, 0, -10)), camera2.constructRay(3, 3, 0, 1), badRay);

		// BV06: 3X3 Corner (0,0)
		assertEquals(new Ray(Point.ZERO, new Vector(2, -2, -10)), camera2.constructRay(3, 3, 0, 0), badRay);

	}

	/**
	 * Test for depth of field effect
	 */
	@Test
	public void advancedDepthOfFieldTest() {
		Scene scene = new Scene("advanced depth of field test");

		Material mat = new Material().setKd(0.3).setKr(0.4).setKs(0.7).setShininess(500);

		scene.lights.add(new PointLight(new Color(150, 150, 150), new Point(20, 20, 20))); // Increased light intensity
		scene.geometries.add(new Sphere(new Point(10, 15, -80), 5).setEmission(new Color(RED)).setMaterial(mat),
				new Sphere(new Point(5, 10, -40), 5).setEmission(new Color(BLUE)).setMaterial(mat),
				new Sphere(new Point(0, 5, 0), 5).setEmission(new Color(GREEN)).setMaterial(mat),
				new Sphere(new Point(-5, 0, 40), 5).setEmission(new Color(255, 105, 180)).setMaterial(mat),
				new Sphere(new Point(-10, -5, 80), 5).setEmission(new Color(255, 255, 0)).setMaterial(mat));

		Camera.Builder cameraBuilder = Camera.getBuilder();

		cameraBuilder.setImageWriter(new ImageWriter("no_advanced_depth", 600, 600))
				.setRayTracer(new SimpleRayTracer(scene)).setVpDistance(150) // Adjusted for a closer view of the scene
				.setVpSize(40, 40) // Maintain size for consistency
				// Moved closer to the scene for more field
				.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0)).setLocation(new Point(-5, 0, 200)).build()
				.renderImage().writeToImage();

		cameraBuilder.setImageWriter(new ImageWriter("advanced_depth", 600, 600)).setFocalDistance(160) // Set the focal
																										// plane
																										// distance to
																										// where one
																										// sphere should
																										// be in focus
				.setApertureRadius(5) // Decreased aperture to reduce overall blurriness while still showing depth of
										// field
				.setSqrtGridSize(8) // Increased number of rays for a smoother depth of field effect
				.build().renderImage().writeToImage();
	}

}
