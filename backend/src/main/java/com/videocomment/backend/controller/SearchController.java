package com.videocomment.backend.controller;

import com.videocomment.backend.core.constant.ApiRouterConsts;
import com.videocomment.backend.core.constant.Result;
import com.videocomment.backend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName SearchController
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/5 21:55
 * @Version 1.0
 */
@RestController
@RequestMapping(ApiRouterConsts.SEARCH_URL_PREFIX)
public class SearchController {
    @Autowired
    SearchService searchService;

    @GetMapping("/content")
    public Result search(@RequestParam Map<String,String> m1){
        String content = m1.get("content");
        return searchService.search(content);
    }
}
