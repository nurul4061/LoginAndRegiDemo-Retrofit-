package capsulestudio.loginandregi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Shaon on 8/15/2016.
 */
public interface APIService {


    @FormUrlEncoded
    @POST("loginandregi/view/signup.php")
    Call<MSG> userSignUp(@Field("name") String name,
                         @Field("email") String email,
                         @Field("password") String password);

    @FormUrlEncoded
    @POST("loginandregi/view/login.php")
    Call<MSG> userLogIn(@Field("email") String email,
                        @Field("password") String password);

    @FormUrlEncoded
    @POST("phpFile/food/InsertData_Retrieve.php")
    Call<GetUserName> userGetIn(@Field("name") String name);





}