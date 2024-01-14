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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nvb.dev.usermanagementdemo.constant.ExceptionMessage.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class, NoDataFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(RuntimeException exception) {

        ErrorResponse errorResponse = new ErrorResponse(List.of(exception.getLocalizedMessage()));

        Map<String, Object> body = new HashMap<>();

        body.put(TIME_STAMP, errorResponse.getTimeStamp());
        body.put(MESSAGE, errorResponse.getMessages());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(List.of(ex.getLocalizedMessage()));

        Map<String, Object> body = new HashMap<>();

        body.put(TIME_STAMP, errorResponse.getTimeStamp());
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
