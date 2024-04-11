package plazoleta.config.exceptionhandler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import plazoleta.adapters.driven.jpa.msql.exception.ErrorBaseDatos;
import plazoleta.adapters.driven.jpa.msql.exception.ProductNotFount;
import plazoleta.adapters.driving.http.exception.ErrorAccess;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ErrorAccess.class)
    public ResponseEntity<ExceptionResponse> handleErrorListTechnologies(ErrorAccess exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse
                (String.format(exception.getMessage()),
                        HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()
                ));
    }

    @ExceptionHandler(ErrorBaseDatos.class)
    public ResponseEntity<ExceptionResponse> handleErrorBaseDade(ErrorBaseDatos exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse
                (String.format(exception.getMessage()),
                        HttpStatus.INTERNAL_SERVER_ERROR.toString(), LocalDateTime.now()
                ));
    }
    @ExceptionHandler(ProductNotFount.class)
    public ResponseEntity<ExceptionResponse> handleErrorProductNotFount(ProductNotFount exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse
                (String.format(exception.getMessage()),
                        HttpStatus.NOT_FOUND.toString(), LocalDateTime.now()
                ));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
