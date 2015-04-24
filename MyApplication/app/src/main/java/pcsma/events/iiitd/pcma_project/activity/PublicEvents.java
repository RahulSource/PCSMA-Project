package pcsma.events.iiitd.pcma_project.activity;


import java.util.ArrayList;

import pcsma.events.iiitd.pcma_project.populatingClass.Discussion;
import pcsma.events.iiitd.pcma_project.populatingClass.Events;
import pcsma.events.iiitd.pcma_project.populatingClass.Rsvp;
import pcsma.events.iiitd.pcma_project.populatingClass.User;
import pcsma.events.iiitd.pcma_project.populatingClass.UserResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;



public interface PublicEvents {

    @GET("/welcome")
    String printWelcome();

    @GET("/test")
    public Events test(@Query("event_id") String event_id);

    @POST("/createUserRSVPTable/{user_id}")
    public void createUserRSVPTable(@Body ArrayList<Rsvp> rsvpList,
                                    @Path("user_id") String user_id,
                                    Callback<Rsvp> callback);

    @POST("/updateUserTable")
    public void updateUserTable(@Body User user,
                                Callback<User> callback);

    @GET("/fetchProfile")
    public ArrayList<Events> fetchProfile(@Query("user_id") String user_id);

    @GET("/userSettingEvents")
    public ArrayList<Events> userSettingEvents(@Query("user_id") String user_id);

    @GET("/eventDescription")
    public Events eventDescription(@Query("user_id") String user_id,
                                   @Query("event_id") String event_id);

    @GET("/eventDiscussion")
    public ArrayList<Discussion> eventDiscussion(@Query("event_id") String event_id);

    @POST("/postDiscussion")
    public void postDiscussion(@Body Discussion discussion,
                               Callback<Discussion> callback);

    @POST("/calendarEvents")
    public ArrayList<Events> calendarEvents(@Query("date") String date);

    @POST("/updateUserResponse")
    public void updateUserResponse(@Body UserResponse userResponse,
                                   Callback<UserResponse> callback);


}