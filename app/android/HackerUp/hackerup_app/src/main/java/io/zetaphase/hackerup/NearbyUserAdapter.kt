package io.zetaphase.hackerup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import java.util.ArrayList

/**
 * Created by Dave Ho on 3/25/2017.
 */

class NearbyUserAdapter(private val context: Context, resource: Int, nearbyUsers: ArrayList<User>) : ArrayAdapter<User>(context, resource, nearbyUsers) {
    private var userList: List<User>? = null

    init {
        this.userList = nearbyUsers
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val user = userList!![position]

        val inflater = context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.user_row, null)

        val name = view.findViewById(R.id.userRowName) as TextView
        val distance = view.findViewById(R.id.userRowDistance) as TextView

        name.text = user.name
        distance.text = "" + user.distance + " meters"

        return view
    }

    fun updateNearbyUserList(userList: ArrayList<User>) {
        this.userList = userList
    }
}
