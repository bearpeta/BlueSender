package pren1.team30.bluesender;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Alessandro on 13.11.2014.
 */
public class DeviceListFragment extends ListFragment {

    private DeviceListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = DeviceListAdapter.getInstance(getActivity().getApplicationContext(), R.layout.liestview_item);
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        getListView().setAdapter(adapter);
        return rootView;
    }


}
