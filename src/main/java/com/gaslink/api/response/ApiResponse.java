////package com.gaslink.api.response;
////
////import com.fasterxml.jackson.annotation.JsonInclude;
////import lombok.Builder;
////import lombok.Data;
////
////import java.time.Instant;
////
////@Data
////@Builder
////@JsonInclude(JsonInclude.Include.NON_NULL)
////public class ApiResponse<T> {
////
////    private boolean success;
////    private String message;
////    private T data;
////    private Instant timestamp;
////
////    public static <T> ApiResponse<T> ok(String message, T data) {
////        return ApiResponse.<T>builder()
////                .success(true)
////                .message(message)
////                .data(data)
////                .timestamp(Instant.now())
////                .build();
////    }
////
////    public static <T> ApiResponse<T> ok(T data) {
////        return ok("Success", data);
////    }
////
////    public static <T> ApiResponse<T> error(String message) {
////        return ApiResponse.<T>builder()
////                .success(false)
////                .message(message)
////                .timestamp(Instant.now())
////                .build();
////    }
////}
//
//package com.gaslink.api.response;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import lombok.Builder;
//import lombok.Data;
//import lombok.Setter;
//
//import java.time.Instant;
//
//@Data
//@Builder
//@Setter  // Add this to generate setters
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public class ApiResponse<T> {
//
//    private boolean success;
//    private String message;
//    private T data;
//    private Instant timestamp;
//
//    public static <T> ApiResponse<T> ok(String message, T data) {
//        return ApiResponse.<T>builder()
//                .success(true)
//                .message(message)
//                .data(data)
//                .timestamp(Instant.now())
//                .build();
//    }
//
//    public static <T> ApiResponse<T> ok(T data) {
//        return ok("Success", data);
//    }
//
//    public static <T> ApiResponse<T> error(String message) {
//        return ApiResponse.<T>builder()
//                .success(false)
//                .message(message)
//                .timestamp(Instant.now())
//                .build();
//    }
//}

package com.gaslink.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@NoArgsConstructor // Keep this for Jackson (JSON)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private Instant timestamp;

    // MANUAL CONSTRUCTOR - This ensures the compiler always sees it
    public ApiResponse(boolean success, String message, T data, Instant timestamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(true, message, data, Instant.now());
    }

    public static <T> ApiResponse<T> ok(T data) {
        return ok("Success", data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, Instant.now());
    }
}