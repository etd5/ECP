package fr.ecp.sio.superchat;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ecp.sio.superchat.model.Tweet;
import fr.ecp.sio.superchat.model.User;
import fr.ecp.sio.superchat.model.UserFollow;

/**
 * Created by Michaël on 12/12/2014.
 */
public class ApiClient {

    //private static final String API_BASE = "http://hackndo.com:5667/mongo/";
    private static final String API_BASE = "http://vps99972.ovh.net:5660/";
    //private static final String API_BASE = "http://vps99972.ovh.net:8080/";
    //private static final String API_BASE = "http://127.0.0.1:5000/";


    public String login(String handle, String password) throws IOException {

        HttpPost httppost = new HttpPost(API_BASE + "sessions");
        String response;
        //HttpResponse response;

        try {
            List<NameValuePair> nameValuePair  = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("handle", handle));
            nameValuePair.add(new BasicNameValuePair("password", password));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
            CallHttpPost callHttpPost = new CallHttpPost(httppost);
            Log.d("Debug_Appel callHttpPost", "ok");
            new Thread(callHttpPost).start();
            response = callHttpPost.getValue();
            //response = httpclient.execute(httppost);

//        } catch (ClientProtocolException e) {
//            return "ClientProtocolException";
        } catch (IOException e) {
            return "IOException";
        }
        return (response == null) ? "" : response.toString();
    }

    public List<User> getUsers() throws IOException {
        Log.d("ApiClient_getUsers (URL): " + new URL(API_BASE + "users").toString(), "ok");
        InputStream stream = new URL(API_BASE + "users").openStream();
        String response = IOUtils.toString(stream);
        return Arrays.asList(new Gson().fromJson(response, User[].class));
    }

    public List<UserFollow> getUserFollow(Context context) throws IOException {
        String handle = AccountManager.getUserHandle(context);
        Log.d("ApiClient_getUserFollow (URL): " + new URL(API_BASE + "users").toString(), "ok");
        InputStream streamUsers = new URL(API_BASE + "users").openStream();
        Log.d("ApiClient_getUserFollow (URL): " + new URL(API_BASE + handle.replaceAll(" ", "%20") + "/followers").toString(), "ok");
        InputStream streamFollowers = new URL(API_BASE + handle.replaceAll(" ", "%20") + "/followers").openStream();
        Log.d("ApiClient_getUserFollow (URL): " + new URL(API_BASE + handle.replaceAll(" ", "%20") + "/followings").toString(), "ok");
        InputStream streamFollowings = new URL(API_BASE + handle.replaceAll(" ", "%20") + "/followings").openStream();

        String responseUsers = IOUtils.toString(streamUsers);
        String responseFollowers = IOUtils.toString(streamFollowers);
        String responseFollowings = IOUtils.toString(streamFollowings);

        List<UserFollow> listUsers = Arrays.asList(new Gson().fromJson(responseUsers, UserFollow[].class));
        List<User> listFollowers = Arrays.asList(new Gson().fromJson(responseFollowers, User[].class));
        List<User> listFollowings = Arrays.asList(new Gson().fromJson(responseFollowings, User[].class));

        for (UserFollow uf : listUsers) {
            for (User ufer : listFollowers) {
                if (uf.getId().equals(ufer.getId())) {
                    uf.setFollower();
                }
            }
            for (User ufing : listFollowings) {
                if (uf.getId().equals(ufing.getId())) {
                    uf.setFollowing();
                }
            }
        }

        return listUsers;
    }

    public List<Tweet> getUserTweets(String handle) throws IOException {
        Log.d("ApiClient_getUsers_Tweets (URL): " + new URL(API_BASE + handle + "/tweets").toString(), "ok");
        URL url = new URL(API_BASE + handle.replaceAll(" ", "%20") + "/tweets");
        Log.d("ApiClient_getUsers_Contenu url: " + url.toString(), "ok");
        InputStream stream = url.openStream();
        String response = IOUtils.toString(stream);
        Log.d("ApiClient_getUsers_Tweets" + response, "ok");
        return Arrays.asList(new Gson().fromJson(response, Tweet[].class));
    }

    public String postTweet(String handle, String content) throws IOException {

        HttpPost httppost = new HttpPost(API_BASE + "handle/tweets");
        //HttpResponse response;
        String response;

        try {
            List<NameValuePair> nameValuePair  = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("handle", handle));
            nameValuePair.add(new BasicNameValuePair("content", content));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
            CallHttpPost callHttpPost = new CallHttpPost(httppost);
            Log.d("Debug_Appel callHttpPost", "ok");
            new Thread(callHttpPost).start();
            response = callHttpPost.getValue();
            //response = httpclient.execute(httppost);

//        } catch (ClientProtocolException e) {
//            return "ClientProtocolException";
        } catch (IOException e) {
            return "IOException";
        }

        return (response == null) ? "" : response.toString();

    }

    public String addFollower(String handle, String follower) throws IOException {
        return updateFollower(handle, follower, "POST");
    }

    public String delFollower(String handle, String follower) throws IOException {
        return updateFollower(handle, follower, "DELETE");
    }

    public String updateFollower(String handle, String follower, String method) throws IOException {

        HttpPost httppost = new HttpPost(API_BASE + "handle/followers");
        //HttpResponse response;
        String response;

        try {
            List<NameValuePair> nameValuePair  = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("handle", handle));
            nameValuePair.add(new BasicNameValuePair("followers", follower));
            nameValuePair.add(new BasicNameValuePair("method", method));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
            CallHttpPost callHttpPost = new CallHttpPost(httppost);
            Log.d("Debug_Appel callHttpPost", "ok");
            new Thread(callHttpPost).start();
            response = callHttpPost.getValue();
            //response = httpclient.execute(httppost);

//        } catch (ClientProtocolException e) {
//            return "ClientProtocolException";
        } catch (IOException e) {
            return "IOException";
        }

        return (response == null) ? "" : response.toString();
    }

    public class CallHttpPost implements Runnable {
        private HttpPost mHttpPost;
        private String mRetour;

        public void setRetour(String retour) {
            mRetour = retour;
        }
        public CallHttpPost(HttpPost httpPost) {
            mHttpPost = httpPost;
        }

        @Override
        public void run() {
            HttpResponse response;
            Log.d("Debug_On est dans le run", "ok");
            AndroidHttpClient httpclient = AndroidHttpClient.newInstance("Android");
            try {
                setRetour(httpclient.execute(mHttpPost).toString());
            } catch (ClientProtocolException e) {
                setRetour("ClientProtocolException");
            } catch (IOException e) {
                setRetour("IOException");
            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }
            }
        }

        public String getValue() {
            return mRetour;
        }
    }
}