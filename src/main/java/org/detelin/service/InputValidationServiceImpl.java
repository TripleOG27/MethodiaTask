package org.detelin.service;

import org.springframework.stereotype.Service;

import java.util.InputMismatchException;

@Service
public class InputValidationServiceImpl implements InputValidationService {
    @Override
    public void validateString(String input) {
        if (null == input || input.isEmpty()) {
            throw new InputMismatchException(input);
        }
    }
}
