/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package displayLib.font;

/**
 *
 * @author richard
 */
public class Font {
    
    public static final int standard_8_mono = 0;
    public static final int standard_6_mono = 1;
    public static final int standard_6 = 2;
    
    public static int getFontHeight(int fontType)
    {
        switch (fontType)
        {
            case Font.standard_6: return 6;
            case Font.standard_6_mono: return 6;
            case Font.standard_8_mono: return 8;
            default: return 0;
        }
    }
    
}
