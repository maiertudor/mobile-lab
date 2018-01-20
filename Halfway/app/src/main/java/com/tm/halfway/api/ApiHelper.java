package com.tm.halfway.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tm.halfway.utils.Constants;
import com.tm.halfway.utils.SessionUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by FilipMarusca on 04/08/16.
 */

public class ApiHelper {
    public static final int TIMEOUT_FAST_CONNECTION = 20;

    private static final String BASE_ENDPOINT = "https://halfway-staging-api.herokuapp.com/api/";

    private static ApiHelper sInstance;

    private static Gson sGson;

    private IHalfwayAPI mFlyCleanersApi;

    public ApiHelper() {
        mFlyCleanersApi = (IHalfwayAPI) createApiClient(IHalfwayAPI.class, BASE_ENDPOINT);

        sGson = initGson();
    }

    public static ApiHelper getInstance() {
        if (sInstance == null) {
            sInstance = new ApiHelper();
        }

        return sInstance;
    }

    public static IHalfwayAPI getApi() {
        return getInstance().mFlyCleanersApi;
    }

    public static Gson getGson() {
        return getInstance().sGson;
    }

    public static String getBaseEndpoint() {
        return BASE_ENDPOINT;
    }

    private static Object createApiClient(Class clazz, String endpoint) {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(TIMEOUT_FAST_CONNECTION, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(TIMEOUT_FAST_CONNECTION, TimeUnit.SECONDS);

        if (clazz.equals(IHalfwayAPI.class)) {
            // Add interceptor for authorization for the fly cleaners API
            okHttpBuilder.addInterceptor(chain -> {
                Request request = chain.request();
                Request newRequest;

                newRequest = request.newBuilder()
                        .addHeader(Constants.ApiConstants.HEADER_AUTHORIZATION_KEY, SessionUtils.getToken())
                        .build();

                return chain.proceed(newRequest);
            });
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpBuilder.addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(initGson()))
                .build();

        return retrofit.create(clazz);
    }

    private static Gson initGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }
}
