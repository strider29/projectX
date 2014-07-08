package droiders.dark.seenbeenread.menufragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import droiders.dark.seenbeenread.R;

/**
 * Created by Strider on 7/7/2014.
 */
public class MoviesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        return rootView;
    }

}
