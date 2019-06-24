package com.gabi.backend.bikeparkend.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"stackTrace","cause","suppressed","localizedMessage"})
public class NotAllowedBikerException extends Exception {
    public NotAllowedBikerException(String message){
        super(message);
    }
}
