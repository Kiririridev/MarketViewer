package com.kiririri.marketviewer;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Bartlomiej Kirejczyk
 *
 * Class created to put ArrayList<Market> into ListView.
 * It extends BaseAdapter object, so it can be set to ListView with .setAdapter(Adapter).
 *
 * This class also contains some utility functions like sorting.
 *
 */

/**
 * My Adapter Class extends BaseAdapter class
 * This class is used to put data into ListView.
 * I also put here some utility methods like sorting
 *
 * */
public class MarketAdapter extends BaseAdapter
{
    private ArrayList<Market> marketArrayList = new ArrayList<>();


    /**
     * Class constructor with ArrayList<Market> argument.
     * Data is sorted by instrumentName
     *
     * @param marketArrayList
     */
    public MarketAdapter(final ArrayList<Market> marketArrayList) {

        quickSort(marketArrayList, 0, marketArrayList.size() - 1);

        this.marketArrayList = marketArrayList;
    }


    /**
     * Class constructor with InputStream argument.
     *
     * It calls MarketAdapter(ArrayList<Market>) and ArrayList is sorted there
     *
     * @param inputStream - InputStream of JSONObject
     */
    public MarketAdapter(InputStream inputStream)
    {
        this(readJSON(inputStream));
    }

    /**
     * Class constructor with JSONObject argument.
     *
     * It calls MarketAdapter(ArrayList<Market>) and ArrayList is sorted there
     *
     * @param object- JSONObject
     */

    public MarketAdapter(JSONObject object)
    {
        this(readJSON(object));

    }

    /**
     * method of BaseAdapter
     *
     * @return
     */
    @Override
    public int getCount()
    {
        return marketArrayList.size();
    }


    /**
     * method of BaseAdapter
     *
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position)
    {
        return marketArrayList.get(position);
    }


    /**
     * method of BaseAdapter
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    /**method of BaseAdapter
     *
     * LinearLayout is created here. It contains two TextView for two Market parameters.
     * One for instrumentName and one for displayOffer.
     * TextViews are placed Vertically, one under another.
     *
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LinearLayout view = new LinearLayout(parent.getContext());
        view.setOrientation(LinearLayout.VERTICAL);


        TextView instrumentNameTextView = new TextView(parent.getContext());
        TextView displayOfferTextView = new TextView(parent.getContext());



        instrumentNameTextView.setTextSize(2, 20.0f);
        instrumentNameTextView.setTextColor(Color.BLACK);



        instrumentNameTextView.setText(marketArrayList.get(position).getInstrumentName());
        displayOfferTextView.setText(Double.toString(marketArrayList.get(position).getDisplayOffer()));


        //view.removeAllViews();
        view.addView(instrumentNameTextView);
        view.addView(displayOfferTextView);

        return view;
    }


    /**
     * Sorting QuickSort Algorithm.
     *
     * I had to implement sorting algorithm on my own, because method Collections.sort()
     * requires API level 24, while device I was USB debugging on had only API level 21.
     *
     * This is popular quicksort algorithm that splits ArrayList into smaller ArrayList using recursion.
     *
     * It sorts ArrayList alphabetically, ascending.
     *
     *
     *
     * @param arrayList ArrayList<Market> to sort
     * @param start should be zero in first call
     * @param end usually ArrayList.size(0) -1 in first call
     */
    private static void quickSort(ArrayList<Market> arrayList, int start, int end)
    {
            int i, j;
            String pivot;

            i=start;
            j=end;

            if(j-i>=1)
            {
                pivot = arrayList.get(i).getInstrumentName();

                while(j>i)
                {
                    while(arrayList.get(i).getInstrumentName().compareTo(pivot)<=0 && j>i)
                    {
                        i++;
                    }
                    while(arrayList.get(j).getInstrumentName().compareTo(pivot)>=0 && j>=i)
                    {
                        j--;
                    }
                    if(j>i)
                    {
                        Collections.swap(arrayList, i, j);
                    }
                }
                Collections.swap(arrayList, start, j);

                quickSort(arrayList, start, j);
                quickSort(arrayList, j+1, end);
            }
    }




    /**
     * Method readJSON(JSONObject) parses JSONObject to ArrayList<Market>.
     * First it unpacks JSONObject to JSONArray and then unpacks each JSONObject in this array.
     * It creates new Market object and sets it's parameters to the one unpacked from JSON.
     *
     *
     * @param object
     * @return
     */
    private static ArrayList<Market> readJSON(JSONObject object)
    {
        ArrayList<Market> markets = new ArrayList<>();

        try {

            JSONArray jsonArray;
            JSONObject jsonObject = object;

            jsonArray = jsonObject.getJSONArray("markets");

            for(int i = 0; i<jsonArray.length(); i++) {

                String instrumentName = jsonArray.getJSONObject(i).getString("instrumentName");
                int instrumentVersion = jsonArray.getJSONObject(i).getInt("instrumentVersion");
                String displayPeriod = jsonArray.getJSONObject(i).getString("displayPeriod");
                String epic = jsonArray.getJSONObject(i).getString("epic");
                String exchangeID = jsonArray.getJSONObject(i).getString("exchangeId");
                Double displayBid = jsonArray.getJSONObject(i).getDouble("displayBid");
                Double displayOffer = jsonArray.getJSONObject(i).getDouble("displayOffer");
                int updateTime = jsonArray.getJSONObject(i).getInt("updateTime");
                double netChange = jsonArray.getJSONObject(i).getDouble("netChange");
                Boolean scaled = jsonArray.getJSONObject(i).getBoolean("scaled");
                int timeZoneOffset = jsonArray.getJSONObject(i).getInt("timezoneOffset");

                markets.add(i, new Market(instrumentName, displayOffer));

                markets.get(i).setDisplayBid(displayBid);
                markets.get(i).setDisplayPeriod(displayPeriod);
                markets.get(i).setEpic(epic);
                markets.get(i).setInstrumentVersion(instrumentVersion);
                markets.get(i).setExchangeId(exchangeID);
                markets.get(i).setNetChange(netChange);
                markets.get(i).setScaled(scaled);
                markets.get(i).setTimeZoneOffset(timeZoneOffset);
                markets.get(i).setUpdateTime(updateTime);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return markets;
    }


    /**
     * Method readJSON(InputStream) puts InputStream into a String content,
     * creates JSONObject of it and then calls above method readJSON(JSONObject) with created object.
     *
     * It is called by constructor when creating MarketAdapter from .json files from assets folder,
     * which give they return in InputStream object when when .getAssets().open("name").
     *
     * @param inputStream
     * @return
     */
    private static ArrayList<Market> readJSON(InputStream inputStream)
    {
        JSONObject jsonObject = null;

        try {
            String content;

            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            content = new String(buffer, "UTF-8");


            jsonObject = new JSONObject(content);



        }
        catch(Exception e)
        {
            e.printStackTrace();

        }

        return readJSON(jsonObject);
    }



    /**
     * Variables MarketAdapter in MainActivity are marked as final. It means we can't assign new object,
     * however we can change object itself. This method was created to wrap new JSONObject into Adapter,
     * without creating new Adapter object.
     *
     * This method is used, when we receive new JSONObject from other thread that is not null.
     * It is called by button click.
     *
     * @param object JSONObject
     */
    public void inputNewJSONObject(JSONObject object)
    {
        marketArrayList = readJSON(object);

        quickSort(marketArrayList, 0, marketArrayList.size()-1);
        this.marketArrayList = marketArrayList;

    }
}
