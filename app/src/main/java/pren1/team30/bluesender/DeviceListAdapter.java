package pren1.team30.bluesender;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by Alessandro on 13.11.2014.
 */
public class DeviceListAdapter extends ArrayAdapter<BluetoothDevice> {

    Context context;
    int layoutResourceId;
    BluetoothDevice data[] = null;

    public DeviceListAdapter(Context context, int layoutResourceId, BluetoothDevice[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
}
