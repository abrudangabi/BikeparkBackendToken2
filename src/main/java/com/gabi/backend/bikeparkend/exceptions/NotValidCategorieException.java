package com.gabi.backend.bikeparkend.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace","cause","suppressed","localizedMessage"})
public class NotValidCategorieException extends Exception {
    public NotValidCategorieException(String message) {
        super(message);
    }
}
