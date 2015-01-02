package fr.ecp.sio.superchat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import fr.ecp.sio.superchat.loaders.UsersFollowLoader;
import fr.ecp.sio.superchat.model.User;
import fr.ecp.sio.superchat.model.UserFollow;

/**
 * Created by Michaël on 05/12/2014.
 */
public class FollowFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<UserFollow>> {

    private static final int LOADER_FOLLOW_USERS = 1001;
    private static final int REQUEST_LOGIN_FOR_POST = 1;

    private UserFollowAdapter mListAdapter;
    private boolean mIsMasterDetailsMode;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.follows_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListAdapter = new UserFollowAdapter();
        setListAdapter(mListAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(LOADER_FOLLOW_USERS, null, this);
    }

    @Override
    public Loader<List<UserFollow>> onCreateLoader(int id, Bundle args) {
        return new UsersFollowLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<UserFollow>> loader, List<UserFollow> users) {
        mListAdapter.setUsers(users);
    }

    @Override
    public void onLoaderReset(Loader<List<UserFollow>> loader) { }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        final UserFollow user = mListAdapter.getItem(position);
        final String title, response;
        String masterUser = AccountManager.getUserHandle(getActivity());

        if (masterUser.toLowerCase().equals(user.getHandle().toLowerCase())) {
            Toast.makeText(getActivity(), "Vous ne pouvez pas vous suivre vous même !", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            if ("follower".equals(user.getFollower())) {
                title = "Ne plus suivre " + user.getHandle() + " ?";
                response = user.getHandle() + " n'est plus suivi.";
            } else {
                title = "Suivre " + user.getHandle() + " ?";
                response = user.getHandle() + " est maintenant suivi.";
            }
            builder
                    .setTitle("Suivre / ne plus suivre un utilisateur")
                    .setMessage(title)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (traiterSuivi(user)) {
                                Toast.makeText(getActivity(), response,
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "une erreur s'est produite pendant la mise à jour.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("No", null)						//Do nothing on no
                    .show();
        }


    }

    private boolean traiterSuivi(UserFollow follower) {
        String masterUser = AccountManager.getUserHandle(getActivity());

        if ("follower".equals(follower.getFollower())) {
            try {
                new ApiClient().delFollower(masterUser, follower.getHandle());
                getLoaderManager().restartLoader(LOADER_FOLLOW_USERS, null, this);
                mListAdapter.notifyDataSetChanged();
                return  true;
            } catch (IOException e) {
                Log.e(PostActivity.class.getName(), "Delfollower failed", e);
            }
        } else {
            try {
                new ApiClient().addFollower(masterUser, follower.getHandle());
                getLoaderManager().restartLoader(LOADER_FOLLOW_USERS, null, this);
                mListAdapter.notifyDataSetChanged();
                return true;
            } catch (IOException io) {
                Log.e(PostActivity.class.getName(), "Add follower failed", io);
            } catch (Exception ex) {
                Log.e(PostActivity.class.getName(), "Add follower failed", ex);
            }
        }
        return false;
    }

}