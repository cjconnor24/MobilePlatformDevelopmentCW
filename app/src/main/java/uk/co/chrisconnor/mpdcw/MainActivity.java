/*  Starter project for Mobile Platform Development in Semester B Session 2018/2019
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/

//
// Name                 Chris Connor
// Student ID           S1715477
// Programme of Study   Computing (BSc Hons)
//
package uk.co.chrisconnor.mpdcw;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "MainActivity";
    private TextView rawDataDisplay;
    private Button startButton;

    private String url1 = "";
    private String urlSource = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private ListView earthquakeList;
    List<Earthquake> earthquakes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the raw links to the graphical components
        rawDataDisplay = (TextView) findViewById(R.id.rawDataDisplay);
        startButton = (Button) findViewById(R.id.startButton);

        // POINTLESS 😂
        startButton.setOnClickListener(this);

        // EARTHQUAKE LIST
        earthquakeList = (ListView) findViewById(R.id.earthquakeList);

        // AUTODOWNLOAD
        startProgress();

        // TODO: DOWNLOAD THE XML

        // TODO: PARSE THE XML

        // TODO: DISPLAY THE LISTVIEW

    }


    public void onClick(View aview) {
        startProgress();
    }



    public void startProgress() {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    } //

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    // TODO: MAYBE CHANGE THESE FOR ASYNC TASK - WAITING ON IAIN TO REPLY TO MAIL
    private class Task implements Runnable {

        private String url;

        private Task(String url) {
            this.url = url;
        }

        @Override
        public void run() {

            URL aurl;
            URLConnection connection;

            String inputLine = "";
            StringBuilder xmlResult = new StringBuilder();

            Log.d(TAG, "run: RUNNING HERE");

            try {

                BufferedReader reader = null;
                aurl = new URL(url);
                connection = aurl.openConnection();

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                int charsRead;
                char[] inputBuffer = new char[500];

                while(true){

                    charsRead = reader.read(inputBuffer);
                    if(charsRead < 0){
                        break;
                    }
                    if(charsRead > 0){
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                    }

                }

                reader.close();

                ParseEarthquakes parseEarthquakes = new ParseEarthquakes();

                List<Earthquake> list = parseEarthquakes.parse(xmlResult.toString().trim());

                EarthquakeListAdapter earthquakeListAdapter = new EarthquakeListAdapter(MainActivity.this, R.layout.list_earthquake, list);
//                earthquakeList.setAdapter(earthquakeListAdapter);

                Log.d(TAG, "run: There are" + list.size() + " earthquakes in the xml");
                for(Earthquake e : list){
                    Log.d(TAG, "Earthquake" + e.toString());
                }


            } catch (MalformedURLException e) {
                Log.d(TAG, "run: Malformed URL Exception " + e.getMessage());
            }
            catch (IOException e){
                Log.e(TAG, "run: IO Exception ERROR " + e.getMessage());
            }

            //
            // Now that you have the xml data you can parse it
            //
            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");

                    // TODO: PARSE DATA HERE INSTEAD MAYBE
//                    rawDataDisplay.setText(result);
                }
            });
        }

    }

    // IGNORE BELOW FOR NOW

    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }

}