/**
 * 
 */
package unittests.renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import renderer.ImageWriter;

/**
 * Unit tests for Image Writer Test
 * 
 * @author Shir Babayev and Hodaya Avidan
 */
class ImageWriterTest {
	/**
	 * The number of pixels on the X-axis
	 */
	private final int NX = 801;
	/**
	 * The number of pixels on the Y-axis
	 */
	private final int NY = 501;

	/**
	 * an imageWriter board for the test
	 */
	private final ImageWriter imageWriter = new ImageWriter("image1", NX, NY);

	/**
	 * Test method for {@link renderer.ImageWriter#writeToImage()}
	 */
	@Test
	void writeToImage() {
		Color color1 = new Color(java.awt.Color.RED);
		Color color2 = new Color(java.awt.Color.CYAN);

		// Loop through all columns of the image
		for (int i = 0; i < NX; i++) {// i<800
			// Loop through all columns of the image
			for (int j = 0; j < NY; j++)
				imageWriter.writePixel(i, j, i % 50 == 0 || j % 50 == 0 ? color1 : color2);
		}
		imageWriter.writeToImage();// write the image to the file
	}

}
