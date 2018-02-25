package com.djavid.schoolapp.rest;

import android.text.format.DateFormat;

import com.djavid.schoolapp.model.event_comments.EventComment;
import com.djavid.schoolapp.model.events.Event;
import com.djavid.schoolapp.model.groups.Group;
import com.djavid.schoolapp.model.schedule.Schedule;
import com.djavid.schoolapp.model.schedule.ScheduleLesson;
import com.djavid.schoolapp.model.schedule.ScheduleSubject;
import com.djavid.schoolapp.model.users.TokenResponse;
import com.djavid.schoolapp.model.users.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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
import retrofit2.http.Query;

/**
 * @author Andrei Kolomiets
 */
public interface Api {

    String ENDPOINT = "https://school-1329.herokuapp.com/api/";

    String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    String DATE_STRING_FORMAT = "%04d-%02d-%02dT%02d:%02d:%02d";

    // headers

    String Auth = "Authorization";
    String ContentType = "content-type";

    // users

    String USERS = ENDPOINT + "users";

    @FormUrlEncoded
    @POST(USERS + "/create_code")
    Single<Map<String, String>> createCode(@Field("expiration_date") String expiration_date,
                                           @Field("level") int level);

    @FormUrlEncoded
    @POST(USERS + "/check_code")
    Single<Map<String, String>> checkCode(@Field("code") String code);

    @FormUrlEncoded
    @POST(USERS + "/register")
    Single<TokenResponse> register(@Field("username") String nickname, @Field("password") String userID,
                                   @Field("level") int level, @Field("code") String code);

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

    @GET(GROUPS + "/{id}")
    Single<Group> getGroup(@Header(Auth) String auth, @Path("id") long id);

    @FormUrlEncoded
    @PUT(GROUPS + "/{id}")
    Single<Group> updateGroup(@Header(Auth) String auth, @Path("id") long id, @Field("title") String title);

    @DELETE(GROUPS + "/{id}")
    Single<Map<String, String>> deleteGroup(@Header(Auth) String auth, @Path("id") long id);

    @POST(GROUPS + "/{id}/add_user")
    Single<Map<String, String>> joinGroup(@Header(Auth) String auth, @Path("id") long groupId);

    @POST(GROUPS + "/{id}/remove_user")
    Single<Map<String, String>> leaveGroup(@Header(Auth) String auth, @Path("id") long groupId);

    @GET(GROUPS + "/{id}/users")
    Single<List<User>> getGroupParticipants(@Header(Auth) String auth, @Path("id") long groupId);

    // events

    String EVENTS = ENDPOINT + "events";

    @GET(EVENTS)
    Single<List<Event>> getAllEvents(@Header(Auth) String auth);

    @GET(EVENTS + "/user_entered_events")
    Single<List<Event>> getEnteredEvents(@Header(Auth) String auth);

    @GET(EVENTS + "/user_created_events")
    Single<List<Event>> getCreatedEvents(@Header(Auth) String auth);

    @FormUrlEncoded
    @POST(EVENTS)
    Single<Event> createEvent(@Header(Auth) String auth, @Field("title") String title,
                              @Field("place") String place, @Field("description") String description,
                              @Field("participation_groups") List<String> participationGroups,
                              @Field("start_date") String startDate, @Field("end_date") String endDate);

    @GET(EVENTS + "/{id}")
    Single<Event> getEvent(@Header(Auth) String auth, @Path("id") long id);

    @FormUrlEncoded
    @PUT(EVENTS + "/{id}")
    Single<Event> updateEvent(@Header(Auth) String auth, @Path("id") long eventId,
                              @Field("title") String title, @Field("place") String place,
                              @Field("description") String description,
                              @Field("participation_groups") List<Long> participationGroups,
                              @Field("start_date") String startDate, @Field("end_date") String endDate);

    @DELETE(EVENTS + "/{id}")
    Single<Map<String, String>> deleteEvent(@Header(Auth) String auth, @Path("id") long eventId);

    // Event Comments

    String COMMENTS = ENDPOINT + "event_comments";

    @GET(COMMENTS)
    Single<List<EventComment>> getComments(@Header(Auth) String auth, @Query("id") long eventId);

    @FormUrlEncoded
    @POST(COMMENTS)
    Single<EventComment> createComment(@Header(Auth) String auth, @Field("text") String text, @Field("event") long eventId);

    @DELETE(COMMENTS + "/{id}")
    Single<Map<String, String>> deleteComment(@Header(Auth) String auth, @Path("id") long commentId);

    //schedule subjects

    String SUBJECTS = ENDPOINT + "schedule_subjects";

    @GET(SUBJECTS)
    Single<List<ScheduleSubject>> getAllSubjects(@Header(Auth) String auth);

    @FormUrlEncoded
    @POST(SUBJECTS)
    Single<ScheduleSubject> createSubject(@Header(Auth) String auth, @Field("title") String title);

    @FormUrlEncoded
    @PUT(SUBJECTS + "/{id}")
    Single<ScheduleSubject> updateSubject(@Header(Auth) String auth, @Path("id") long subjectId,
                                          @Field("title") String title);

    @DELETE(SUBJECTS + "/{id}")
    Single<Map<String, String>> deleteSubject(@Header(Auth) String auth, @Path("id") long subjectId);

    //schedule lessons

    String LESSONS = ENDPOINT + "schedule_lessons";

    @GET(LESSONS + "/user_schedule")
    Single<Schedule> getAllLessons(@Header(Auth) String auth);

    @FormUrlEncoded
    @POST(LESSONS)
    Single<ScheduleLesson> createLesson(@Header(Auth) String auth, @Field("start_time") String start_time,
                                        @Field("weekday") int weekday, @Field("groups") int[] groups,
                                        @Field("subject") int subject, @Field("place") String place);

    @FormUrlEncoded
    @PUT(LESSONS + "/{id}")
    Single<ScheduleLesson> updateLesson(@Header(Auth) String auth, @Path("id") long lessonId,
                                        @Field("start_time") String start_time,
                                        @Field("weekday") int weekday, @Field("groups") int[] groups,
                                        @Field("subject") int subject, @Field("place") String place);

    @DELETE(LESSONS + "/{id}")
    Single<Map<String, String>> deleteLesson(@Header(Auth) String auth, @Path("id") long lessonId);

    // helpers

    static String Date(Calendar date) {
        return new SimpleDateFormat(Api.DATE_FORMAT).format(date.getTime());
    }

    static String LocalizedDate(Calendar date) {
        return new SimpleDateFormat(DateFormat.getBestDateTimePattern(Locale.getDefault(), Api.DATE_FORMAT))
                .format(date.getTime());
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
