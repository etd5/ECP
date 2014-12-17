package fr.ecp.sio.superchat.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by christophe on 12/12/14.
 */
public class AccountManager {
    //
    private static final String PREF_API_TOKEN = "apiToken";

    // Est-ce que le token existe ?
    public static boolean isConnected(Context context) {
        return getUserToken(context) != null;
    }

    // Renvoie le token du user
    public static String getUserToken(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(PREF_API_TOKEN, null);
    }

    // On sauvegarde le token
    public static  void saveUserToken(Context context, String token) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit()
                .putString(PREF_API_TOKEN, token)
                .apply();
    }

    // En cas de déconnection, on supprime le token
    public static void clearUserToken (Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit()
                .remove(PREF_API_TOKEN)
                .apply();
    }
}
