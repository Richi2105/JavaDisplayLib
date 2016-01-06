/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package displayLib;

import displayLib.font.Font;
import displayLib.position.DisplayPosition;
import displayLib.string.DisplayList;
import displayLib.string.DisplayString;
import eventSystem.logging.Log;
import eventSystem.logging.LoggerAdapter;
import eventSystem.participants.EventSystemClient;
import eventSystem.telegram.Telegram;
import eventSystem.telegram.TelegramObject;

/**
 *
 * @author richard
 */
public class DotMatrixClient implements DisplayCommunication {
    
    private EventSystemClient displayClient;
    private DisplayPosition resolution;
    private DisplayString[] stringList;
    private DisplayList lastList;
    
    public DotMatrixClient()
    {
        this.displayClient = new EventSystemClient(Telegram.ID_DISPLAYCLIENT);
        this.displayClient.connectToMaster();
        this.resolution = new DisplayPosition();
        this.stringList = null;
        this.lastList = null;
        
        LoggerAdapter.initLoggerAdapter(displayClient);

        this.getDisplayResolution();
    }
    
    private void getDisplayResolution()
    {
        Telegram request = new Telegram(Telegram.ID_DISPLAY);
        request.setType(Telegram.REQUEST);
        this.displayClient.send(request);
        
        DisplayPosition res = new DisplayPosition();
        TelegramObject telegramResolution = new TelegramObject();
        telegramResolution.setObject(res);
        
        byte[] data = new byte[telegramResolution.getSerializedSize()];
        this.displayClient.receive(data, false);
        telegramResolution.deserialize(data);
        
        if (telegramResolution.getType() == Telegram.DISPLAYDIMENSION)
        {
            LoggerAdapter.log(Log.LOG_INFO, "Resolution received");
            this.resolution.setXPosition(res.getXPosition());
            this.resolution.setYPosition(res.getYPosition());
        }
        else
        {
            LoggerAdapter.log(Log.LOG_WARNING, "Wrong telegram received");
        }
    }
    
    private void initList(int boundary, int side, int font)
    {
	int fontSize = Font.getFontHeight(font);
	int displayableEntries = this.resolution.getYPosition() / fontSize;
        
        this.stringList = new DisplayString[displayableEntries];

	int xPosBegin = 0;
	int xSize = 0;

	switch (boundary)
	{
	case DisplayCommunication.BOUNDARY_FOURTH: xSize = this.resolution.getXPosition()/ 4; break;
	case DisplayCommunication.BOUNDARY_HALF: xSize = this.resolution.getXPosition() / 2; break;
	case DisplayCommunication.BOUNDARY_THREE_FOURTHS: xSize = (this.resolution.getXPosition() / 4) * 3; break;
	case DisplayCommunication.BOUNDARY_FULL: xSize = this.resolution.getXPosition(); break;
	default: xSize = this.resolution.getXPosition(); break;
	}

	switch (side)
	{
	case DisplayCommunication.SIDE_LEFT: xPosBegin = 0; break;
	case DisplayCommunication.SIDE_MIDDLE: xPosBegin = (this.resolution.getXPosition() / 2) - (xSize / 2); break;
	case DisplayCommunication.SIDE_RIGHT: xPosBegin = this.resolution.getXPosition() - xSize; break;
	default: xPosBegin = 0; break;
	}

	int yBegin = 0;

	for (int i=0; i<displayableEntries; i+=1)
	{
            DisplayString entry = new DisplayString(xPosBegin, yBegin, xSize, "");
            entry.setFont(font);
            this.stringList[i] = entry;
            yBegin += fontSize;
	}
    }
    
    @Override
    public void display(DisplayList list)
    {
        if (list.equals(this.lastList))
        {
/*            if (this.stringList == null)
            {
                this.initList(list);
            }
            else
*/          {
                TelegramObject objTelegram = new TelegramObject();
                objTelegram.setDestinationID(Telegram.ID_DISPLAY);
                objTelegram.setType(Telegram.DISPLAYDATA);
                int displayableEntries = this.stringList.length;

                int selectedEntry = list.getSelectedEntry();

                float center_rel = ((float) displayableEntries - 1) /2;
                center_rel = list.isScrollingDown() ? center_rel + ((float) displayableEntries) /6 : center_rel - ((float) displayableEntries) /6;
                System.out.println(String.format("Relative Center is %d", Math.round(center_rel)));

                int index_start = selectedEntry - Math.round(center_rel);
                if (index_start < 0)
                    index_start = 0;
/*                if (index_start > list.getSize())
                    index_start = list.getSize()-displayableEntries;
*/

                for (int i=0; i<this.stringList.length; i+=1)
                {
                    this.stringList[i].setString(list.getEntryAt(index_start));    
                    if (index_start == selectedEntry)
                        stringList[i].setInverted(true);
                    else
                        stringList[i].setInverted(false);
                    index_start += 1;
                    
                    objTelegram.setObject(stringList[i]);
                    this.displayClient.send(objTelegram);

                }
            }
        }
        else
        {
            this.lastList = list;
            this.initList(list.getBoundary(), list.getSideAlign(), list.getFont());
            this.display(list);            
        }
        
        
    }

    @Override
    public void display(DisplayString string) {
        TelegramObject objTelegram = new TelegramObject(Telegram.ID_DISPLAY, string);
        objTelegram.setType(Telegram.DISPLAYDATA);
        this.displayClient.send(objTelegram);
    }
    
}
