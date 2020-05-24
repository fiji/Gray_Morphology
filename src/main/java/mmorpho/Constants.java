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
 * Constants.java
 *
 *     @version 1.0.0;  21 Nov 2003
 *
 *     @author	Dimiter Prodanov
 *     @author  University of Leiden
 *
 * @author  dprodanov
 */
package mmorpho;

public interface Constants {
    public final static int FREE=-1;
    public final static int CIRCLE=0;
    public final static int DIAMOND=1;
    public final static int LINE=2;
    public final static int VLINE=3;
    public final static int HLINE=4;
    public final static int CLINE=5;
    public final static int HPOINTS=6;
    public final static int VPOINTS=5;
    public final static int SQARE=7;
    public final static int RING=8;
    public final static int[] OFFSET0 ={0,0};
    public final static int[] NGRAD = {0,1};
    public final static int[] SGRAD = {0,-1};
    public final static int[] WGRAD ={1,0};
    public final static int[] EGRAD ={-1,0};
    public final static int[] NEGRAD ={-1,-1};
     public final static int[] NWGRAD = {1,-1}; 
    public final static int[] SEGRAD = {-1,-1}; 
      public final static int[] SWGRAD = {1,1}; 
    public final static int MAXSIZE=151;
    public final static int HPLUS=1;
    public final static int HMINUS=-1;
    public final static int PERIM=-16;
    public final static int FULLAREA=-32;
    
    public final static int ERODE=32, DILATE=64;
    public  static final double cor3=1.5-Math.sqrt(3.0);
}
