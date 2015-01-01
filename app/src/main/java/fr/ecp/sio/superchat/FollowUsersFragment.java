package fr.ecp.sio.superchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import fr.ecp.sio.superchat.model.User;

/**
 * Created by Michaël on 12/12/2014.
 */
public class FollowUsersFragment extends DialogFragment implements DialogInterface.OnShowListener {

    private User mUser;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.follow_user_fragment, null);

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.Confirm_follow_unfollow)
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        dialog.setOnShowListener(this);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle arg = this.getArguments();
        mUser = (User)arg.get("user");
    }

        @Override
    public void onShow(DialogInterface dialog) {
        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm(getActivity().getApplicationContext());
            }
        });
    }

    private void confirm(Context context) {

        String handle = AccountManager.getUserHandle(context);

/*        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                try {
                    return new ApiClient().login(handle, password);
                } catch (IOException e) {
                    Log.e(FollowUsersFragment.class.getName(), "Login failed", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String token) {
                if (token != null) {
                    Fragment target = getTargetFragment();
                    if (target != null) {
                        target.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                        String test = getActivity().getPackageName();
                    }
                    AccountManager.login(getActivity(), token, handle);
                    dismiss();
                    Toast.makeText(getActivity(), R.string.login_success, Toast.LENGTH_SHORT).show();
                    ((UsersActivity) getActivity()).loginSuccessfull();
                } else {
                    Toast.makeText(getActivity(), R.string.login_error, Toast.LENGTH_SHORT).show();
                }
            }

        }.execute();
*/

    }
}