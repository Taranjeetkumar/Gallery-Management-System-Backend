package com.gallery.dto;

import java.util.Map;
import lombok.Data;

@Data
public class EmailResponse {
    private int statusCode;
    private Map<String, String> headers;
    private String body;
}

