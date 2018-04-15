package com.kiririri.marketviewer;

/**
 * @author Bartlomiej Kirejczyk
 *
 * Class Market that holds parameters taken from JSONObject.
 *
 * I decided to put two arguments in constructor, as they are necessary to correctly show object in ListView.
 * Rest of parameters are accessed via setters. Only instrumentName and displayOffer have their getters, because
 * only this variables are used for something, except setting.
 */
public class Market {
    private String instrumentName;
    private int instrumentVersion = 0;
    private String displayPeriod = "";
    private String epic = "";
    private String exchangeId = "";
    private double displayBid = 0.0;
    private double displayOffer;
    private int updateTime = 0;
    private double netChange = 0.0;
    private boolean scaled = false;
    private int timeZoneOffset = 0;


    public Market(String instrumentName, double displayOffer)
    {
        this.instrumentName = instrumentName;
        this.displayOffer = displayOffer;
    }


    public String getInstrumentName() {
        return this.instrumentName;
    }



    public double getDisplayOffer()
    {
        return this.displayOffer;
    }

    public void setDisplayPeriod(String displayPeriod)
    {
        this.displayPeriod = displayPeriod;
    }

    public void setEpic(String epic)
    {
        this.epic=epic;
    }

    public void setExchangeId(String exchangeId)
    {

        this.exchangeId = exchangeId;
    }

    public void setDisplayBid(double displayBid)
    {
        this.displayBid = displayBid;
    }

    public void setNetChange(double netChange)
    {
        this.netChange = netChange;
    }

    public void setScaled(boolean scaled)
    {
        this.scaled = scaled;
    }

    public void setUpdateTime(int updateTime)
    {
        this.updateTime = updateTime;
    }

    public void setTimeZoneOffset(int timeZoneOffset) {
        this.timeZoneOffset = timeZoneOffset;
    }

    public void setDisplayOffer(double displayOffer) {
        this.displayOffer = displayOffer;
    }

    public void setInstrumentVersion(int instrumentVersion) {
        this.instrumentVersion = instrumentVersion;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }
}
