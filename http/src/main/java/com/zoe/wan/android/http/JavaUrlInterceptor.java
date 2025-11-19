package com.zoe.wan.android.http;


import androidx.annotation.NonNull;
import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Description:动态域名切换功能测试
 * @Author: zengjinjin
 * @CreateDate: 2025/11/12
 */
public final class JavaUrlInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final Request originalRequest = chain.request();
        System.out.println("hzz 原始请求"+ originalRequest.url().url());
        String matchUrl = getUrl(originalRequest);
        // 重建 URL
        Request.Builder requestBuilder = originalRequest.newBuilder();
        HttpUrl newUrl = rebuildUrl(originalRequest.url(), matchUrl);
        System.out.println("hzz 动态替换后的请求"+ newUrl.url());
        requestBuilder.url(newUrl);
        final Request newRequest = requestBuilder.build();
        System.out.println("hzz 动态替换后的请求"+ newRequest.url().url());
        return chain.proceed(newRequest);
    }

    private String getUrl(Request request) {
        //  从 URL 路径模式判断
        String path = request.url().encodedPath();
        if (path.contains("v1")) {
            return BaseUrlConstants.url1;
        } else if (path.contains("v2")) {
            return BaseUrlConstants.url2;
        }
        // 从注解信息判断（需要反射，比较复杂）
        return BaseUrlConstants.baseUrl;
    }

    private HttpUrl rebuildUrl(HttpUrl originalUrl, final String baseUrl) {
        try {
            HttpUrl newBase = HttpUrl.parse(baseUrl);
            if (newBase == null) {
                throw new IllegalArgumentException("Bad URL: " + baseUrl);
            }
            return originalUrl.newBuilder()
                    .scheme(newBase.scheme())
                    .host(newBase.host())
                    .port(newBase.port())
                    .build();
        } catch (Exception e) {
            return originalUrl;
        }
    }
}
