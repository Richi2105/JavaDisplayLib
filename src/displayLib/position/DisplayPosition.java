/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package displayLib.position;

import eventSystem.Serializeable;

/**
 *
 * @author richard
 */
public class DisplayPosition implements Serializeable{
    
    private int xPosition;
    private int yPosition;
    
    public DisplayPosition()
    {
        this.xPosition = 0;
        this.yPosition = 0;
    }
    
    public DisplayPosition(int x, int y)
    {
        this.xPosition = x;
        this.yPosition = y;
    }
    
    /**
     * @return the xPosition
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * @param xPosition the xPosition to set
     */
    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * @return the yPosition
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     * @param yPosition the yPosition to set
     */
    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    @Override
    public int getSerializedSize() {
        int size = 0;
        size += Integer.BYTES *2;
        return size;
    }

    @Override
    public int serialize(byte[] data) {
        int i = 0;
        for (int y=0; y < Integer.BYTES; y+=1)
        {
            data[i++] = (byte) (this.xPosition >>> ((8*y)));
        }
        for (int y=0; y < Integer.BYTES; y+=1)
        {
            data[i++] = (byte) (this.yPosition >>> ((8*y)));
        }
        return i;
    }

    @Override
    public int deserialize(byte[] data) {
        int i = 0;
        this.xPosition = 0;
        for (int y=0; y < Integer.BYTES; y+=1)
        {
            this.xPosition += ((int) (data[i++])) << ((8*y));     
        }
        this.yPosition = 0;
        for (int y=0; y < Integer.BYTES; y+=1)
        {
            this.yPosition += ((int) (data[i++])) << ((8*y));     
        }
        return i;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        int i = offset;
        for (int y=0; y < Integer.BYTES; y+=1)
        {
            data[i++] = (byte) (this.xPosition >>> ((8*y)));
        }
        for (int y=0; y < Integer.BYTES; y+=1)
        {
            data[i++] = (byte) (this.yPosition >>> ((8*y)));
        }
        return i - offset;
    }

    @Override
    public int deserialize(byte[] data, int offset) {
        int i = offset;
        this.xPosition = 0;
        for (int y=0; y < Integer.BYTES; y+=1)
        {
            this.xPosition += ((int) (data[i++])) << ((8*y));     
        }
        this.yPosition = 0;
        for (int y=0; y < Integer.BYTES; y+=1)
        {
            this.yPosition += ((int) (data[i++])) << ((8*y));     
        }
        return i - offset;
    }


    
}
