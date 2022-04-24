package ru.solarlab.study.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.solarlab.study.errors.ValidationErrorResponse;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice /* Обозначает глобальный класс по обработке ошибок */
@Slf4j
public class ErrorHandlingControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * Обрабатывает валидационную ошибку (которая через throw)
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationErrorResponse> onValidationExceptionError(
            ValidationException e) {

        return new ResponseEntity<>(
                new ValidationErrorResponse(
                        List.of(e.getMessage())),
                HttpStatus.BAD_REQUEST);

    }

    /**
     * Стандартный метод, который возникоет прии валидации параметров
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> onConstraintValidationException(
            ConstraintViolationException e) {

        final List<String> violations = e.getConstraintViolations().stream()
                .map(error -> String.format("" +
                        "%s: %s",
                        error.getPropertyPath().toString(),
                        error.getMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                new ValidationErrorResponse(violations),
                HttpStatus.BAD_REQUEST);

    }

    /**
     * Перехват ошибки MethodArgumentNotValidException для Validation Contract
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return Массив строк с расшифровкой exception
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        final List<String> violations = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format(
                        "%s: %s",
                        error.getField(),
                        error.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                new ValidationErrorResponse(violations),
                HttpStatus.BAD_REQUEST);

    }

}
