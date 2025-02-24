package com.lawfirm.common.util.http;

import com.lawfirm.common.util.BaseUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * HTTP工具类
 */
public class HttpUtils extends BaseUtils {

    /**
     * 发送GET请求
     */
    public static String get(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            return client.execute(request, response -> 
                new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("GET请求失败: {}", url, e);
            return null;
        }
    }

    /**
     * 发送带参数的GET请求
     */
    public static String get(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && !params.isEmpty()) {
            sb.append('?');
            params.forEach((key, value) -> 
                sb.append(key).append('=').append(value).append('&'));
            sb.deleteCharAt(sb.length() - 1);
        }
        return get(sb.toString());
    }

    /**
     * 发送POST请求
     */
    public static String post(String url, String body) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(url);
            request.setEntity(new StringEntity(body));
            return client.execute(request, response -> 
                new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("POST请求失败: {}", url, e);
            return null;
        }
    }

    /**
     * 发送带参数的POST请求
     */
    public static String post(String url, Map<String, String> params) {
        StringBuilder body = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            params.forEach((key, value) -> 
                body.append(key).append('=').append(value).append('&'));
            body.deleteCharAt(body.length() - 1);
        }
        return post(url, body.toString());
    }
}

@FunctionalInterface
interface HttpProgressListener {
    void onProgress(long total, long current, int progress);
} 