package com.djavid.schoolapp.model;

import com.djavid.schoolapp.model.dto.events.Event;
import com.djavid.schoolapp.model.dto.users.TokenResponse;
import com.djavid.schoolapp.model.dto.groups.Group;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * @author Andrei Kolomiets
 */
public interface Api {

    String ENDPOINT = "https://school-1329.herokuapp.com/api/";

    String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    String DATE_STRING_FORMAT = "%04d-%02d-%02dT%02d:%02d:%02d";

    // headers

    String Auth = "Authorization";

    // users

    String USERS = ENDPOINT + "users";

    @FormUrlEncoded
    @POST(USERS + "/create_code")
    Single<Map<String, String>> createCode(@Field("expiration_date") String expiration_date, @Field("level") int level);

    @FormUrlEncoded
    @POST(USERS + "/check_code")
    Single<Map<String, String>> checkCode(@Field("code") String code);

    @FormUrlEncoded
    @POST(USERS + "/register")
    Single<TokenResponse> register(@Field("username") String nickname, @Field("password") String userID, @Field("level") int level, @Field("code") String code);

    @FormUrlEncoded
    @POST(USERS + "/login")
    Single<Map<String, String>> login(@Field("username") String nickname, @Field("password") String userID);

    // groups

    String GROUPS = ENDPOINT + "groups";

    @GET(GROUPS)
    Single<List<Group>> getAllGroups(@Header(Auth) String auth);

    @GET(GROUPS + "/user_groups")
    Single<List<Group>> getMyGroups(@Header(Auth) String auth);

    @FormUrlEncoded
    @POST(GROUPS)
    Single<Group> createGroup(@Header(Auth) String auth, @Field("title") String title);

    @FormUrlEncoded
    @PUT(GROUPS + "/{id}")
    Single<Group> updateGroup(@Header(Auth) String auth, @Path("id") long id, @Field("title") String title);

    @DELETE(GROUPS + "/{id}")
    Single<Map<String, String>> deleteGroup(@Header(Auth) String auth, @Path("id") long id);

    @POST(GROUPS + "/{id}/add_user")
    Single<Map<String, String>> joinGroup(@Header(Auth) String auth, @Path("id") long groupId);

    @POST(GROUPS + "/{id}/remove_user")
    Single<Map<String, String>> leaveGroup(@Header(Auth) String auth, @Path("id") long groupId);

    // events

    String EVENTS = ENDPOINT + "events";

    @GET(EVENTS)
    Single<List<Event>> getAllEvents(@Header(Auth) String auth);

    @GET(EVENTS + "/user_events")
    Single<List<Event>> getMyEvents(@Header(Auth) String auth);

    @FormUrlEncoded
    @POST(EVENTS)
    Single<Event> createEvent(@Header(Auth) String auth, @Field("title") String title, @Field("place") String place, @Field("description") String description, @Field("participation_groups") List<String> participationGroups, @Field("start_date") String startDate, @Field("end_date") String endDate);

    @FormUrlEncoded
    @PUT(EVENTS + "/{id}")
    Single<Event> updateEvent(@Header(Auth) String auth, @Path("id") long eventId, @Field("title") String title, @Field("place") String place, @Field("description") String description, @Field("participation_groups") List<String> participationGroups, @Field("start_date") String startDate, @Field("end_date") String endDate);

    @DELETE(EVENTS + "/{id}")
    Single<Map<String, String>> deleteEvent(@Header(Auth) String auth, @Path("id") long eventId);


    // helpers

    static String Date(Calendar date) {
        return new SimpleDateFormat(Api.DATE_FORMAT).format(date.getTime());
    }

    static Calendar ParseDate(String date) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(
                    new SimpleDateFormat(Api.DATE_FORMAT).parse(date));
            return calendar;
        } catch (ParseException e) {
            return Calendar.getInstance();
        }
    }
}
