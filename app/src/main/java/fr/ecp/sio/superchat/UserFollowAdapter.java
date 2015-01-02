package fr.ecp.sio.superchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.ecp.sio.superchat.model.User;
import fr.ecp.sio.superchat.model.UserFollow;

/**
 * Created by Michaël on 05/12/2014.
 */
public class UserFollowAdapter extends BaseAdapter {

    private List<UserFollow> mUsers;

    public List<UserFollow> getUsers() {
        return mUsers;
    }

    public void setUsers(List<UserFollow> users) {
        mUsers = users;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mUsers != null ? mUsers.size() : 0;
    }

    @Override
    public UserFollow getItem(int position) {
        return mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId().hashCode();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_item, parent, false);
        }
        String connectedUser = AccountManager.getUserHandle(parent.getContext());
        UserFollow user = getItem(position);
        TextView handleView = (TextView) convertView.findViewById(R.id.handle);
        if (connectedUser.toLowerCase().equals(user.getHandle().toLowerCase())) {
            handleView.setText(user.getHandle() + " (Moi)");
        } else {
            handleView.setText(user.getHandle());
        }

        TextView followView = (TextView) convertView.findViewById(R.id.followStatus);
        followView.setText(user.getFollowText());
        ImageView profilePictureView = (ImageView) convertView.findViewById(R.id.profile_picture);
        Picasso.with(convertView.getContext()).load(user.getProfilePicture()).into(profilePictureView);
        return convertView;
    }

}
