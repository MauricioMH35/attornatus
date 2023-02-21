package br.com.attornatus.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
@RequiredArgsConstructor
public class LocalDateUtility {

    private final Clock clock;
    private final String PATTERN = "dd-MM-yyyy";
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    private LocalDate localDate;
    private String localDateString;

    public Boolean isLocalDateFormatter(String localDate) {
        try {
            this.localDate = LocalDate.parse(localDate, FORMATTER);
            return true;
        }
        catch (DateTimeParseException e) {
            return false;
        }
    }

    public Boolean isLocalDateFormatter(LocalDate localDate) {
        try {
            this.localDateString = localDate.format(FORMATTER);
            return true;
        }
        catch (DateTimeParseException e) {
            return false;
        }
    }

    public LocalDate parseLocalDate(String localDate) {
        if (isLocalDateFormatter(localDate)) {
            return this.localDate;
        }
        return null;
    }

    public String parseString(LocalDate localDate) {
        if (isLocalDateFormatter(localDate)) {
            return this.localDateString;
        }
        return null;
    }

    public LocalDate now() {
        LocalDate now = LocalDate.now(clock);
        return now;
    }

}
