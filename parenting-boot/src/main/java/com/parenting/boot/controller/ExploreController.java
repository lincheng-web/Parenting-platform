package com.parenting.boot.controller;

import com.parenting.core.service.BaiduMapService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/explore")
@CrossOrigin(origins = "*")
public class ExploreController {

    private static final Logger log = LoggerFactory.getLogger(ExploreController.class);

    private final BaiduMapService baiduMapService;

    public ExploreController(BaiduMapService baiduMapService) {
        this.baiduMapService = baiduMapService;
    }

    @PostMapping("/nearby")
    public ResponseEntity<Map<String, Object>> nearby(@RequestBody Map<String, Object> request) {
        Double latitude = (Double) request.get("latitude");
        Double longitude = (Double) request.get("longitude");
        String category = (String) request.getOrDefault("category", "park");
        Integer radius = (Integer) request.getOrDefault("radius", 1000);
        
        log.info("周边搜索: lat={}, lng={}, category={}, radius={}", latitude, longitude, category, radius);

        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Map<String, Object>> pois = getMockPOIs(category);
            
            response.put("success", true);
            response.put("pois", pois);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("周边搜索失败", e);
            response.put("success", false);
            response.put("pois", new ArrayList<>());
            return ResponseEntity.ok(response);
        }
    }

    private List<Map<String, Object>> getMockPOIs(String category) {
        List<Map<String, Object>> pois = new ArrayList<>();
        
        switch (category) {
            case "park":
                pois.add(createPOI("朝阳公园", "北京市朝阳区朝阳公园南路1号", 500));
                pois.add(createPOI("奥林匹克森林公园", "北京市朝阳区北五环林萃路", 1200));
                pois.add(createPOI("团结湖公园", "北京市朝阳区团结湖", 800));
                break;
            case "school":
                pois.add(createPOI("北京市第一中学", "北京市东城区鼓楼东大街", 800));
                pois.add(createPOI("朝阳区实验小学", "北京市朝阳区建国路", 300));
                pois.add(createPOI("清华附中", "北京市海淀区中关村", 2000));
                break;
            case "hospital":
                pois.add(createPOI("北京儿童医院", "北京市西城区南礼士路56号", 2000));
                pois.add(createPOI("朝阳医院", "北京市朝阳区工人体育场南路8号", 1500));
                pois.add(createPOI("协和医院", "北京市东城区帅府园1号", 3000));
                break;
            case "mall":
                pois.add(createPOI("朝阳大悦城", "北京市朝阳区朝阳北路101号", 600));
                pois.add(createPOI("国贸商城", "北京市朝阳区建国门外大街1号", 1800));
                pois.add(createPOI("三里屯太古里", "北京市朝阳区三里屯路", 900));
                break;
            default:
                pois.add(createPOI("示例地点", "示例地址", 1000));
        }
        
        return pois;
    }

    private Map<String, Object> createPOI(String name, String address, int distance) {
        Map<String, Object> poi = new HashMap<>();
        poi.put("name", name);
        poi.put("address", address);
        poi.put("distance", distance);
        return poi;
    }
}
