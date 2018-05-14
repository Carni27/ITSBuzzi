package it.itistulliobuzzi.mybuzzi.itsbuzzi.Fragment;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;

/**
 * Questa &egrave; una sottoclasse dalla classe {@link ListFragment}
 * e si occupa di mostrare la lista dei Tweet
 * del Buzzi
 */

public class FragmentTwitter extends ListFragment {

    private MainActivity mainActivity;

    public FragmentTwitter() {
    }

    @SuppressLint("ValidFragment")
    public FragmentTwitter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("TullioBuzzi1")
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(mainActivity)
                .setTimeline(userTimeline)
                .build();
        setListAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_twitter, container, false);
        mainActivity.setTitle("Twitter");
        return view;
    }
}
