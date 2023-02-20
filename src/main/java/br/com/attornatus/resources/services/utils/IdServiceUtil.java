package br.com.attornatus.resources.services.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdServiceUtil {

    public Boolean validIdentity(Long id) {
        throwsExceptionIdEmpty(id);
        throwsExceptionIdLessThanOne(id);
        return true;
    }

    private void throwsExceptionIdEmpty(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("O id (" + id + ") informado não pode ser nulo.");
        }
    }

    private void throwsExceptionIdLessThanOne(Long id) {
        if (id < 1) {
            throw new IllegalArgumentException("O id (" + id + ") informado não pode ser menor do que zero.");
        }
    }

}
