package mmorpho.operators;

import ij.IJ;
import mmorpho.Constants;

public class UtilsMorph {
	
	public int[] getMinMax(int index, int width, int height, byte[] pixels, int[][] pg,  int type) {
        //  int[][]pg=se.getVectTransform(mType);
        int pgzise=pg.length;
        
        int[] wnd=new int[2];
        int i,j,k=0;
        int x,y=0;
        int min=255;
        int max=0;

        i=index/width;
        j=index%width;
        for (int g=0;g<pgzise;g++){
            y=i+pg[g][0];
            x=j+pg[g][1];
            try {
                if  ((x>=width) || (y>=height) || (x<0) || (y<0) ) {
                    if (type==Constants.DILATE)  k=0;
                    if (type==Constants.ERODE)  k=255;
                }
                else {
                    k=pixels[x+width*y]&0xFF;
                }
            }
            catch (ArrayIndexOutOfBoundsException ex) {
                
                k=x+width*y;
                IJ.log("AIOB x: "+x+" y: "+y+" index: "+k);
            }
            
            if (type==Constants.DILATE)    k=k+pg[g][2];
            if (type==Constants.ERODE)   k=k-pg[g][2];
            if (k<min) min=k; 
            if (k>max) max=k;
     
            
        }
        wnd[0]=min&0xFF;
        wnd[1]=max&0xFF;
        return wnd;
    }
	
	private int[] getRanks(int index, int width, int height, byte[] pixels,int[][] pg,  int type) {
        //  int[][]pg=se.getVectTransform(mType);
        int pgzise=pg.length;
        
        int[] wnd=new int[pgzise];
        int i,j,k=0;
        int x,y=0;
        
        i=index/width;
        j=index%width;
        for (int g=0;g<pgzise;g++){
            y=i+pg[g][0];
            x=j+pg[g][1];
            try {
                if  ((x>=width) || (y>height-1) || (x<0) || (y<0) ) {
                    if (type==Constants.DILATE)  k=0;
                    if (type==Constants.ERODE)  k=255;
                }
                else {
                    k=pixels[x+width*y]&0xFF;
                }
            }
            catch (ArrayIndexOutOfBoundsException ex) {
                
                k=x+width*y;
                IJ.log("AIOB x: "+x+" y: "+y+" index: "+k);
            }
            
            //  if (type==DILATE)    wnd[g]=k+pg[g][2];
            // if (type==ERODE)    wnd[g]=k-pg[g][2];
            
            if (type==Constants.ERODE)   {
                k=k-pg[g][2];
                
                int v=0;
                while (v<=g) {
                    if (k>=wnd[v]) {
                        v++;
                    }
                    else {
                        wnd[v]=k;
                        v++;
                        break;
                    }
                }
                
            }
            if (type==Constants.DILATE)  {
                k=k+pg[g][2];
                //int swp=0;
                int v=pgzise-1;
                
                while (v>=g) {
                    if (k<=wnd[v]) {
                        v--;
                    }
                    else {
                        wnd[v]=k;
                        v--;
                        break;
                    }
                }
            }
        }
        
        return wnd;
    }
	
	/** Calculates Min and Max element of an array
     * @return [0] - min [1] - max
     * @param a the array
     *
     * public static int[] getMinMax(int[] a) {
     * int min = a[0];
     * int max =a[0];
     * int value;
     * for (int i=0; i<a.length; i++) {
     * value = a[i];
     * if (value<min)
     * min = value;
     * if (value>max)
     * max = value;
     * }
     * int[] minAndMax = new int[2];
     * minAndMax[0] = min;
     * minAndMax[1] = max;
     * return minAndMax;
     * }
     */
    private final float findMean(float[] values) {
        float sum = values[0];
        for (int i=1; i<values.length; i++)
            sum += values[i];
        return (float)(sum/values.length);
    }
    
    /** Logs
     * @param arr the array
     */
    private void Log(int[][] a){
        String aStr="";
        // int w=(int) Math.sqrt(a.length);
        int h=a.length;
        for (int i=0;i<h;i++) {
            for (int j=0; j<a[i].length;j++){
                aStr+=a[i][j]+"  ";
            }
            IJ.log(aStr);
            aStr="";
        }
        
    }

}
