package mmorpho.operators;

import ij.IJ;
import ij.process.ImageProcessor;
import mmorpho.Constants;
import mmorpho.StructureElement;

public class LineDilateOperator {
	
	int width, height;
	private StructureElement se;
	
	public LineDilateOperator(StructureElement se, int width, int height){
		this.se = se;
		this.width = width;
		this.height = height;
	}
	

//  implementation of the DAA algorithm for fast linear dilation
    public void LineDilate(ImageProcessor ip) {
        
        int width = ip.getWidth();
        int height = ip.getHeight();
        int  min = -32767;//,k=0,x=0,y=0;
        
        int w=Math.max(this.width,this.height);
       
        int shift=se.getShift();
        int p=w-2*shift;
        
        int[] wnd=new int[2*p];
        int[] R=new int[p];
        int[] S=new int[p];
       // int r=(int)se.getR();
        int type=se.getType();
       //  IJ.log("r: "+r+ " l: "+p+" sh: "+shift);
        
        byte[] pixels=(byte[])ip.getPixels();
        byte[] newpix= new byte[pixels.length];
        
        int z=0, index=0;
        //IJ.log("type "+type);
        if (type==Constants.HLINE) {
            
            for (int row=0;row<height;row++){
                z=row*width;

                for  (int col=0; col<=width+p;col+=p){
                    int k=0;
                    //wnd population
                    for (int i=-p; i<p; i++) {
                        int x=col+i;
                        index=z+x;
                        //  edge effects
                        try {
                            if  ((x>width) || (x<0) || (index>=width*height)) {
                                k=0;
                           }
                            else {
                                k=pixels[index]&0xFF;
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException ex) {
                            IJ.log("AIOB row: "+row+" col: "+col+" index: "+index+"x: "+x);
                        }
                        wnd[i+p]=k;
                } // wnd

                
                R[0]=wnd[p]; //center
                S[0]=wnd[p]; //center

                for (int j=1; j<p; j++){
                    R[j]=Math.max(R[j-1],wnd[p-j]); //backward
                    S[j]=Math.max(S[j-1],wnd[j+p]);//forward
                }
                int offset=0;
                 
                for (int j=0; j<p;j++){
                    
                    try {
                        min=Math.max(R[j],S[p-j-1]);
                        offset=-j+p/2;
                        if ( (col+offset>=0) && (col+offset<width))
                            newpix[z+col+offset]=(byte)(min&0xFF);
                          
                        }
                        catch  ( ArrayIndexOutOfBoundsException aiob) {
                            IJ.log("row: "+row+" col: "+col+" off: "+offset);
                        }
                    }
                } //odd loop
            }
        }
    
        
        if (type==Constants.VLINE) {
          for  (int col=0; col<width;col++){
       
            //z=(col-1);
           for (int row=0;row<height+p;row+=p){
               // z=(row-1)*width;
                int k=0;
                //wnd population
                for (int i=-p; i<p; i++) {
                    int y=row+i;
                    index=col+y*width;
                    //  edge effects
                    try {
                        if  ((y>height) || (y<0) || (index>width*height-1)) {
                            k=0;
                       }
                        else {
                            k=pixels[index]&0xFF;
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException ex) {
        
                        IJ.log("AIOB row: "+row+" col: "+col+" index: "+index+"y: "+y);
                    }
                    wnd[i+p]=k;

                } // wnd

                
                R[0]=wnd[p]; //center
                S[0]=wnd[p]; //center

                for (int j=1; j<p; j++){
                    R[j]=Math.max(R[j-1],wnd[p-j]); //backward
                    S[j]=Math.max(S[j-1],wnd[j+p]);//forward
                }
                 int y=0;
                 
                for (int j=0; j<p;j++){
                     try {
                        min=Math.max(R[j],S[p-j-1]);
                        y=p/2-j;
                        if ( (row+y>=0) && (col+(row+y)*width<height*width))
                            newpix[col+(row+y)*width]=(byte)(min&0xFF);
                      //  newpix[col+(row+y)*width]=(byte)(0xA0);
                    }
                    catch  ( ArrayIndexOutOfBoundsException aiob) {
                        IJ.log("row: "+row+" col: "+col+" off: "+y);
         
                    }
                }
            } //odd loop
            
            
        }
        }
         
        System.arraycopy(newpix, 0, pixels, 0, pixels.length);
        
        //  }
        //    else IJ.log("wrong type");
    }
    
}
