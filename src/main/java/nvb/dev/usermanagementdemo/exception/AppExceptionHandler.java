package nvb.dev.usermanagementdemo.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nvb.dev.usermanagementdemo.constant.ExceptionMessage.*;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        Map<String, Object> body = new HashMap<>();

        body.put(TIME_STAMP, LocalDate.now());
        body.put(MESSAGE, exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Object> handleNoDataFoundException(NoDataFoundException exception) {
        Map<String, Object> body = new HashMap<>();

        body.put(TIME_STAMP, LocalDate.now());
        body.put(MESSAGE, exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        Map<String, Object> body = new HashMap<>();

        body.put(TIME_STAMP, LocalDate.now());
        body.put(STATUS, status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        body.put(ERRORS, errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
