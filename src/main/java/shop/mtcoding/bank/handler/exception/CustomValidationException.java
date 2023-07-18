package shop.mtcoding.bank.handler.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class CustomValidationException extends RuntimeException{

    private final Map<String, String> map;

    public CustomValidationException(String message, Map<String, String> map) {
        super(message);
        this.map = map;
    }
}
