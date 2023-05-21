package labs.nusantara.smartrinse.services.api

import labs.nusantara.smartrinse.services.response.ArticleResponse
import labs.nusantara.smartrinse.services.response.LoginResponse
import labs.nusantara.smartrinse.services.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @FormUrlEncoded
    @POST("users")
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
}