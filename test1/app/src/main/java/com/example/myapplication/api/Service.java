package com.example.myapplication.api;

import com.example.myapplication.Course;
import com.example.myapplication.User;
import com.example.myapplication.UserCourse;
import com.example.myapplication.material;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by frank on 12/16/16.
 */

public interface Service {
    @GET("/elearn/users/")
    Call<List<User>> getUsers();
    @GET("/elearn/courses/")
    Call<List<Course>> getCourses();
    @GET("/elearn/usercourses/")
    Call<List<UserCourse>> getUserCourses();
    @GET("/elearn/courses/001/materials/")
    Call<List<material>> getMaterial();

    @GET("/elearn/addUser/")
    Call<User> putUser(@Query("name") String username, @Query("password") String password, @Query("email") String email,@Query("mobile") String mobile,@Query("level") String level);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/elearn/addUser/")
    Call<User> pushUser(@Body User user);


    @GET("/materials/{mid}/media/")
    Call<ResponseBody> getVideo(@Query("mid") String mid);
    @GET("/elearn/materials/{mid}/file/")
    Call<ResponseBody> getPicture(@Query("mid") String mid);

}


