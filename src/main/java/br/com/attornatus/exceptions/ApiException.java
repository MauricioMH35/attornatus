package br.com.attornatus.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Builder
@Getter
public class ApiException {

    private final HttpStatus status;
    private final String message;
    private final ZonedDateTime timestamp;

}
