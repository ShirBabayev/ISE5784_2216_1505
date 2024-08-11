package unittests.renderer;

import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import lighting.PointLight;
import primitives.*;
import renderer.*;

//import scene.Scene;
import scene.Scene;

/**
 * Testing Camera Class
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
class DepthOfFieldTest {
	
	//TODO: add java doc
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

		cameraBuilder.setImageWriter(new ImageWriter("no_advanced_depth", 1000, 1000))
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
				.setApertureRadius(2) // Decreased aperture to reduce overall blurriness while still showing depth of
										// field
				.setSqrtGridSize(17) // Increased number of rays for a smoother depth of field effect
				.build().renderImage().writeToImage();
	}


}
