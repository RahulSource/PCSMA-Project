package pcsma.events.iiitd.pcma_project.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;

import java.util.Arrays;

import pcsma.events.iiitd.pcma_project.R;

public class MainFragment extends Fragment {

    public MainFragment() {

    }



    private UiLifecycleHelper uiHelper;
    ProfilePictureView userPic;
    LoginButton authButton;
    ProgressDialog progress;
    String fbUserID = "";
    String fbUserName = "";
    public Session session;

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };



    private Session.StatusCallback statusCallback =
            new SessionStatusCallback();


    private void onClickLogin() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this)
                    .setPermissions(Arrays.asList("public_profile", "user_friends","user_events"))
                    .setCallback(statusCallback));
        } else {
            Session.openActiveSession(getActivity(), this, true, statusCallback);
        }
    }

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            // Respond to session state changes, ex: updating the view

            onSessionStateChange(session, state, exception);
        }
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);


    }


    public static Boolean isNetworkAvailable(Activity activity) {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main, container, false);

        authButton = (LoginButton) view.findViewById(R.id.authButton);
        authButton.setFragment(this);
        authButton.setReadPermissions(Arrays.asList("public_profile", "user_friends","user_events"));

        return view;
        /*

        if (*//*isNetworkAvailable(getActivity())*//*true) {
            View view = inflater.inflate(R.layout.activity_main, container, false);
            authButton = (LoginButton) view.findViewById(R.id.authButton);
            authButton.setFragment(this);
            authButton.setReadPermissions(Arrays.asList("public_profile", "user_friends","user_events"));

            return view;
        } else {

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity().getBaseContext());

            alert.setTitle("TITLE");

            return null;
        }*/

    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {

        if (state.isOpened()) {
            System.out.println("Logged In");
            authButton.setVisibility(View.INVISIBLE);
            authButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {

                @Override
                public void onUserInfoFetched(GraphUser user) {

                    System.out.println("USER INFO FETCH");
                    MainFragment.this.fbUserID = user.getId();
                    MainFragment.this.fbUserName = user.getName();

                    FBGetUserInfo info = new FBGetUserInfo();
                    info.execute();

                }
            });

        } else if (state.isClosed()) {
            authButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Session session = Session.getActiveSession();
        if (session != null && (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }

        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    private String buildUserInfoDisplay(GraphUser user) {

        System.out.println("buildUserInfoDisplay");
        StringBuilder userInfo = new StringBuilder("");

        userInfo.append(String.format("%s",	user.getId()));
        userInfo.append(String.format("%s", user.getName()));

        return userInfo.toString();
    }

    private class FBGetUserInfo extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            Request.newMeRequest(session, new Request.GraphUserCallback() {

                @Override
                public void onCompleted(GraphUser user, Response response) {
                    System.out.println("onCompleted");
                    if (user != null) {
                        System.out.println("inside");
                        buildUserInfoDisplay(user);
                    }
                }

            }).executeAndWait();



            new Request(
                    session,
                    "/search?type=event&q=IIIT%20Delhi",
                    null,
                    HttpMethod.GET,
                    new Request.Callback() {
                        public void onCompleted(Response response) {
                            String res=response.toString();
                            System.out.println("123 : "+res);
                        }
                    }
            ).executeAsync();

            return fbUserID;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
//search?type=event&q=IIIT%20Delhi



        //    Intent intent = new Intent(getActivity(),ProfilePage.class);
            Intent intent = new Intent(getActivity(),MainMenu.class);
            intent.putExtra("FB_USER_ID", fbUserID);
            intent.putExtra("FB_USER_NAME", fbUserName);
            startActivity(intent);
        }
    }

}