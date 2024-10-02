package ru.gorohov.springbootmvc.handler;


import java.time.LocalDateTime;

public record MessageErrorOnField(
        String message,
        String field,
        String detailedMessage,
        LocalDateTime errorTime
) {
}