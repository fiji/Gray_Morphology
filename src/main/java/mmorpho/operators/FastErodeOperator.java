package mmorpho.operators;

import ij.IJ;
import ij.process.ImageProcessor;
import mmorpho.LocalHistogram;

public class FastErodeOperator {
	
	private int[][]pg,pg_plus,pg_minus;
	private LocalHistogram bh,p_h,m_h;
	
	public FastErodeOperator(int[][] pg, int[][] pg_plus, int[][] pg_minus){
		this.pg = pg;
		this.pg_plus = pg_plus;
		this.pg_minus = pg_minus;
		bh=new LocalHistogram();
        p_h=new LocalHistogram();
        m_h=new LocalHistogram();
	}
	
	/** Performs gray level erosion */
    public void fastErode(ImageProcessor ip) {
        int width = ip.getWidth();
        int height = ip.getHeight();
        int  min = -32767;//,k=0,x=0,y=0;
        
        //int pgzise=pg.length;
        byte[] pixels=(byte[])ip.getPixels();
        byte[] newpix= new byte[pixels.length];
       // String s="", s2="";
        
        int row=0, z=0;
        int index=0;
        
        
       //boolean changed=false;
        for (row=1;row<=height;row++){
            z=(row-1)*width;
            //      IJ.log("odd index  "+ z);
            bh.init(z,   width, height, pixels, pg, 1) ;
            
            // bh.Log();
            //bh.doMinimum();
            min=bh.getMinimum();
            newpix[z]=(byte)(min&0xFF);
            for  (int col=1; col<width;col++){
                index=z+col;
                //          s2+=" "+index+"\r\n";
                //  StringBuffer sb=new StringBuffer(100);
                try {
                    p_h.init(index, width, height, pixels, pg_plus, 1) ;
                    m_h.init(index-1, width, height, pixels, pg_minus, 1);
                    bh.sub(m_h);
                    bh.add(p_h);
                    
                    bh.doMinimum();
                    
                    min=bh.getMinimum();
                    newpix[index]=(byte)(min&0xFF);
                }
                catch  ( ArrayIndexOutOfBoundsException aiob) {
                    IJ.log(" out index: "+index+" min "+min);
                    //IJ.log(sb.toString());
                }
            } //odd loop
            
            
        }
        //IJ.log("add " +cnt + " sub " +cnt2);
        //         IJ.log(" " +index+" height "+height );
        //         IJ.log(s2);
        //         IJ.log(s);
        
        
        System.arraycopy(newpix, 0, pixels, 0, pixels.length);
    }
}
