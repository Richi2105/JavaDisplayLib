/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package displayLib;

import displayLib.string.DisplayList;
import displayLib.string.DisplayString;

/**
 *
 * @author richard
 */
public interface DisplayCommunication {
    
    public static final int BOUNDARY_FOURTH = 0;
    public static final int BOUNDARY_HALF = 1;
    public static final int BOUNDARY_THREE_FOURTHS = 2;
    public static final int BOUNDARY_FULL = 3;
    
    public static final int POSITION_TOP = 14;
    public static final int POSITION_TOP_CENTER = 15;
    public static final int POSITION_CENTER = 16;
    public static final int POSITION_BOTTOM_CENTER = 17;
    public static final int POSITION_BOTTOM = 18;
    
    public static final int SIDE_LEFT = 100;
    public static final int SIDE_MIDDLE = 101;
    public static final int SIDE_RIGHT = 102;
    
    public void display(DisplayString string);
    
    public void display(DisplayList list);
    
}
