package com.bsoft.network_api.net;

import android.support.v4.util.ArrayMap;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by diwang on 2018/6/12.
 */

public interface BaseApiService {

    @FormUrlEncoded  //表示请求发送编码表单数据，每个键值对需要使用@Field注解
    @POST
   //@Filed  多用于post请求中表单字段,Filed和FieldMap需要FormUrlEncoded结合使用
    //@FiledMap 和@Filed作用一致，用于不确定表单参数
    Call<String> post(@Url String url, @HeaderMap ArrayMap<String, String> headers,
                      @FieldMap ArrayMap<String, String> maps);

   // @Body多用于post请求发送非表单数据,比如想要以post方式传递json格式数据



    @Multipart
    @POST
    Call<String> uploadFiles(@Url String url, @PartMap ArrayMap<String, RequestBody> files,
                             @HeaderMap ArrayMap<String, String> headers);

    @Multipart
    @POST
    Call<String> uploadFiles(@Url String url, @PartMap ArrayMap<String, RequestBody> files,
                             @HeaderMap ArrayMap<String, String> headers,
                             @QueryMap ArrayMap<String, String> params);

    @Multipart
    @POST
    Call<String> uploadFile(@Url String url, @Part MultipartBody.Part file,
                            @HeaderMap ArrayMap<String, String> headers);

    @Multipart
    @POST
    Call<String> uploadFile(@Url String url, @Part MultipartBody.Part file,
                            @HeaderMap ArrayMap<String, String> headers,
                            @QueryMap ArrayMap<String, String> params);

    @Multipart
    @POST
    Flowable<String> uploadFile(@Url String url, @PartMap ArrayMap<String, RequestBody> maps,
                                @PartMap ArrayMap<String, RequestBody> params,
                                @HeaderMap ArrayMap<String, String> headers);



    @POST
    Flowable<String> post(@Url String url, @Body Object body);

    @POST
    Flowable<String> post(@Url String url,
                          @HeaderMap ArrayMap<String, String> headers,
                          @Body Object body);

    @GET
    Flowable<String> get(@Url String url);

    @GET
    Flowable<String> get(@Url String url, @HeaderMap ArrayMap<String, String> headers);
    @Multipart
    @POST
    Flowable<String> uploadHeader(@Url String url, @Part MultipartBody.Part file,
                                  @HeaderMap ArrayMap<String, String> headers);

}
