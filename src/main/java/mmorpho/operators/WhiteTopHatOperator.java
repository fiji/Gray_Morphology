package mmorpho.operators;

import ij.process.ImageProcessor;

import java.util.Arrays;

/**
 * The Class WhiteTopHatOperator.
 * 
 * @author Edwin Delgado H.
 */
public class WhiteTopHatOperator {

	/** The pg. */
	private int[][] pg;

	/** The height. */
	int width, height;

	/**
	 * Instantiates a new white top hat operator.
	 * 
	 * @param pg
	 *            the pg
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	public WhiteTopHatOperator(int[][] pg, int width, int height) {
		this.pg = pg;
		this.width = width;
		this.height = height;
	}

	/**
	 * Performs graylevel white top hat operation with arbitrary structural
	 * element se.
	 * 
	 * @param ip
	 *            the ImageProcessor
	 */
	public void whiteTopHat(ImageProcessor ip) {
		int width = ip.getWidth();
		int height = ip.getHeight();
		
		byte[] pixels = (byte[]) ip.getPixels();
		byte[] newpix = new byte[pixels.length];
		
		byte[] originalPixels = Arrays.copyOf(pixels, pixels.length);
		
		OpenOperator openOperator = new OpenOperator(pg, width, height);
		openOperator.open(ip);
		byte[] pixelsOpened = (byte[]) ip.getPixels();
		
		for (int c=0;c<originalPixels.length;c++) {
			
			byte k= (byte)(originalPixels[c] - pixelsOpened[c]);
			if(k < 0){
				k = 0;
			}
			newpix[c]=(byte)(k&0xFF);
			
        }
        
		System.arraycopy(newpix, 0, pixels, 0, pixels.length);

	}

}
