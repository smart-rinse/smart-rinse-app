package labs.nusantara.smartrinse.services.api

import labs.nusantara.smartrinse.services.response.*
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confPassword") confPassword: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("article")
    fun getArticle(
        @Header("Authorization") token: String
    ): Call<ArticleResponse>

    @GET("users/{userId}")
    fun getUserDetail(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): Call<UserDetailResponse>

    @FormUrlEncoded
    @PUT("editPassword/{userId}")
    fun putUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Field("currentPassword") oldPassword: String,
        @Field("newPassword") newPassword: String,
        @Field("confirmPassword") confirmPassword: String
    ): Call<UserPasswordResponse>
}