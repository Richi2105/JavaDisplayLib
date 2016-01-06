/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package displayLib.string;

import displayLib.DotMatrixClient;
import displayLib.font.Font;
import eventSystem.logging.Log;
import eventSystem.logging.LoggerAdapter;
import java.util.Vector;
import displayLib.DisplayCommunication;

/**
 *
 * @author richard
 */
public class DisplayList {
    
    private Vector<String> list;
    
    private int entry_nr;
    private int prevous_entry_nr;    
    private int selected_entry;
    private int previous_selected_entry;
    
    private int fontType;
    
    private final int boundary;
    private final int side;
    
    private static DisplayCommunication client;
    
    public DisplayList(int relativeBoundary, int sideAling)
    {
	this.side = sideAling;
	this.boundary = relativeBoundary;
	this.fontType = Font.standard_8_mono;
        
        this.list = new Vector<>();

	this.entry_nr = 0;
	this.prevous_entry_nr = 0;
	this.selected_entry = -1;
	this.previous_selected_entry = -1;
    }
    
    /**
     * sets the DotMatrixClient, so list can be displayed
     */
    public static void setCommunicationModule(DisplayCommunication dmclient)
    {
        DisplayList.client = dmclient;
    }

    /**
     * adds an entry to the list
     * @param entry: to be added at the bottom of the list
     * returns the size of the list
     */
    public int addEntry(String entry)
    {
        this.list.add(entry);
        return list.size();
    }
    
    public int getSize()
    {
        return this.list.size();
    }

    /**
     * sets an entry to be displayed inverted
     * @param entry: the entry number to be displayed inverted, must be smaller than the total number of entries
     */
    public void selectEntry(int entry)
    {
        if (entry < this.getSize() && entry >= 0)
	{
            this.previous_selected_entry = this.selected_entry;
            this.selected_entry = entry;
	}
        else if (entry >= this.getSize())
            selectEntry(this.getSize()-1);
        else if (entry < 0)
            selectEntry(0);
    }
    
    /**
     * scroll the list down step values
     * @param step the number of values to scroll down
     * @return the now selected entry
     */
    public int scrollDown(int step)
    {
        this.selectEntry(this.selected_entry + step);
        return this.selected_entry;
    }
    
    public int scrollUp(int step)
    {
        this.selectEntry(this.selected_entry - step);
        return this.selected_entry;
    }

    /**
     * returns the index of the selected entry
     */
    public int getSelectedEntry()
    {
        return this.selected_entry;
    }

    /**
     * clears the list and associated values
     */
    public void clear()
    {
        this.list.clear();
        this.selected_entry = -1;
        this.previous_selected_entry = -1;
    }

    /**
     * displays the list on the display, with the selected entry in focus
     * this must be called when changes are done to the list (except for clear())
     */
    public void display()
    {
        if (DisplayList.client != null)
        {
            DisplayList.client.display(this);
        }
        else
        {
            LoggerAdapter.log(Log.LOG_WARNING, "DisplayList: no valid DisplayClient set");
        }
    }

    /**
     * sets the font of the list. Warning: this should be done BEFORE calling display().
     * Changing the font after display() has been called has no effect.
     * @param type: the font type identifier
     */
    public void setFont(int fontType)
    {
        this.fontType = fontType;
    }

    /**
     * returns the font identifier
     */
    public int getFont()
    {
        return this.fontType;
    }

    /**
     * returns the relative size in x-direction of the list
     */
    public int getBoundary()
    {
        return this.boundary;
    }

    /**
     * returns the side alignment of the list
     */
    public int getSideAlign()
    {
        return this.side;
    }

    /**
     * returns whether the previous selected entry was above or below the current selected entry,
     * thus indicating if the list is scrolling down
     * @return true if list scrolls down, false if scrolls up
     */
    public boolean isScrollingDown()
    {
        if (this.selected_entry <= this.previous_selected_entry)
            return false;
        else
            return true;
    }

    public String getEntryAt(int index)
    {
        if (index >= 0 && index < this.getSize())
            return this.list.get(index);
        else
            return " ";
    }
    
}
