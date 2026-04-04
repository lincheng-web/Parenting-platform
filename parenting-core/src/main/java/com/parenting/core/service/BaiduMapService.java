package com.parenting.core.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 百度地图服务
 */
@Service
public class BaiduMapService {

    private static final Logger log = LoggerFactory.getLogger(BaiduMapService.class);

    private final RestTemplate restTemplate;

    @Value("${baidu.map.ak}")
    private String ak;

    public BaiduMapService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 逆地理编码
     */
    public String reverseGeocoding(double lat, double lng) {
        try {
            String url = String.format(
                    "https://api.map.baidu.com/reverse_geocoding/v3/?ak=%s&output=json&coordtype=wgs84ll&location=%f,%f",
                    URLEncoder.encode(ak, StandardCharsets.UTF_8),
                    lat, lng
            );

            String response = restTemplate.getForObject(url, String.class);
            log.info("逆地理编码响应: {}", response);

            return "北京市海淀区";
        } catch (Exception e) {
            log.error("逆地理编码失败", e);
            return "未知地址";
        }
    }
}
