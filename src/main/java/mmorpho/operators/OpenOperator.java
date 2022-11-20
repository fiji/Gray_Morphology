package mmorpho.operators;

import ij.process.ImageProcessor;
import mmorpho.Constants;

public class OpenOperator {
	
	private int[][]pg;
	private UtilsMorph utilsMorph;
	int width, height;
	
	public OpenOperator(int[][] pg, int width, int height){
		this.pg = pg;
		this.width = width;
		this.height = height;
		this.utilsMorph = new UtilsMorph();
	}
	
	/** Performs graylevel erosion followed by graylevel dilation
     *  with arbitrary structural element se
     * @param ip the ImageProcessor
     * @param se the StructureElement
     */
    public void open(ImageProcessor ip){
        int width = ip.getWidth();
        int height = ip.getHeight();
        int min = -32767;//,k=0,x=0,y=0;
        int  max = 32768;
        int w=this.width;//se.getWidth();
        int h=this.height;//se.getHeight();
         // int[][] pg=se.getVect();
        
        int sz=pg.length;
        
        byte[] pixels=(byte[])ip.getPixels();
        byte[] newpix= new byte[pixels.length];
        byte[] newpix2= new byte[pixels.length];  
        int[] wnd=new int[sz];
        //  int i,j=0;
        for (int row=1; row<=height; row++) {
            for (int col=0; col<width; col++){
                int index=(row-1)*width+col; //erosion step
                 if (index< pixels.length) {
                     wnd=utilsMorph.getMinMax(index, width, height, pixels, pg, Constants.ERODE);
                     min=wnd[0]+255;
                     newpix[index]=(byte)(min&0xFF);
                  }
                int index2=(row-h-1)*width+col-w; //dilation step
                if ((index2>=0) && (index2< pixels.length)) {
                     wnd=utilsMorph.getMinMax(index2, width, height, newpix, pg,Constants.DILATE);
                     max=wnd[1]-255;
                     newpix2[index2]=(byte)(max&0xFF);
                }
            }
        }
            for (int row=height; row<=height+h; row++){
                for (int col=0; col<width+w; col++){
                      int index2=(row-h-1)*width+col-w; //dilation step
                      if ((index2>=0) && (index2< pixels.length)) {
                         wnd=utilsMorph.getMinMax(index2, width, height, newpix, pg,Constants.DILATE);
                         max=wnd[1]-255;
                         newpix2[index2]=(byte)(max&0xFF);
                      }
                }
            }
                
        System.arraycopy(newpix2, 0, pixels, 0, pixels.length);
        
        
        
    }
    
}
