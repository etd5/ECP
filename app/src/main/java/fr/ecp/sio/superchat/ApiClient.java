package fr.ecp.sio.superchat;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.ecp.sio.superchat.model.Tweet;
import fr.ecp.sio.superchat.model.User;

/**
 * Created by Michaël on 12/12/2014.
 */
public class ApiClient {

    //private static final String API_BASE = "http://hackndo.com:5667/mongo/";
    private static final String API_BASE = "http://vps99972.ovh.net:5000/";

    public String login(String handle, String password) throws IOException {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(API_BASE + "sessions");

        try {
            List<NameValuePair> nameValuePair  = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("handle", handle));
            nameValuePair.add(new BasicNameValuePair("password", password));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
            HttpResponse response = httpclient.execute(httppost);
            return response.toString();

        } catch (ClientProtocolException e) {
            return "ClientProtocolException";
        } catch (IOException e) {
            return "IOException";
        }
    }

    public List<User> getUsers() throws IOException {
        Log.d("ApiClient_getUsers (URL): " + new URL(API_BASE + "users").toString(), "ok");
        InputStream stream = new URL(API_BASE + "users").openStream();
        String response = IOUtils.toString(stream);
        return Arrays.asList(new Gson().fromJson(response, User[].class));
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

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(API_BASE + " handle/tweets");

        try {
            List<NameValuePair> nameValuePair  = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("handle", handle));
            nameValuePair.add(new BasicNameValuePair("content", content));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
            HttpResponse response = httpclient.execute(httppost);
            return response.toString();

        } catch (ClientProtocolException e) {
            return "ClientProtocolException";
        } catch (IOException e) {
            return "IOException";
        }

    }
}