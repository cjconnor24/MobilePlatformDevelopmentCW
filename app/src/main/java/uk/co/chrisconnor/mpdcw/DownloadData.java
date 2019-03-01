package uk.co.chrisconnor.mpdcw;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus {
    IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK
};

class DownloadData extends AsyncTask<String, Void, String> {

    private static final String TAG = "DownloadData";
    private DownloadStatus mDownloadStatus;
    private final OnDownloadComplete mCallback;

    // THIS INTERFACE WILL MAKE SURE EVERY CALL CLASS HAS A METHOD FOR HANDLING THE RETURNED DATA
    interface OnDownloadComplete {
        void onDownloadComplete(String data, DownloadStatus status);
    }

    /**
     * Constructor will take the callback from the calling class and set status to IDLE
     *
     * @param callback Callback to handle the result in calling class
     */
    public DownloadData(OnDownloadComplete callback) {
        this.mDownloadStatus = DownloadStatus.IDLE;
        this.mCallback = callback;
    }



    @Override
    protected void onPostExecute(String data) {

        if (mCallback != null) {
            mCallback.onDownloadComplete(data, mDownloadStatus);
        }
        Log.d(TAG, "onPostExecute has completed");
    }

    @Override
    protected String doInBackground(String... strings) {

        // SETUP A CONNECTION AND A READER
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        // CHECK TO MAKE SURE A STRING WAS PASSED
        if (strings == null) {
            mDownloadStatus = DownloadStatus.NOT_INITIALISED;
            Log.d(TAG, "doInBackground: Strings are empty. Set to not initialised.");
            return null;
        }

        try {

            // SETUP THE VARIABLES FOR DOWNLOAD THE DATA
            mDownloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);

            // MAKE THE CONNECTION AND STORE THE CODE - e.g. 200, 404
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // THIS IS DEFAULT ANYWAY
            connection.connect();
            int response = connection.getResponseCode();

            Log.d(TAG, "doInBackground: The response code was" + response);

            // SETUP NEW VALUE TO STORE THE STREAM
            StringBuilder result = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));


            // LOOP THROUGH EACH LINE AND BUILD UP THE STREAM LOCALLY
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                result.append(line).append("\n"); // NEW LINES ARE GETTING STRIPPED SO NEED TO ADD THEM BACK IN
            }

            // SET STATUS TO OK AND RETURN THE STRING RESULT
            mDownloadStatus = DownloadStatus.OK;
            return result.toString();


        } catch (MalformedURLException e) {
            // BAD URL SUPPLIED
            Log.e(TAG, "doInBackground: Invalid URL" + e.getMessage());
        } catch (IOException e) {
            // ISSUE WITH THE HTTP CONNECTION OR THE BUFFERED READER
            Log.e(TAG, "doInBackground: IO Exception reading data" + e.getMessage());
        } catch (SecurityException e) {
            // ISSUE WITH PERMISSIONS, PROBABLY INTERNET PERMISSION
            Log.e(TAG, "doInBackground: Security exception. Need permission?" + e.getMessage());
        } finally {

            // TRY AND CLEAR UP ALL THE CONNECTIONS AND THE READERS TO FREE UP MEMORY
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: Error Closing the stream: " + e.getMessage());
                }
            }
        }

        mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;

    }

}
