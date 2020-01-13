package mmorpho.operators;

import ij.process.ImageProcessor;

import java.util.Arrays;

/**
 * The Class WhiteTopHatOperator.
 * 
 * @author Edwin Delgado H.
 */
public class BlackTopHatOperator {

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
	public BlackTopHatOperator(int[][] pg, int width, int height) {
		this.pg = pg;
		this.width = width;
		this.height = height;
	}

	/**
	 * Performs graylevel black top hat operation with arbitrary structural
	 * element se.
	 * 
	 * @param ip
	 *            the ImageProcessor
	 */
	public void blackTopHat(ImageProcessor ip) {
		int width = ip.getWidth();
		int height = ip.getHeight();
		
		byte[] pixels = (byte[]) ip.getPixels();
		byte[] newpix = new byte[pixels.length];
		
		byte[] originalPixels = Arrays.copyOf(pixels, pixels.length);
		
		CloseOperator closeOperator = new CloseOperator(pg, width, height);
		closeOperator.close(ip);
		byte[] pixelsClosed = (byte[]) ip.getPixels();
		
		for (int c=0;c<originalPixels.length;c++) {
			
			byte k= (byte)(pixelsClosed[c] - originalPixels[c]);
			if(k < 0){
				k = 0;
			}
			newpix[c]=(byte)(k&0xFF);
			
        }
        
		System.arraycopy(newpix, 0, pixels, 0, pixels.length);

	}

}
