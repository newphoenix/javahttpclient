package com.example.http;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(LocalDateTime timestamp,Integer status, String message, List<String> errors) {

}
