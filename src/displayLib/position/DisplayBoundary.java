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
public class DisplayBoundary implements Serializeable {

    private DisplayPosition begin;
    private DisplayPosition end;
    
    public DisplayBoundary()
    {
        this.begin = new DisplayPosition();
        this.end = new DisplayPosition();
    }
    
    public DisplayBoundary(int xPosBegin, int yPosBegin, int xPosEnd, int yPosEnd)
    {
        this.begin = new DisplayPosition(xPosBegin, yPosBegin);
        this.end = new DisplayPosition(xPosEnd, yPosEnd);
    }

    public DisplayBoundary(DisplayPosition begin, DisplayPosition end)
    {
        this.begin = begin;
        this.end = end;
    }
    
    
    public int getXPosBegin()
    {
        return this.begin.getXPosition();
    }
    public int getXPosEnd()
    {
        return this.end.getXPosition();
    }
    public int getYPosBegin()
    {
        return this.begin.getYPosition();
    }
    public int getYPosEnd()
    {
        return this.end.getYPosition();
    }

    public void setXPosBegin(int xBegin)
    {
        this.begin.setXPosition(xBegin);
    }
    public void setXPosEnd(int xEnd)
    {
        this.end.setXPosition(xEnd);
    }
    public void setYPosBegin(int yBegin)
    {
        this.begin.setYPosition(yBegin);
    }
    public void setYPosEnd(int yEnd)
    {
        this.end.setYPosition(yEnd);
    }
    
    /**
     * @return the begin
     */
    public DisplayPosition getBegin() {
        return begin;
    }

    /**
     * @param begin the begin to set
     */
    public void setBegin(DisplayPosition begin) {
        this.begin = begin;
    }

    /**
     * @return the end
     */
    public DisplayPosition getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(DisplayPosition end) {
        this.end = end;
    }
    
    
    @Override
    public int getSerializedSize() {
        int size = 0;
        size += this.begin.getSerializedSize();
        size += this.end.getSerializedSize();
        return size;
    }

    @Override
    public int serialize(byte[] data) {
        int i = 0;
        i += this.begin.serialize(data, i);
        i += this.end.serialize(data, i);
        return i;
    }

    @Override
    public int deserialize(byte[] data) {
        int i = 0;
        i += this.begin.deserialize(data, i);
        i += this.end.deserialize(data, i);
        return i;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        int i = offset;
        i += this.begin.serialize(data, i);
        i += this.end.serialize(data, i);
        return i - offset;
    }

    @Override
    public int deserialize(byte[] data, int offset) {
        int i = offset;
        i += this.begin.deserialize(data, i);
        i += this.end.deserialize(data, i);
        return i - offset;
    }
    
    
    
}
