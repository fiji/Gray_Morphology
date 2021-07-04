package mmorpho.operators;

import ij.process.ImageProcessor;
import mmorpho.Constants;

public class DilateOperator {
	
	private int[][]pg;
	private UtilsMorph utilsMorph;
	
	public DilateOperator(int[][] pg){
		this.pg = pg;
		this.utilsMorph = new UtilsMorph();
	}
	
	/** Performs gray level dilation
     * @param ip the ImageProcessor
     * @param se the StructureElement
     */
    public void dilate(ImageProcessor ip){
        
        int width = ip.getWidth();
        int height = ip.getHeight();
        int  max = 32768;//,k=0,x=0,y=0;
      
        //int[][]pg=se.getVect();
        //  IJ.log("pg: "+pg.length);
        int sz=pg.length; //se.getWidth()*se.getHeight();
       
        byte[] pixels=(byte[])ip.getPixels();
        int[] wnd=new int[sz];
               
        byte[] newpix= new byte[pixels.length];
        //int i,j=0;
        for (int c=0;c<pixels.length;c++) {
            
            //i=c/width;
            //j=c%width;
            wnd=utilsMorph.getMinMax(c, width, height, pixels, pg,Constants.DILATE);
   
            max=wnd[1]-255;
            newpix[c]=(byte)(max&0xFF);
            
        }
        
        System.arraycopy(newpix, 0, pixels, 0, pixels.length);
    }
    
}
