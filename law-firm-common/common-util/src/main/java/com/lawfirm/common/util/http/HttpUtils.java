package com.lawfirm.common.util.http;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.lawfirm.common.util.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Map;

@Slf4j
public class HttpUtils {
    
    public static String get(String url) {
        try {
            return HttpRequest.get(url).execute().body();
        } catch (Exception e) {
            log.error("HTTP GET request failed", e);
            return null;
        }
    }
    
    public static <T> T get(String url, Class<T> responseType) {
        try {
            String response = HttpRequest.get(url).execute().body();
            return JsonUtils.parseObject(response, responseType);
        } catch (Exception e) {
            log.error("HTTP GET request failed", e);
            return null;
        }
    }
    
    public static String get(String url, Map<String, String> headers) {
        try {
            return HttpRequest.get(url)
                    .addHeaders(headers)
                    .execute()
                    .body();
        } catch (Exception e) {
            log.error("HTTP GET request failed", e);
            return null;
        }
    }
    
    public static String post(String url, Object body) {
        try {
            return HttpRequest.post(url)
                    .body(JsonUtils.toJsonString(body))
                    .execute()
                    .body();
        } catch (Exception e) {
            log.error("HTTP POST request failed", e);
            return null;
        }
    }
    
    public static <T> T post(String url, Object body, Class<T> responseType) {
        try {
            String response = HttpRequest.post(url)
                    .body(JsonUtils.toJsonString(body))
                    .execute()
                    .body();
            return JsonUtils.parseObject(response, responseType);
        } catch (Exception e) {
            log.error("HTTP POST request failed", e);
            return null;
        }
    }
    
    public static String post(String url, Object body, Map<String, String> headers) {
        try {
            return HttpRequest.post(url)
                    .addHeaders(headers)
                    .body(JsonUtils.toJsonString(body))
                    .execute()
                    .body();
        } catch (Exception e) {
            log.error("HTTP POST request failed", e);
            return null;
        }
    }
    
    public static String upload(String url, Map<String, Object> form) {
        try {
            return HttpRequest.post(url)
                    .form(form)
                    .execute()
                    .body();
        } catch (Exception e) {
            log.error("HTTP upload failed", e);
            return null;
        }
    }
    
    public static void download(String url, String destPath) {
        HttpUtil.downloadFile(url, destPath);
    }
}

@FunctionalInterface
interface HttpProgressListener {
    void onProgress(long total, long current, int progress);
} 