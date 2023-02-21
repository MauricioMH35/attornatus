package br.com.attornatus.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
@RequiredArgsConstructor
public class LocalDateTimeUtility {

    private final Clock clock;
    private final String PATTERN = "dd-MM-yyyy hh:mm:ss";
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    private LocalDateTime localDateTime;
    private String localDateTimeString;

    public Boolean isLocalDateTimeFormatter(String localDateTime) {
        try {
            this.localDateTime = LocalDateTime.parse(localDateTime, FORMATTER);
            return true;
        }
        catch (DateTimeParseException e) {
            return false;
        }
    }

    public Boolean isLocalDateTimeFormatter(LocalDateTime localDateTime) {
        try {
            this.localDateTimeString = localDateTime.format(FORMATTER);
            return true;
        }
        catch (DateTimeParseException e) {
            return false;
        }
    }

    public LocalDateTime parseLocalDate(String localDateTime) {
        if (isLocalDateTimeFormatter(localDateTime)) {
            return this.localDateTime;
        }
        return null;
    }

    public String parseString(LocalDateTime localDateTime) {
        if (isLocalDateTimeFormatter(localDateTime)) {
            return this.localDateTimeString;
        }
        return null;
    }

    public LocalDateTime now() {
        LocalDateTime now = LocalDateTime.now(clock);
        return now;
    }

}
