package ru.gorohov.springbootmvc.handler;

import java.time.LocalDateTime;

public record DefaultError(
        String message,
        String detailedMessage,
        LocalDateTime errorTime
) {
}