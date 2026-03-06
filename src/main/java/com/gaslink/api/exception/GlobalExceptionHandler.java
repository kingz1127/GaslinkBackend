//package com.gaslink.api.exception;
//
//import com.gaslink.api.response.ApiResponse;
//import org.slf4j.Logger; // Added manual import
//import org.slf4j.LoggerFactory; // Added manual import
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.time.Instant;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestControllerAdvice
//// REMOVED @Slf4j
//public class GlobalExceptionHandler {
//
//    // ADDED MANUAL LOGGER (This replaces @Slf4j)
//    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFoundException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(ex.getMessage()));
//    }
//
//    @ExceptionHandler(UnauthorizedException.class)
//    public ResponseEntity<ApiResponse<Void>> handleUnauthorized(UnauthorizedException ex) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(ex.getMessage()));
//    }
//
//    @ExceptionHandler(ForbiddenException.class)
//    public ResponseEntity<ApiResponse<Void>> handleForbidden(ForbiddenException ex) {
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error(ex.getMessage()));
//    }
//
//    @ExceptionHandler(BusinessRuleException.class)
//    public ResponseEntity<ApiResponse<Void>> handleBusinessRule(BusinessRuleException ex) {
//        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ApiResponse.error(ex.getMessage()));
//    }
//
//    @ExceptionHandler(PaymentVerificationException.class)
//    public ResponseEntity<ApiResponse<Void>> handlePaymentVerification(PaymentVerificationException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(ex.getMessage()));
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach(error -> {
//            String field = ((FieldError) error).getField();
//            errors.put(field, error.getDefaultMessage());
//        });
//
//        // Ensure you are using the manual constructor we created in the previous step
//        ApiResponse<Map<String, String>> response = new ApiResponse<>(
//                false,
//                "Validation failed",
//                errors,
//                Instant.now()
//        );
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<Void>> handleGeneral(Exception ex) {
//        log.error("Unhandled exception", ex); // This will now work correctly
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(ApiResponse.error("An unexpected error occurred"));
//    }
//}