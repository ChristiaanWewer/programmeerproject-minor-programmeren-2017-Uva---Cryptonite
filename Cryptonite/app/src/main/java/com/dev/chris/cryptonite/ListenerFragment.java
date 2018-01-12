package com.dev.chris.cryptonite;


    import android.database.Cursor;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.os.Bundle;
    import android.support.v4.app.ListFragment;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;

    import org.json.JSONArray;
    import com.loopj.android.http.*;
    import java.util.ArrayList;
    import cz.msebera.android.httpclient.Header;


/**
 */
public class ListenerFragment extends ListFragment {


    public ListenerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listener, container, false);
    }

}
