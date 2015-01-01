package fr.ecp.sio.superchat.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import fr.ecp.sio.superchat.ApiClient;
import fr.ecp.sio.superchat.model.User;
import fr.ecp.sio.superchat.model.UserFollow;

/**
 * Created by Michaël on 05/12/2014.
 */
public class UsersFollowLoader extends AsyncTaskLoader<List<UserFollow>> {

    private List<UserFollow> mResult;

    public UsersFollowLoader(Context context) {
        super(context);
    }

    @Override
    public List<UserFollow> loadInBackground() {
        try {
            return new ApiClient().getUserFollow(getContext());
        } catch (IOException e) {
            Log.e(UsersFollowLoader.class.getName(), "Failed to download users", e);
            return null;
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mResult != null){
            deliverResult(mResult);
        }
        if (takeContentChanged() || mResult == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    public void deliverResult(List<UserFollow> data) {
        mResult = data;
        super.deliverResult(data);
    }

}