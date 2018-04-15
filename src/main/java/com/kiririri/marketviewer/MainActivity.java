package com.kiririri.marketviewer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONObject;

import java.io.IOException;



public class MainActivity extends Activity {

    private ListView list;

    private Button buttonGB;
    private Button buttonFR;
    private Button buttonGER;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * Three objects GetJSONTask are created and executed. Each of them tries to get JSONObject from urlString added in arguments.
         * They do this in another threads and usually they do not menage to do this in time.
         * Waiting for these threads to finish would create a huge lag in application.
         * If JSONObjects are null, app loads old JSONObject from assets folder (the data is not up-to-date).
         * We can get these online JSONObject later using buttons.
         *
         * adapterGB, adapterFR, adapterDe had to be created final to be accessed by inner classes in button's initialisation,
         * but at the same time, their constructor throws IOException, which must be put in try/catch block.
         * To avoid compiler warning about risk of not initializing these variables, I created temporary variables
         * and pass their value to final ones.
         */

        final RetrieveJSONTask taskGB = new RetrieveJSONTask("https://api.ig.com/deal/samples/markets/ANDROID_PHONE/en_GB/igi");
        final RetrieveJSONTask taskFR = new RetrieveJSONTask("https://api.ig.com/deal/samples/markets/ANDROID_PHONE/fr_FR/frm");
        final RetrieveJSONTask taskDE = new RetrieveJSONTask("https://api.ig.com/deal/samples/markets/ANDROID_PHONE/de_DE/dem");
        taskGB.execute();
        taskFR.execute();
        taskDE.execute();

        JSONObject jsonObjectGB = taskGB.getJSONObject();
        JSONObject jsonObjectFR = taskFR.getJSONObject();
        JSONObject jsonObjectDE = taskDE.getJSONObject();

        final MarketAdapter adapterGB;
        final MarketAdapter adapterFR;
        final MarketAdapter adapterDE;

        MarketAdapter tempAdapterGB = null;
        MarketAdapter tempAdapterFR = null;
        MarketAdapter tempAdapterDE = null;

        if(jsonObjectGB == null || jsonObjectFR==null || jsonObjectDE==null)
        {
            try
            {
                tempAdapterGB = new MarketAdapter(getAssets().open("en_GB.json"));
                tempAdapterFR = new MarketAdapter(getAssets().open("fr_FR.json"));
                tempAdapterDE = new MarketAdapter(getAssets().open("de_DE.json"));
            }catch(IOException e)
            {
                e.printStackTrace();
            }
        } else
            {
             tempAdapterGB = new MarketAdapter(jsonObjectGB);
             tempAdapterFR = new MarketAdapter(jsonObjectFR);
             tempAdapterDE = new MarketAdapter(jsonObjectDE);
            }


            adapterGB = tempAdapterGB;
            adapterFR = tempAdapterFR;
            adapterDE = tempAdapterDE;

        /**
         * ListView initialisation.
         * It loads ListView from XML file.
         * Agaoin checks if taskGB has completed background process.
         * Sets adapterGB as first adapter so markets from GB are shown as default.
         *
         */

        list = findViewById(R.id.listView1);

        if(taskGB.getJSONObject()!=null)
        {
            adapterGB.inputNewJSONObject(taskGB.getJSONObject());
        }
        list.setAdapter(adapterGB);





        //=================================BUTTONS=======================================
        /**
         * Button initialization with event handling
         *
         * Clicking button does two things:
         * 1. It checks if Object RetrieveJSONTask has downloaded JSONObject from given URL.
         * It it succeeded, it passes retrieved JSONObject to adapter.
         * 2. It changes data in ListView to markets from another country,
         * by setting it other MyAdapter.
         *
         */

        buttonGB = findViewById(R.id.buttonGB);
        buttonGB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(taskGB.getJSONObject()!=null)
                {
                    adapterGB.inputNewJSONObject(taskGB.getJSONObject());
                }

                list.setAdapter(adapterGB);

            }

        });



        buttonFR = findViewById(R.id.buttonFR);
        buttonFR.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(taskDE.getJSONObject()!=null)
                {
                    adapterDE.inputNewJSONObject(taskDE.getJSONObject());
                }

                list.setAdapter(adapterFR);
            }
        });



        buttonGER = findViewById(R.id.buttonGER);
        buttonGER.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(taskDE.getJSONObject()!=null)
                {
                    adapterDE.inputNewJSONObject(taskDE.getJSONObject());
                }

                list.setAdapter(adapterDE);
            }

        });

        //=====================================================================================
    }
}





