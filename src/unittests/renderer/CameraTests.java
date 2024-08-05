package unittests.renderer;

import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

import geometries.Cylinder;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
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
	
	  @Test void testConstructRay() { final String badRay = "Bad ray";
	  
	  // ============ Equivalence Partitions Tests ============== 
	  // EP01: 4X4 Inside (1,1) 
	  Camera camera1 = cameraBuilder.setVpSize(8, 8).build();
	  assertEquals(new Ray(Point.ZERO, new Vector(1, -1, -10)),
	  camera1.constructRay(4, 4, 1, 1), badRay);
	  
	  // =============== Boundary Values Tests ================== 
	  // BV01: 4X4 Corner (0,0) 
	  assertEquals(new Ray(Point.ZERO, new Vector(3, -3, -10)),
	  camera1.constructRay(4, 4, 0, 0), badRay);
	  
	  // BV02: 4X4 Side (0,1) 
	  assertEquals(new Ray(Point.ZERO, new Vector(1, -3,
	  -10)), camera1.constructRay(4, 4, 1, 0), badRay);
	  
	  // BV03: 3X3 Center (1,1) 
	  Camera camera2 = cameraBuilder.setVpSize(6,
	  6).build(); assertEquals(new Ray(Point.ZERO, new Vector(0, 0, -10)),
	  camera2.constructRay(3, 3, 1, 1), badRay);
	  
	  // BV04: 3X3 Center of Upper Side (0,1) 
	  assertEquals(new Ray(Point.ZERO, new
	  Vector(0, -2, -10)), camera2.constructRay(3, 3, 1, 0), badRay);
	  
	  // BV05: 3X3 Center of Left Side (1,0) 
	  assertEquals(new Ray(Point.ZERO, new
	  Vector(2, 0, -10)), camera2.constructRay(3, 3, 0, 1), badRay);
	  
	  // BV06: 3X3 Corner (0,0) 
	  assertEquals(new Ray(Point.ZERO, new Vector(2, -2,
	  -10)), camera2.constructRay(3, 3, 0, 0), badRay);
	  
	  }
	 
	/*
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

	
	@Test
	public void createTestScene() {
	    Scene scene = new Scene("test_scene");
	    // Light sources
	    scene.lights.add(new SpotLight(new Color(150, 69, 0), new Point(0, 6, -200), new Vector(1,1,1)));
	    //scene.lights.add(new SpotLight(new Color(150, 69, 0), new Point(0, 6, -270), new Vector(0,0,-2)));

	    Material mat = new Material().setKd(0.3).setKr(0.4).setKs(0.7).setShininess(1);
	    Material mat2 = new Material().setKd(0.3).setKs(0.7).setShininess(2).setKt(0.5);
	    Material mat3 = new Material().setKd(0.3).setKs(0.7).setKr(0.8).setShininess(500).setKt(0.5);
	    Material mat4 = new Material().setKd(0.3).setKr(0.4).setKs(0.7).setShininess(1);



	    

	    Camera.Builder cameraBuilder = Camera.getBuilder()
	            .setRayTracer(new SimpleRayTracer(scene))
	            .setImageWriter(new ImageWriter("test_scene", 600, 600))
	            .setLocation(new Point(0, 10, 50))
	            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
	            .setVpDistance(100)
	            .setVpSize(50, 50);
	            //.setFocalDistance(40)
	            //.setApertureRadius(2)
	            //.setSqrtGridSize(8);

	    scene.geometries.add(
	    		//the sun
	    		new Sphere(new Point(0, 6, -300), 30)
	            .setEmission(new Color(255, 120, 71))
	            .setMaterial(mat2),

	            
	            //the sea
	            new Plane(new Point(12, 0, 0),new Point(-6, -0.09, 0), new Point(0, 0, -3.06))
	            .setEmission(new Color(120,90,190)) 
	            .setMaterial(mat4),
	            
	            
	            //the sky
	            new Plane(new Point(9.77, 0, -450), new Point(-10.91, 0, -450), new Point(0, -2.95, -450))
	            .setEmission(new Color(100,100,235)) 
	            .setMaterial(mat),
	            
	            //something to cover the sky - make it look more real
	            new Plane(new Point(9.77, 0, -450), new Point(-10.91, 0, -450), new Point(0, -2.95, -450))
	            .setEmission(new Color(255, 94, 77)) 
	            .setMaterial(mat3));
	    
	    Random rand = new Random();
	    //clouds - right side
        for (int k = 0; k < 200; k++) {
            double x1 = -15 + 100 * rand.nextDouble();
            double y1 = 11 + 3 * rand.nextDouble();
            double z1 = -300 - 10 * rand.nextDouble();
            double radius = 0.01 + rand.nextDouble();
	        scene.geometries.add(
	            new Sphere(new Point(x1, y1, z1), radius)
	            .setEmission(new Color(255, 220, 220)) // Cloud color
	            .setMaterial(mat)
	        );
        }
      //clouds - left side
        for (int k = 0; k < 200; k++) {
            double x1 = -85 + 100 * rand.nextDouble();
            double y1 = 22 + 4 * rand.nextDouble();
            double z1 = -300 - 10 * rand.nextDouble();
            double radius1 = 0.01 + rand.nextDouble();
	        scene.geometries.add(
	            new Sphere(new Point(x1, y1, z1), radius1)
	            .setEmission(new Color(255, 220, 220)) // Cloud color
	            .setMaterial(mat2)
	        );
        }
        
        //stars
        for (int i = 0; i < 80; i++) {
	        double x = -100 + 200 * rand.nextDouble();
	        double y = 35 + 80 * rand.nextDouble();
	        double z = -350 + 50 * rand.nextDouble();
	        double radius = 0.01 + 0.5*rand.nextDouble();
	        scene.geometries.add(
	            new Sphere(new Point(x, y, z), radius)
	            .setEmission(new Color(0, 5, 255)) // Cloud color
	            .setMaterial(mat3));
        }
        /*// Adding a simple boat
        // Boat base
        scene.geometries.add(
            new Polygon(
                new Point(2, 0.5, -3),
                new Point(-2, 0.5, -3),
                new Point(-2, 0.5, -5),
                new Point(2, 0.5, -5))
            .setEmission(new Color(139, 69, 19)) // Brown color for the boat base
            .setMaterial(mat2)
        );

        // Boat mast
        scene.geometries.add(
            new Cylinder(
                new Ray(new Point(0, 0.5, -4), new Vector(0, 1, 0)), 0.1, 2)
            .setEmission(new Color(160, 82, 45)) // Darker brown for the mast
            .setMaterial(mat3)
        );

        // Boat sail
        scene.geometries.add(
            new Triangle(
                new Point(0, 2.5, -4),
                new Point(-1, 0.5, -4),
                new Point(1, 0.5, -4))
            .setEmission(new Color(255, 255, 255)) // White color for the sail
            .setMaterial(mat4)
        );
	    */
        // Boat body (hull)
        scene.geometries.add(
            new Polygon(
                new Point(-12, 0, -12),
                new Point(-8, 0, -12),
                new Point(-7, 1, -12),
                new Point(-13, 1, -12))
            .setEmission(new Color(139, 69, 19)) // Brown color for the hull
            .setMaterial(mat2)
        );

        scene.geometries.add(
            new Polygon(
                new Point(-12, 0, -10),
                new Point(-8, 0, -10),
                new Point(-7, 1, -10),
                new Point(-13, 1, -10))
            .setEmission(new Color(139, 69, 19)) // Brown color for the hull
            .setMaterial(mat2)
        );

        scene.geometries.add(
            new Polygon(
                new Point(-13, 1, -12),
                new Point(-7, 1, -12),
                new Point(-7, 1, -10),
                new Point(-13, 1, -10))
            .setEmission(new Color(139, 69, 19)) // Brown color for the hull
            .setMaterial(mat2)
        );

        // Boat front (bow)
        scene.geometries.add(
            new Polygon(
                new Point(-12, 0, -12),
                new Point(-8, 0, -12),
                new Point(-10, 2, -13))
            .setEmission(new Color(160, 82, 45)) // Darker brown for the bow
            .setMaterial(mat2)
        );

        // Boat back (stern)
        scene.geometries.add(
            new Polygon(
                new Point(-12, 0, -10),
                new Point(-8, 0, -10),
                new Point(-10, 1.5, -9))
            .setEmission(new Color(160, 82, 45)) // Darker brown for the stern
            .setMaterial(mat2)
        );

        // Boat deck
        scene.geometries.add(
            new Polygon(
                new Point(-12, 1, -12),
                new Point(-8, 1, -12),
                new Point(-8, 1, -10),
                new Point(-12, 1, -10))
            .setEmission(new Color(139, 69, 19)) // Brown color for the deck
            .setMaterial(mat2)
        );

        // Boat sides
        scene.geometries.add(
            new Polygon(
                new Point(-13, 0, -12),
                new Point(-12, 0, -12),
                new Point(-12, 1, -12),
                new Point(-13, 1, -12))
            .setEmission(new Color(160, 82, 45)) // Darker brown for the side
            .setMaterial(mat2)
        );

        scene.geometries.add(
            new Polygon(
                new Point(-8, 0, -12),
                new Point(-7, 0, -12),
                new Point(-7, 1, -12),
                new Point(-8, 1, -12))
            .setEmission(new Color(160, 82, 45)) // Darker brown for the side
            .setMaterial(mat2)
        );

        // Boat mast
        scene.geometries.add(
            new Cylinder(
                new Ray(new Point(-10, 1, -11), new Vector(0, 1, 0)), 0.1, 5)
            .setEmission(new Color(160, 82, 45)) // Darker brown for the mast
            .setMaterial(mat3)
        );

        // Boat sails
        scene.geometries.add(
            new Triangle(
                new Point(-10, 6, -11),
                new Point(-11, 1, -11),
                new Point(-9, 1, -11))
            .setEmission(new Color(255, 255, 255)) // White color for the sail
            .setMaterial(mat4)
        );

        scene.geometries.add(
            new Triangle(
                new Point(-10, 4, -11),
                new Point(-10.5, 1, -11),
                new Point(-9.5, 1, -11))
            .setEmission(new Color(255, 255, 255)) // White color for the smaller sail
            .setMaterial(mat4)
        );

        // Boat railing
        for (int i = 0; i < 5; i++) {
            scene.geometries.add(
                new Cylinder(
                    new Ray(new Point(-12.8 + i * 1.4, 1, -11), new Vector(0, 1, 0)), 0.05, 0.5)
                .setEmission(new Color(160, 82, 45)) // Darker brown for the railing posts
                .setMaterial(mat3)
            );
        }

        scene.geometries.add(
            new Polygon(
                new Point(-12.8, 1.5, -11),
                new Point(-7.2, 1.5, -11),
                new Point(-7.2, 1.4, -11),
                new Point(-12.8, 1.4, -11))
            .setEmission(new Color(160, 82, 45)) // Darker brown for the railing
            .setMaterial(mat3)
        );
        
	    Camera camera = cameraBuilder.build();
	    camera.renderImage();
	    camera.writeToImage();
	}

}
