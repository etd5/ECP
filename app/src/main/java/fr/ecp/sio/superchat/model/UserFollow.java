package fr.ecp.sio.superchat.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Michaël on 05/12/2014.
 */
public class UserFollow implements Parcelable {

    private String _id;
    private String handle;
    private String profilePicture;
    private String follower;
    private String following;

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getFollower() { return follower;  }

    public void setFollower() {  this.follower = "follower";  }

    public String getFollowing() { return following;  }

    public void setFollowing() { this.following = "following";  }

    public String getFollowText() {
        if (("following".equals(following)) && ("follower".equals(follower))) {
            return ("Follower et following");
        }
        if ("following".equals(following)) {
            return ("Following");
        }
        if ("follower".equals(follower)) {
            return ("Follower");
        }
        return "";
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(handle);
        dest.writeString(profilePicture);
        dest.writeString(following);
        dest.writeString(follower);

    }

    public static final Creator<UserFollow> CREATOR = new Creator<UserFollow>() {

        @Override
        public UserFollow createFromParcel(Parcel source) {
            UserFollow user = new UserFollow();
            user._id = source.readString();
            user.handle = source.readString();
            user.profilePicture = source.readString();
            user.following = source.readString();
            user.follower = source.readString();
            return user;
        }

        @Override
        public UserFollow[] newArray(int size) {
            return new UserFollow[0];
        }

    };

}