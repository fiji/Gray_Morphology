package mmorpho.operators;

import ij.IJ;
import ij.process.ImageProcessor;
import mmorpho.LocalHistogram;

public class FastCloseOperator {
	
	private int[][]pg,pg_plus,pg_minus;
	private LocalHistogram bh,p_h,m_h;
	
	public FastCloseOperator(int[][] pg, int[][] pg_plus, int[][] pg_minus){
		this.pg = pg;
		this.pg_plus = pg_plus;
		this.pg_minus = pg_minus;
		bh=new LocalHistogram();
        p_h=new LocalHistogram();
        m_h=new LocalHistogram();
	}
	
	/**
     *  Performs fast graylevel dilation followed by fast graylevel erosion
     *  with arbitrary structural element
     *  @param ip the ImageProcessor
     * @param se the StructureElement
     */
    public void fclose(ImageProcessor ip){
        
        //fastDilate(ip,se);
        //fastErode(ip,se);
        int width = ip.getWidth();
        int height = ip.getHeight();
        int  max = 32767;//,k=0,x=0,y=0;
         
        //int pgzise=pg.length;
        byte[] pixels=(byte[])ip.getPixels();
        byte[] newpix= new byte[pixels.length];
        //String s="", s2="";
        
        int row=0,z=0;
        int index=0;
        
        // Dilation loop
        
        for (row=1;row<=height;row++){
            
            z=(row-1)*width;
            //    IJ.log("odd index  "+ z);
            bh.init(z,   width, height, pixels, pg, 0) ;
            //  bh.doMaximum();
            max=bh.getMaximum();
            newpix[z]=(byte)(max&0xFF);
            for  (int col=1; col<width;col++){
                index=z+col;
                //          s2+=" "+index+"\r\n";
                try {
                    p_h.init(index, width, height, pixels, pg_plus, 0) ;
                    m_h.init(index-1, width, height, pixels, pg_minus, 0) ;
                    bh.sub(m_h);
                    bh.add(p_h);
                    bh.doMaximum();
                    max=bh.getMaximum();
                    newpix[index]=(byte)(max&0xFF);
                }
                catch  ( ArrayIndexOutOfBoundsException aiob) {
                    IJ.log(" out index: "+index);
                }
            } //odd loop
            
            
        }
        
        int  min = -32767;//,k=0,x=0,y=0;
                
        byte[] newpix2= new byte[pixels.length];

        
        // Erosion loop
        
        //boolean changed=false;
        for (row=1;row<=height;row++){
            z=(row-1)*width;
            //                                //    IJ.log("odd index  "+ z);
            bh.init(z,   width, height, newpix, pg, 1) ;
            
            // bh.Log();
            //bh.doMinimum();
            min=bh.getMinimum();
            newpix2[z]=(byte)(min&0xFF);
            for  (int col=1; col<width;col++){
                index=z+col;
                //          s2+=" "+index+"\r\n";
                //  StringBuffer sb=new StringBuffer(100);
                try {
                    p_h.init(index, width, height, newpix, pg_plus, 1) ;
                    m_h.init(index-1, width, height, newpix, pg_minus, 1);
                    bh.sub(m_h);
                    bh.add(p_h);
                    
                    bh.doMinimum();
                    
                    min=bh.getMinimum();
                    newpix2[index]=(byte)(min&0xFF);
                }
                catch  ( ArrayIndexOutOfBoundsException aiob) {
                    IJ.log(" out index: "+index+" min "+min);
                    //IJ.log(sb.toString());
                }
            } //odd loop
            
            
        }
        
        
        
        System.arraycopy(newpix2, 0, pixels, 0, pixels.length);
        
    }
}
