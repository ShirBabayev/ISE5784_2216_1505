package primitives;

/**
 * A class that represents the material from which the geometric bodies are made
 * 
 * @author Hodaya Avidan and Shir Babayev
 */
public class Material {

	/**
	 * Diffusion attenuation factor
	 */
	public Double3 kD = Double3.ZERO;
	/**
	 * Specular attenuation factor
	 */
	public Double3 kS = Double3.ZERO;
	/**
	 * the amount of shininess that the material return
	 */
	public int nShininess = 0;

	/**
	 * setter function for the diffusion attenuation factor
	 * 
	 * @param kD is diffusion attenuation factor
	 * @return the updated material
	 */
	public Material setKd(Double3 kD) {
		this.kD = kD;
		return this;
	}

	/**
	 * setter function for the diffusion attenuation factor
	 * 
	 * @param kD is diffusion attenuation factor
	 * @return the updated material
	 */
	public Material setKd(double kD) {
		this.kD = new Double3(kD);
		return this;
	}

	/**
	 * setter function for the specular attenuation factor
	 * 
	 * @param kS is specular attenuation factor
	 * @return the updated material
	 */
	public Material setKs(Double3 kS) {
		this.kS = kS;
		return this;
	}

	/**
	 * setter function for the specular attenuation factor
	 * 
	 * @param kS is specular attenuation factor
	 * @return the updated material
	 */
	public Material setKs(double kS) {
		this.kS = new Double3(kS);
		return this;
	}

	/**
	 * setter function for the shininess of the material
	 * 
	 * @param nShininess is the shininess that the material returns
	 * @return the updates material
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}

}
