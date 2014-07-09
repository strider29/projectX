package droiders.dark.seenbeenread.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import droiders.dark.seenbeenread.R;
import droiders.dark.seenbeenread.misc.JsonParser;


public class ResultActivity extends Activity {
    String query;
    TextView title;
    ListView searchList;
    ArrayList<String> searchResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent i=getIntent();
        query=i.getStringExtra("search");
        title= (TextView)findViewById(R.id.title);
        searchList = (ListView)findViewById(R.id.title_list);
        searchResults=new ArrayList<String>();
        getMovieDetails details = new getMovieDetails();
        details.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setValues()
    {
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, searchResults);
        searchList.setAdapter(adapter);


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
            setValues();

        }
        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
