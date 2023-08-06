package com.ensah.exception;
import com.ensah.payload.response.MessageResponse;
import com.ensah.web.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleThrowable(final Exception exception) {
        log.warn("catched error [NotFoundException] : {} ", exception.getMessage());
        return  new ResponseEntity<>(new MessageResponse("Academic email not found"),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleThrowable(final Throwable ex) {
        log.warn("catched error [Throwable] : {} ", ex.getMessage());
        return  new ResponseEntity<>(new MessageResponse(ex.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleSecurityException(final AccessDeniedException ex) {
        log.warn("catched error [AccessDeniedException] : {}", ex.getMessage());
        return  new ResponseEntity<>(new MessageResponse(ex.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  WebRequest request) {
        log.info("[MethodArgumentNotValidException ]        : {}", ex.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 400); // Change this to 404 if you want to keep the status as 404.
        body.put("error", "Bad Request");
        body.put("message", "Validation failed for object='" + ex.getBindingResult().getObjectName() + "'. Error count: " + ex.getBindingResult().getErrorCount());

        // Customize the errors field
        List<Map<String, Object>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> {
                    Map<String, Object> errorBody = new LinkedHashMap<>();
                    errorBody.put("field", error.getField());
                    errorBody.put("message", error.getDefaultMessage());
                    errorBody.put("rejectedValue", error.getRejectedValue());
                    return errorBody;
                })
                .collect(Collectors.toList());

        body.put("errors", errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}