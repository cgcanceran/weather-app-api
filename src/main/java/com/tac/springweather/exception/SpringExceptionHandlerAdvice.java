package com.tac.springweather.exception;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.tac.springweather.model.error.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class SpringExceptionHandlerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringExceptionHandlerAdvice.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, List<ErrorMessage>> handleException(MethodArgumentNotValidException exception) {
        return transformBindErrors(exception.getBindingResult());
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public Map<String, List<ErrorMessage>> handleException(HttpRequestMethodNotSupportedException exception) {
        return createErrorMessage(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, List<ErrorMessage>> handleException(MethodArgumentTypeMismatchException exception) {
        return createErrorMessage("incorrect type for value: " + exception.getValue().toString());
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, List<ErrorMessage>> handleException(HttpMessageNotReadableException exception) {
        return createErrorMessage("invalid request");
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, List<ErrorMessage>> handleException(BindException exception) {
        return transformBindErrors(exception.getBindingResult());
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, List<ErrorMessage>> handleException(ServletRequestBindingException exception) {
        return createErrorMessage(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, List<ErrorMessage>> badRequestException(final BadRequestException e) {
        return createErrorMessage(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public Map<String, List<ErrorMessage>> badRequestException(final ForbiddenOperationException e) {
        return createErrorMessage(e.getMessage());
    }

    private Map<String, List<ErrorMessage>> transformBindErrors(BindingResult result) {
        return createErrorsMap(result.getFieldErrors().stream()
                .sorted((fe1, fe2) -> fe1.getField().compareTo(fe2.getField()))
                .sorted((fe1, fe2) -> fe1.getDefaultMessage().compareTo(fe2.getDefaultMessage()))
                .map(this::resolveErrors)
                .collect(Collectors.toList()));
    }

    private ErrorMessage resolveErrors(FieldError fe) {
        String localizedErrorMessage = messageSource.getMessage(fe, LocaleContextHolder.getLocale());
        return new ErrorMessage(String.format("%s: %s", fe.getField(), localizedErrorMessage));
    }

    static Map<String, List<ErrorMessage>> createErrorMessage(String msg) {
        LOGGER.info(msg);
        return createErrorsMap(new ErrorMessage(msg));
    }

    static Map<String, List<ErrorMessage>> createErrorsMap(List<ErrorMessage> errors) {
        return ImmutableMap.of("errors", errors);
    }

    static Map<String, List<ErrorMessage>> createErrorsMap(ErrorMessage error) {
        return createErrorsMap(Lists.newArrayList(error));
    }

}
