package com.hoavy.orapp.api;

import com.hoavy.orapp.models.Post;
import com.hoavy.orapp.models.Tokens;
import com.hoavy.orapp.models.User;
import com.hoavy.orapp.models.dtos.AvatarRequest;
import com.hoavy.orapp.models.dtos.CategoryRequest;
import com.hoavy.orapp.models.dtos.LoginRequest;
import com.hoavy.orapp.models.dtos.PostRequest;
import com.hoavy.orapp.models.dtos.RegisterRequest;
import com.hoavy.orapp.models.dtos.TokensRequest;
import com.hoavy.orapp.models.dtos.UpdateInfoRequest;
import com.hoavy.orapp.models.dtos.response.CategoriesResponse;
import com.hoavy.orapp.models.dtos.response.PostResponse;
import com.hoavy.orapp.models.dtos.response.PostsResponse;
import com.hoavy.orapp.models.dtos.response.SelectResponse;
import com.hoavy.orapp.models.dtos.response.SinglePostResponse;
import com.hoavy.orapp.models.dtos.response.UpdateCateResponse;
import com.hoavy.orapp.models.dtos.response.UpdateUserResponse;
import com.hoavy.orapp.models.dtos.response.UsersResponse;
import com.hoavy.orapp.repositories.PostsRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("Accounts/Login")
    Call<User> login(@Body LoginRequest loginRequest);

    @POST("Accounts/FreelancerRegister")
    Call<User> freelancerRegister(@Body RegisterRequest registerRequest);

    @POST("Accounts/CustomerRegister")
    Call<User> customerRegister(@Body RegisterRequest registerRequest);

    @POST("Accounts/RefreshToken")
    Call<Tokens> refreshTokens(@Body TokensRequest tokensRequest);

    @POST("Categories/AddCategory")
    Call<String> addCategory(@Body CategoryRequest categoryRequest);

    @GET("Categories/Categories")
    Call<CategoriesResponse> getCategories();

    @GET("Users/GetAll")
    Call<UsersResponse> getUsers();

    @GET("Posts/All")
    Call<PostsResponse> getPosts();

    @GET("Posts/GetHighestPricePosts?number=4")
    Call<PostsResponse> getHighestPricePosts();

    @GET("Posts/GetPostsByCategory")
    Call<PostsResponse> getPostsByCategory(@Query("id") String id);

    @GET("Posts/GetPost")
    Call<SinglePostResponse> getPost(@Query("id") String id);

    @GET("Posts/GetCustomerPosts")
    Call<PostsResponse> getCustomerPosts();

    @POST("Posts/CreatePost")
    Call<PostResponse> createPost(@Body PostRequest postRequest);

    @GET("Posts/GetCustomerProcessingPosts")
    Call<PostsResponse> getCustomerProcessingPosts();

    @GET("Posts/GetCustomerFinishedPosts")
    Call<PostsResponse> getCustomerFinishedPosts();

    @GET("Posts/GetFreelancerProcessingPosts")
    Call<PostsResponse> getFreelancerProcessingPosts();

    @PUT("Posts/UpdatePost")
    Call<Post> updatePost(@Query("id") String id, @Body PostRequest postRequest);

    @POST("Posts/SelectPost")
    Call<SelectResponse> selectPost(@Query("id") String id);

    @DELETE("Posts/UnSelectPost")
    Call<SelectResponse> unselectPost(@Query("postId") String id, @Query("freelancerId") String freelancerId);

    @PUT("Posts/ProcessPost")
    Call<SelectResponse> processPost(@Query("id") String id, @Query("freelancerId") String freelancerId);

    @PUT("Users/UpdateAvatar")
    Call<SelectResponse> updateAvatar(@Body AvatarRequest avatarRequest);

    @PUT("Users/UpdateUserInfo")
    Call<UpdateUserResponse> updateUserInfo(@Query("id") String id, @Body UpdateInfoRequest updateInfoRequest);

    @GET("Users/GetAllFreelancers")
    Call<UsersResponse> getAllFreelancers();

    @GET("Users/GetAllCustomers")
    Call<UsersResponse> getAllCustomers();

    @PUT("Categories/UpdateCategory")
    Call<UpdateCateResponse> updateCategory(@Query("id") String id, @Body CategoryRequest categoryRequest);

    @GET("Users/GetRecentRegister")
    Call<UsersResponse> getRecentRegister(@Query("number") int number);
}
