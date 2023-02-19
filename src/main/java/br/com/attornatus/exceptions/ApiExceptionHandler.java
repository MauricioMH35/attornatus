package br.com.attornatus.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApiExceptionHandler {

    private final ZoneId zoneId;

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ApiException> handleInternalServerErrorException(InternalServerErrorException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ZonedDateTime timestampNow = ZonedDateTime.now(zoneId);

        ApiException response = new ApiException(
                status, e.getMessage(), timestampNow
        );
        return ResponseEntity
                .status(status)
                .body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiException> handleBadRequestException(BadRequestException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ZonedDateTime timestampNow = ZonedDateTime.now(zoneId);

        ApiException response = new ApiException(
                status, e.getMessage(), timestampNow
        );
        return ResponseEntity
                .status(status)
                .body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiException> handleIllegalArgumentException(IllegalArgumentException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ZonedDateTime timestampNow = ZonedDateTime.now(zoneId);

        ApiException response = new ApiException(
                status, e.getMessage(), timestampNow
        );
        return ResponseEntity
                .status(status)
                .body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiException> handleNotFoundException(NotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ZonedDateTime timestampNow = ZonedDateTime.now(zoneId);

        ApiException response = new ApiException(
                status, e.getMessage(), timestampNow
        );

        return ResponseEntity
                .status(status)
                .body(response);
    }

}
