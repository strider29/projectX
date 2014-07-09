package droiders.dark.seenbeenread.activities;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

import droiders.dark.seenbeenread.R;
import droiders.dark.seenbeenread.menufragments.BooksFragment;
import droiders.dark.seenbeenread.menufragments.MoviesFragment;
import droiders.dark.seenbeenread.menufragments.PlacesFragment;
import droiders.dark.seenbeenread.misc.Search;
import droiders.dark.seenbeenread.navigationdrawer.NavigationDrawerFragment;


public class BaseActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, SearchView.OnQueryTextListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle="Movies";
    public static String movieJson;
    Fragment menuFragment=null;
    public static ArrayList<String> searchResults;
    public static ListView searchList;
    private static Context context;
    private static boolean isListVisible=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        searchList = (ListView)findViewById(R.id.search_results);
        searchList.setVisibility(View.GONE);
        context=this;
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }
   @Override
   public void onBackPressed()
   {
        if(isListVisible)
        {
            searchList.setVisibility(View.GONE);
            isListVisible=false;
        }
       else
            super.onBackPressed();
   }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        ActionBar actionBar = getActionBar();
        setTitle(position+1);
        actionBar.setTitle(mTitle);

        if(position==0)
            menuFragment=new MoviesFragment();
        else if(position==1)
            menuFragment=new BooksFragment();
        else if(position==2)
            menuFragment=new PlacesFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, menuFragment)
                .commit();
    }

    public void setTitle(int number) {
        switch (number) {
            case 1:
                mTitle =  "Movies";
                break;
            case 2:
                mTitle = "Books";
                break;
            case 3:
                mTitle = "Places";
                break;
            case 4:
                mTitle = "Friends";
                break;
            case 5:
                mTitle = "Connect";
                break;
            case 6:
                mTitle = "About us";
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    public static void setSearchResults(ArrayList<String> results)
    {
            searchResults=results;
        ArrayAdapter adapter = new ArrayAdapter(context,
                android.R.layout.simple_list_item_1, searchResults);
        searchList.setAdapter(adapter);
        searchList.setVisibility(View.VISIBLE);
        isListVisible=true;
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchList.setVisibility(View.GONE);
                isListVisible=false;

            }
        });



    }

    //comment
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) //show the action bar icons when the drawer is closed
        {
            getMenuInflater().inflate(R.menu.base, menu);
            restoreActionBar();
              final MenuItem searchWidget =  menu.findItem(R.id.action_search);
              final SearchView searchView =(SearchView)searchWidget.getActionView();
              searchView.setOnQueryTextListener(this);
              searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                  @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                  @Override
                  public void onFocusChange(View v, boolean hasFocus) {
                      int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                      if (currentapiVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                          searchWidget.collapseActionView();
                      }

                      searchView.setQuery("", false);
                  }
              });
            return true;
        }
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

    @Override
    public boolean onQueryTextSubmit(String query) {
//        Intent intent = new Intent(this, ResultActivity.class);
//        intent.putExtra("search",query);
//        startActivity(intent);
        Search search = new Search(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

}
