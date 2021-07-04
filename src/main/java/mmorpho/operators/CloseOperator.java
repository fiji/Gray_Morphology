package mmorpho.operators;

import ij.process.ImageProcessor;
import mmorpho.Constants;

public class CloseOperator {
	
	private int[][]pg;
	private UtilsMorph utilsMorph;
	int width, height;
	
	public CloseOperator(int[][] pg, int width, int height){
		this.pg = pg;
		this.width = width;
		this.height = height;
		this.utilsMorph = new UtilsMorph();
	}
	
	/**
     *  Performs graylevel dilation followed by graylevel erosion
     *  with arbitrary structural element
     * @param ip the ImageProcessor
     * @param se the StructureElement
     *
     **/
    public void close(ImageProcessor ip){
        int width = ip.getWidth();
        int height = ip.getHeight();
        int w=this.width;//se.getWidth();
        int h=this.height;//se.getHeight();
        int  min=0,max = 255;//,k=0,x=0,y=0;

        //  IJ.log("pg: "+pg.length);
        int sz=pg.length;//se.getWidth()*se.getHeight();
        
        byte[] pixels=(byte[])ip.getPixels();
        byte[] newpix= new byte[pixels.length];
        byte[] newpix2= new byte[pixels.length];
        int[] wnd=new int[sz];
        
        for (int row=1; row<=height; row++) {
            for (int col=0; col<width; col++){
                int index=(row-1)*width+col; //dilation step
                 if (index< pixels.length) {
                     wnd=utilsMorph.getMinMax(index, width, height, pixels, pg, Constants.DILATE);
                     max=wnd[1]-255; 
                     newpix[index]=(byte)(max&0xFF);
                  }
                int index2=(row-h-1)*width+col-w; //erosion step
                if ((index2>=0) && (index2< pixels.length)) {
                     wnd=utilsMorph.getMinMax(index2, width, height, newpix, pg,Constants.ERODE);
                     min=wnd[0]+255;
                     newpix2[index2]=(byte)(min&0xFF);
                }
            }
        }
            for (int row=height; row<=height+h; row++){
                for (int col=0; col<width+w; col++){
                      int index2=(row-h-1)*width+col-w; //erosion step
                      if ((index2>=0) && (index2< pixels.length)) {
                         wnd=utilsMorph.getMinMax(index2, width, height, newpix, pg,Constants.ERODE);
                          min=wnd[0]+255;
                         newpix2[index2]=(byte)(min&0xFF);
                      }
                }
            }
        
        System.arraycopy(newpix2, 0, pixels, 0, pixels.length);
        
        
    }
    
}
