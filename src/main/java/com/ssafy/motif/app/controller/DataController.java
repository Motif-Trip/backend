package com.ssafy.motif.app.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/data")
public class DataController {

    @Value("${data.url}")
    private String url;

    @GetMapping
    public String getData(@RequestParam String query, @RequestParam String cond) throws Exception {
        return requestData(formatUrl(query, cond));
    }

    private String formatUrl(String query, String cond) throws UnsupportedEncodingException {
        return String.format("%s?query=%s&searchCoord=%s", url,
            URLEncoder.encode(query, "UTF-8"),
            URLEncoder.encode(cond, "UTF-8"));
    }

    private String requestData(String url) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            return br.lines().collect(Collectors.joining());
        }
    }

}
