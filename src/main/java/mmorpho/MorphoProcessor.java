/*-
 * #%L
 * Fiji distribution of ImageJ for the life sciences.
 * %%
 * Copyright (C) 2003 - 2020 Fiji developers.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
/*
 * MorphoProcessor.java
 *  @version 1.0.0; 13 November 2004
 * Created on 13 November 2004, 20:19
 */

package mmorpho;
import ij.IJ;
import ij.process.ImageProcessor;
import mmorpho.operators.BlackTopHatOperator;
import mmorpho.operators.CloseOperator;
import mmorpho.operators.DilateOperator;
import mmorpho.operators.ErodeOperator;
import mmorpho.operators.FastCloseOperator;
import mmorpho.operators.FastDilateOperator;
import mmorpho.operators.FastErodeOperator;
import mmorpho.operators.FastOpenOperator;
import mmorpho.operators.LineDilateOperator;
import mmorpho.operators.LineErodeOperator;
import mmorpho.operators.OpenOperator;
import mmorpho.operators.WhiteTopHatOperator;
/**
 *
 * @author  Dimiter Prodanov
 * @version  1.5   05 June 2006
 * 			 1.0;  09 Dec 2004
 */
public class MorphoProcessor implements Constants {
	private final static String version="1.5";
    private StructureElement se, minus_se, plus_se; //, down_se, up_se;
    private LocalHistogram bh,p_h,m_h;
    private int[][]pg,pg_plus,pg_minus;
    int width, height;
    
    /** Creates a new instance of MorphoProcessor */
    public MorphoProcessor(StructureElement se) {
    	this.se=se;
        width=se.getWidth();
        height=se.getHeight();
        minus_se = new StructureElement(se.H(se.Delta(SGRAD),HMINUS),width);
        plus_se=new StructureElement(se.H(se.Delta(NGRAD),HMINUS),width);
        bh=new LocalHistogram();
        p_h=new LocalHistogram();
        m_h=new LocalHistogram();
        pg=se.getVect();
        pg_plus=plus_se.getVect();
        pg_minus=minus_se.getVect();
        
    }
    
    
    private final static int ORIG=0,PLUS=1,MINUS=-1;
    
    public StructureElement getSE(int options) {
        switch (options) {
            case ORIG: {
                return se;
            }
            case PLUS:{
                return  plus_se;
            }
            case MINUS:
                return minus_se;
                
        }
        return se;
    }
    
    public final static int BINF=-256;
    
    public void erode(ImageProcessor ip){
    	ErodeOperator erodeOperator = new ErodeOperator(pg);
    	erodeOperator.erode(ip);
    }
    
    public void dilate(ImageProcessor ip){
    	DilateOperator dilateOperator = new DilateOperator(pg);
    	dilateOperator.dilate(ip);
    }
    
    public void open(ImageProcessor ip){
    	OpenOperator openOperator = new OpenOperator(pg, width, height);
    	openOperator.open(ip);
    }
    
    public void close(ImageProcessor ip){
    	CloseOperator closeOperator = new CloseOperator(pg, width, height);
    	closeOperator.close(ip);
    }   
    
    public void fastErode(ImageProcessor ip){
    	FastErodeOperator fastErodeOperator = new FastErodeOperator(pg, pg_plus, pg_minus);
    	fastErodeOperator.fastErode(ip);
    }
    
    public void fastDilate(ImageProcessor ip){
    	FastDilateOperator fastDilateOperator = new FastDilateOperator(pg, pg_plus, pg_minus);
    	fastDilateOperator.fastDilate(ip);
    }
    
    public void fopen(ImageProcessor ip){
    	FastOpenOperator fastOpenOperator = new FastOpenOperator(pg, pg_plus, pg_minus);
    	fastOpenOperator.fopen(ip);
    }
    
    public void fclose(ImageProcessor ip){
    	FastCloseOperator fastCloseOperator = new FastCloseOperator(pg, pg_plus, pg_minus);
    	fastCloseOperator.fclose(ip);
    }
    
    public void LineErode(ImageProcessor ip){
    	LineErodeOperator lineErodeOperator = new LineErodeOperator(se, width, height);
    	lineErodeOperator.LineErode(ip);
    }
    
    public void LineDilate(ImageProcessor ip){
    	LineDilateOperator lineDilateOperator = new LineDilateOperator(se, width, height);
    	lineDilateOperator.LineDilate(ip);
    }    

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
        
    public void whiteTopHat(ImageProcessor ip){
    	WhiteTopHatOperator whiteTopHatOperator = new WhiteTopHatOperator(pg, width, height);
    	whiteTopHatOperator.whiteTopHat(ip);
    }
    
    public void blackTopHat(ImageProcessor ip){
    	BlackTopHatOperator blackTopHatOperator = new BlackTopHatOperator(pg, width, height);
    	blackTopHatOperator.blackTopHat(ip);
    }
    
}
