package com.ccb.backend.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Slf4j
public class MapUtil {

    private static final ObjectMapper mapper = new ObjectMapper();  // 解析json
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    private static final String ak = "KpdqviMZSgkDPP7aFXgtrGRrnCWeepgn";

    public  float[] getLatLng(String address) {

        // 拼接url地址
        String url = "https://api.map.baidu.com/geocoding/v3/?address="
                + URLEncoder.encode(address, StandardCharsets.UTF_8)
                + "&output=json&ak=" + ak;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // 检查 HTTP 状态码
            if (response.statusCode() != 200) {
                throw new RuntimeException("HTTP 请求失败: " + response.statusCode());
            }

            String json = response.body();

            // 使用 Jackson 解析 JSON
            JsonNode root = mapper.readTree(json);

            int status = root.path("status").asInt();
            if (status != 0) {
                throw new RuntimeException("百度地图 API 返回错误: status=" + status
                        + ", msg=" + root.path("message").asText("无描述"));
            }

            JsonNode location = root.path("result").path("location");
            if (location.isMissingNode()) {
                throw new RuntimeException("响应中未找到 location 字段");
            }

            float lat = (float) location.path("lat").asDouble();
            float lng = (float) location.path("lng").asDouble();

            return new float[]{lat, lng};

        } catch (IOException e) {
            log.error("网络请求失败", e);
            throw new RuntimeException("请求百度地图 API 时发生网络错误", e);
        } catch (InterruptedException e) {
            log.error("请求被中断", e);
            Thread.currentThread().interrupt(); // 重要：恢复中断状态
            throw new RuntimeException("请求百度地图 API 时被中断", e);
        }


    }

}
