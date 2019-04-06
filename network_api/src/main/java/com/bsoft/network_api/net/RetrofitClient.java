package com.bsoft.network_api.net;

import android.content.Context;
import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;


import com.alibaba.android.arouter.launcher.ARouter;
import com.bsoft.baselib.AppContext;
import com.bsoft.baselib.service.IAppService;
import com.bsoft.commonlib.Constants;
import com.bsoft.commonlib.RouterPath;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Flowable;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * Created by diwang on 2018/6/12.
 */

public class RetrofitClient {
    private static final int CONNECT_TIMEOUT = 20;
    private static final int READ_TIMEOUT = 20;
    private static final int WRITE_TIMEOUT = 60;
  //  private static String baseUrl = AppContext.getBaseUrl();
  private static String baseUrl = Constants.HTTP_HCN_URL_TEXT;
    private static Context mContext = AppContext.getContext();
    private BaseApiService apiService;
    private static OkHttpClient okHttpClient;

    public RetrofitClient() {
    }

    private static Retrofit retrofit;

    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient(mContext, baseUrl);
    }

    public static RetrofitClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RetrofitClient(Context context, String url) {
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.addNetworkInterceptor( new HttpLoggingInterceptor()
                .setLevel(AppContext.isDebug() ?
                        HttpLoggingInterceptor.Level.BODY :
                        HttpLoggingInterceptor.Level.NONE)
        ).retryOnConnectionFailure(false)//不重连
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为15s
                .connectionPool(new ConnectionPool(8, CONNECT_TIMEOUT, TimeUnit.SECONDS))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
//                        System.out.println("header==========="+response.headers().names());
//                        System.out.println("header==========="+response.headers("X-Auth-Failed-Code"));
                        if (response.header("X-Auth-Failed-Code") != null ) {
                            Intent intent = new Intent(AppContext.getHttpHeaderAction());
                            intent.putExtra("code", response.header("X-Auth-Failed-Code"));
                            AppContext.getContext().sendBroadcast(intent);
                        }
                        return response;
                    }
                });
        if(AppContext.getHttpsCer() != null) {
            setCertificates(builder, new Buffer()
                    .writeUtf8(AppContext.getHttpsCer())
                    .inputStream());
        }
        okHttpClient = builder.build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(FastJsonConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();

        createBaseApi();

    }
    public void setCertificates(OkHttpClient.Builder builder, InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);

            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

            sslContext.init(
                    null,
                    trustManagerFactory.getTrustManagers(),
                    new SecureRandom()
            );
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            builder.sslSocketFactory(sslSocketFactory, trustManager);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * create BaseApi  default ApiManager
     *
     * @return ApiManager
     */
    private RetrofitClient createBaseApi() {
        if (apiService == null) {
            apiService = create(BaseApiService.class);
        }
        return this;
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

   /* public Call<String> post(String url, ArrayMap<String, String> headers, Object body) {
        if (headers == null) {
            return apiService.post(url, body);
        }
        return apiService.post(url, headers, body);
    }

    public Call<String> get(String url, ArrayMap<String, String> headers) {
        if (headers == null) {
            return apiService.get(url);
        }
            return apiService.get(url, headers);
        }*/

    public Flowable<String> post(String url, ArrayMap<String, String> headers, Object body) {
        if (headers == null) {
            return apiService.post(url, body);
        }
        return apiService.post(url, headers, body);
    }
//    public Flowable<String> postForm(String url, ArrayMap<String, String> form) {
//        return apiService.postForm(url, form);
//    }

    public Flowable<String> get(String url, ArrayMap<String, String> headers) {
        if (headers == null) {
            return apiService.get(url);
        }
        return apiService.get(url, headers);
    }
    public Flowable<String> postHeader(String url, String filePath, String serviceFileName,
                                       ArrayMap<String, String> headers) {
        MultipartBody.Part part = null;
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            RequestBody photo = RequestBody.create(MediaType.parse("image/*"), file);
            part = MultipartBody.Part.createFormData(serviceFileName, file.getName(), photo);
        }

        return apiService.uploadHeader(url, part, headers);
    }

    /**
     * 上传图片+表单
     *
     * @param url
     * @param maps      form表单数据
     * @param filePaths
     * @param headers
     * @return
     */
    public Flowable<String> postFiles(String url, ArrayMap<String, String> maps, List<String> filePaths,
                                      ArrayMap<String, String> headers) {
        ArrayMap<String, RequestBody> photos = new ArrayMap<>();
        if (filePaths != null && filePaths.size() > 0) {
            for (int i = 0; i < filePaths.size(); i++) {
                File file = new File(filePaths.get(i));
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                photos.put("file\"; filename=\"" + file.getName(), requestFile);
            }
        }
        ArrayMap<String, RequestBody> map = new ArrayMap<>();
        if(maps != null) {
            for (String key : maps.keySet()) {
                if (maps.get(key) == null) {continue;}
                map.put(key, RequestBody.create(MediaType.parse("form-data"), maps.get(key)));
            }
        }
        return apiService.uploadFile(url, map, photos, headers);
    }

}
