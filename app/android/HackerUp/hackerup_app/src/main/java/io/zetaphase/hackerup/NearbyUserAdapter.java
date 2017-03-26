package io.zetaphase.hackerup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dave Ho on 3/25/2017.
 */

public class NearbyUserAdapter extends ArrayAdapter<User>{
    private Context context;
    private List<User> userList;

    public NearbyUserAdapter(Context context, int resource, ArrayList<User> nearbyUsers){
        super(context, resource, nearbyUsers);
        this.context = context;
        this.userList = nearbyUsers;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        User user = userList.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.user_row, null);

        TextView name = (TextView) view.findViewById(R.id.userRowName);
        TextView distance = (TextView) view.findViewById(R.id.userRowDistance);

        name.setText(user.getName());
        distance.setText(""+user.getDistance()+" meters");

        return view;
    }

    public void updateNearbyUserList(ArrayList<User> userList) { this.userList = userList; }
}
