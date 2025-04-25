package com.example.headerApp.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HeaderController {

    // Existing REST endpoint
    @GetMapping("/api/headers")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAllHeaders(@RequestHeader Map<String, String> requestHeaders) {
        // Prepare response headers
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Custom-Header", "SpringBootHeaderDemo");
        responseHeaders.add("Powered-By", "Spring Boot");

        // Prepare response body
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("requestHeaders", requestHeaders);
        responseBody.put("responseHeaders", responseHeaders.toSingleValueMap());

        return ResponseEntity.ok().headers(responseHeaders).body(responseBody);
    }

    // New endpoint for HTML view
    // New endpoint for HTML view
    @GetMapping("/view-headers")
    public String viewHeaders(HttpServletRequest request, HttpServletResponse response, Model model) {
        // Capture Request Headers
        Map<String, String> requestHeaders = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            requestHeaders.put(headerName, headerValue);
        }

        // Set Sample Response Headers (for demonstration)
        response.setHeader("Custom-Response-Header", "SampleValue");
        response.setHeader("Another-Response-Header", "AnotherValue");

        // Capture Response Headers
        Map<String, String> responseHeaders = new HashMap<>();
        Collection<String> responseHeaderNames = response.getHeaderNames();
        for (String headerName : responseHeaderNames) {
            String headerValue = response.getHeader(headerName);
            responseHeaders.put(headerName, headerValue);
        }

        // Add headers to the model
        model.addAttribute("requestHeaders", requestHeaders);
        model.addAttribute("responseHeaders", responseHeaders);

        return "headers";
    }

}