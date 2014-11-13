package pren1.team30.bluesender;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by Alessandro on 13.11.2014.
 */
public class DeviceListAdapter extends ArrayAdapter<BluetoothDevice>  {

    Context context;
    int layoutResourceId;
    BluetoothDevice data[] = null;
    private static DeviceListAdapter instance;


    public static DeviceListAdapter getInstance (Context context, int layoutResourceId) {
        if (DeviceListAdapter.instance == null) {
            DeviceListAdapter.instance = new DeviceListAdapter (context, layoutResourceId);
        }
        return DeviceListAdapter.instance;
    }

    private DeviceListAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        this.layoutResourceId = layoutResourceId;
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DeviceHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new DeviceHolder();
            holder.txtName = (TextView)row.findViewById(R.id.textViewName);
            holder.txtAddress = (TextView)row.findViewById(R.id.textViewAddress);

            row.setTag(holder);
        }
        else
        {
            holder = (DeviceHolder)row.getTag();
        }

        BluetoothDevice device = data[position];
        holder.txtName.setText(device.getName());
        holder.txtAddress.setText(device.getAddress());

        return row;
    }

    static class DeviceHolder
    {
        TextView txtName;
        TextView txtAddress;
    }
}
