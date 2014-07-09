package droiders.dark.seenbeenread.misc;

import android.app.Fragment;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import droiders.dark.seenbeenread.activities.BaseActivity;
import droiders.dark.seenbeenread.menufragments.MoviesFragment;

/**
 * Created by Strider on 7/9/2014.
 */
public class Search {
    ArrayList<String> searchResults = new ArrayList<String>();
    String query;

    public Search(String query)
    {
        this.query=query;
        getSearchResults();
    }

    public void getSearchResults()
    {

        getMovieDetails details = new getMovieDetails();
        details.execute();

    }
    public void done()
    {
        BaseActivity.setSearchResults(searchResults);
    }

    private class getMovieDetails extends AsyncTask<Void, Void, Void> {


        String title1,path1;

        @Override
        protected Void doInBackground(Void... params) {

            JsonParser jParser = new JsonParser();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl("http://api.themoviedb.org/3/search/movie?query="+query+"&api_key=d1886d0cd7133b3707f899d73c47d008");
            try {
                JSONArray results = json.getJSONArray("results");
                int length = results.length();
                for(int i=0;i<length;i++) {
                    JSONObject c = results.getJSONObject(i);
                    title1 = c.getString("title");
                    searchResults.add(title1);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
                done();
        }
        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
