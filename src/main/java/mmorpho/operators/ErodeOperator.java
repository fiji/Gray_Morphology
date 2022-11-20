package mmorpho.operators;

import ij.process.ImageProcessor;
import mmorpho.Constants;

public class ErodeOperator {
	
	private int[][]pg;
	private UtilsMorph utilsMorph;
	
	public ErodeOperator(int[][] pg){
		this.pg = pg;
		this.utilsMorph = new UtilsMorph();
	}
	
	/** Performs gray level erosion */
    public void erode(ImageProcessor ip){
        
        int width = ip.getWidth();
        int height = ip.getHeight();
        int min = -32767; //,k=0,x=0,y=0;
       
        
        int sz=pg.length;//se.getWidth()*se.getHeight();
        // byte[] p=(byte[])ip.convertToByte(false).getPixels();
        byte[] pixels=(byte[])ip.getPixels();
        
        int[] wnd=new int[sz];
        
        byte[] newpix= new byte[pixels.length];
        
        //int i,j=0;
        for (int c=0;c<pixels.length;c++) {
            // i=c/width;
            // j=c%width;
            wnd=utilsMorph.getMinMax(c, width, height, pixels, pg, Constants.ERODE);
            min=wnd[0]+255;
            newpix[c]=(byte)(min&0xFF);
            
        }
        
        
        System.arraycopy(newpix, 0, pixels, 0, pixels.length);
    }
}
