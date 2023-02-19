package br.com.attornatus.resources.services.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Builder
@Getter
public class PageableServiceUtil {

    private Logger log;
    private String logMessage;
    private String throwMessage;

    public PageableServiceUtil(Logger log, String logMessage, String throwMessage) {
        this.log = log;
        this.logMessage = logMessage;
        this.throwMessage = throwMessage;
    }

    public void exceptionFieldsAreNotFound(Map<String, String> pageParams) {
        boolean noExistsPageKey = !pageParams.containsKey("page");
        boolean noExistsSizeKey = !pageParams.containsKey("size");

        if (noExistsPageKey || noExistsSizeKey) {
            log.error(logMessage);
            throw new IllegalArgumentException(throwMessage);
        }
    }

    public void exceptionFieldsAreBlank(Map<String, String> pageParams) {
        boolean isNullPageParam = pageParams.get("page") == "";
        boolean isNullSizeParam = pageParams.get("size") == "";

        if (isNullPageParam || isNullSizeParam) {
            log.error(logMessage);
            throw new IllegalArgumentException(throwMessage);
        }
    }

    public void exceptionFieldAreNotNumbers(Map<String, String> pageParams) {
        boolean isPageNotNumeric = !checkFieldIsNumeric(pageParams.get("page"));
        boolean isSizeNotNumeric = !checkFieldIsNumeric(pageParams.get("size"));

        if (isPageNotNumeric || isSizeNotNumeric) {
            log.error(logMessage);
            throw new IllegalArgumentException(throwMessage);
        }
    }

    public void exceptionFieldsAreLessThanZero(Map<String, String> pageParams) {
        boolean isPageLessThanZero = Integer.parseInt(pageParams.get("page")) < 0;
        boolean isSizeLessThanZero = Integer.parseInt(pageParams.get("size")) < 0;

        if (isPageLessThanZero || isSizeLessThanZero) {
            log.error(logMessage);
            throw new IllegalArgumentException(throwMessage);
        }
    }

    private boolean checkFieldIsNumeric(String field) {
        try {
            Integer.parseInt(field);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

}
