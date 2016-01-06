/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package displayLib.string;

import displayLib.DotMatrixDisplayable;
import displayLib.font.Font;
import displayLib.position.DisplayBoundary;
import displayLib.position.DisplayPosition;
import eventSystem.Serializeable;
import eventSystem.logging.Log;
import eventSystem.logging.LoggerAdapter;
import displayLib.DisplayCommunication;

/**
 *
 * @author richard
 */
public class DisplayString implements DotMatrixDisplayable, Serializeable {
    
    private byte[] str;
    private byte inverted;
    private int fontType;
    private DisplayBoundary box;
    private static DisplayCommunication client;
        
    private static final int DISPLAYSTRINGSIZE = 30;
    
    public DisplayString(DisplayPosition begin, DisplayPosition end, String str)
    {
        this.box = new DisplayBoundary(begin, end);
        this.inverted = 0;
        this.fontType = Font.standard_8_mono;
        this.str = new byte[DisplayString.DISPLAYSTRINGSIZE];
        int i = 0;
        for (char c : str.toCharArray())
        {
            this.str[i++] = (byte) c;
        }
    }
    
    public DisplayString(int x, int y, int length, String str)
    {
        this.box = new DisplayBoundary(x, y, x + length, Font.getFontHeight(Font.standard_8_mono));
        this.inverted = 0;
        this.fontType = Font.standard_8_mono;
        this.str = new byte[DisplayString.DISPLAYSTRINGSIZE];
        int i = 0;
        for (char c : str.toCharArray())
        {
            this.str[i++] = (byte) c;
        }
    }
    
    public DisplayString()
    {
        this.box = new DisplayBoundary();
        this.inverted = 0;
        this.fontType = Font.standard_8_mono;
        this.str = new byte[DisplayString.DISPLAYSTRINGSIZE];
    }
    
    /**
     * sets the DotMatrixClient, so list can be displayed
     */
    public static void setCommunicationModule(DisplayCommunication dmclient)
    {
        DisplayString.client = dmclient;
    }
    
   /**
    * returns the position of the string (upper left corner)
    */
   public DisplayPosition getPositionBegin()
   {
       return this.box.getBegin();
   }

   /**
    * returns the lower right corner of the string
    */
   public DisplayPosition getPositionEnd()
   {
       return this.box.getEnd();
   }

   /**
    * sets the string to a new position, the dimensions determined by the end position are kept.
    */
   public void setPosition(DisplayPosition pos)
   {
       int xEndPos = pos.getXPosition() + this.box.getXPosEnd() - this.box.getXPosBegin();
       int yEndPos = pos.getYPosition() + this.box.getYPosEnd() - this.box.getYPosBegin();
       this.box.setXPosEnd(xEndPos);
       this.box.setYPosEnd(yEndPos);
       this.box.setBegin(pos);
   }

   /**
    * returns the string to be displayed
    */
   public String getString()
   {
       return new String(this.str);
   }

   /**
    * sets the string to be displayed
    */
   public void setString(String str)
   {
       this.str = new byte[DisplayString.DISPLAYSTRINGSIZE];
       int i = 0;
       for (char c : str.toCharArray())
       {
           this.str[i++] = (byte) c;
           if (i >= DisplayString.DISPLAYSTRINGSIZE - 1)
               break;
       }
   }

   /**
    * sets the font of the string
    */
   public void setFont(int f)
   {
       this.fontType = f;
   }

   /**
    * returns the font of the string
    */
   public int getFont()
   {
       return this.fontType;
   }

   /**
    * returns whether the string should be displayed inverted
    */
   public boolean isInverted()
   {
       return this.inverted != 0 ? true : false;
   }

   /**
    * sets the string to be displayed inverted
    */
   public void setInverted(boolean invert)
   {
       if (invert)
           this.inverted = 1;
       else
           this.inverted = 0;
   }
   
    @Override
    public void display() {
        if (DisplayString.client != null)
        {
            DisplayString.client.display(this);
        }
        else
        {
            LoggerAdapter.log(Log.LOG_WARNING, "DisplayString: no valid DisplayClient set");
        }
    }

    @Override
    public int getSerializedSize() {
        int size = 0;
        size += Byte.BYTES * DisplayString.DISPLAYSTRINGSIZE;
        size += Byte.BYTES;
        size += Integer.BYTES;
        size += this.box.getSerializedSize();
        return size;
    }

    @Override
    public int serialize(byte[] data) {
        return this.serialize(data, 0);
    }

    @Override
    public int deserialize(byte[] data) {
        return this.deserialize(data, 0);
    }

    @Override
    public int serialize(byte[] data, int offset) {
        int i = offset;
        for (int y=0; y < DisplayString.DISPLAYSTRINGSIZE; y+=1)
        {
            data[i++] = this.str[y];
        }
        for (int y=0; y < Byte.BYTES; y+=1)
        {
            data[i++] = (byte) (this.inverted >>> ((8*y)));
        }
        for (int y=0; y < Integer.BYTES; y+=1)
        {
            data[i++] = (byte) (this.fontType >>> ((8*y)));
        }
        i += this.box.serialize(data, i);
        
        return i - offset;
    }

    @Override
    public int deserialize(byte[] data, int offset) {
        int i = 0;
        this.str = new byte[DisplayString.DISPLAYSTRINGSIZE];
        for (int y=0; y < DisplayString.DISPLAYSTRINGSIZE; y+=1)
        {
            this.str[y] = data[i++];
        }
        this.inverted = 0;
        for (int y=0; y < Byte.BYTES; y+=1)
        {
            this.inverted += ((int) (data[i++])) << ((8*y));            
        }
        this.fontType = 0;
        for (int y=0; y < Integer.BYTES; y+=1)
        {
            this.fontType += ((int) (data[i++])) << ((8*y));            
        }
        i += this.box.deserialize(data, i);
        
        return i - offset;
    }
    
}
