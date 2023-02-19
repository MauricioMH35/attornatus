package br.com.attornatus.resources.services.utils;

import br.com.attornatus.utils.LocalDateUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class BirthServiceUtil {

    private final Clock clock;
    private final LocalDateUtility localDateUtility;

    public LocalDate parseBirth(String birth) {
        throwExceptionBirthEmpty(birth);
        throwExceptionBirthNotFormattedOfDate(birth);
        return localDateUtility.parseLocalDate(birth);
    }

    private void throwExceptionBirthEmpty(String birth) {
        if (birth.isEmpty()) {
            throw new IllegalArgumentException("A data de nascimento não foi informada.");
        }
    }

    private void throwExceptionBirthNotFormattedOfDate(String birth) {
        boolean isNotValidDateFormat = !localDateUtility.isLocalDateFormatter(birth);
        if (isNotValidDateFormat) {
            throw new IllegalArgumentException("A data de nascimento informada é de um formato não conhecido pela " +
                    "applicação.");
        }
    }

}
