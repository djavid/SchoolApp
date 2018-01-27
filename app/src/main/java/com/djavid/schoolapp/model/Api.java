package com.djavid.schoolapp.model;

import com.djavid.schoolapp.model.dto.groups.Group;

import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import retrofit2.http.DELETE;
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

    String ENDPOINT = "https://school-1329-server.appspot.com/api";

    // headers

    String Auth = "Auth";

    // users

    String USERS = ENDPOINT + "/users";

    @GET(USERS + "/create_code")
    Single<String> createCode(@Query("expiration_date") Date expiration_date, @Query("level") int level);

    @GET(USERS + "/check_code")
    Single<String> checkCode(@Query("code") String code);

    @GET(USERS + "/register")
    Single<String> register(@Query("username") String nickname, @Query("password") String userID, @Query("level") int level, @Query("code") String code);

    @GET(USERS + "/login")
    Single<String> login(@Query("username") String nickname, @Query("password") String userID);

    // groups

    String GROUPS = ENDPOINT + "/groups";

    @GET(GROUPS)
    Single<List<Group>> getAllGroups(@Header(Auth) String auth);

    @POST(GROUPS)
    Single<Group> createGroup(@Header(Auth) String auth, @Query("title") String title);

    @PUT(GROUPS + "/{id}")
    Single<Group> updateGroup(@Header(Auth) String auth, @Path("id") String id, @Query("title") String title);

    @DELETE(GROUPS + "/{id}")
    Single<String> deleteGroup(@Header(Auth) String auth, @Path("id") String id);
}
