package com.moneylover.constants;

import lombok.Getter;

@Getter
public enum HttpStatusCode {
    CONTINUE("100", "Continue"),
    SWITCHING_PROTOCOLS("101", "Switching Protocols"),

    OK("200", "OK"),
    CREATED("201", "Created"),
    ACCEPTED("202", "Accepted"),
    NO_CONTENT("204", "No Content"),

    BAD_REQUEST("400", "Bad Request"),
    UNAUTHORIZED("401", "Unauthorized"),
    FORBIDDEN("403", "Forbidden"),
    NOT_FOUND("404", "Not Found"),
    METHOD_NOT_ALLOWED("405", "Method Not Allowed"),
    REQUEST_TIMEOUT("408", "Request Timeout"),
    CONFLICT("409", "Conflict"),
    PAYLOAD_TOO_LARGE("413", "Payload Too Large"),
    UNSUPPORTED_MEDIA_TYPE("415", "Unsupported Media Type"),

    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
    NOT_IMPLEMENTED("501", "Not Implemented"),
    BAD_GATEWAY("502", "Bad Gateway"),
    SERVICE_UNAVAILABLE("503", "Service Unavailable"),
    GATEWAY_TIMEOUT("504", "Gateway Timeout");

    private final String code;
    private final String message;

    HttpStatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static HttpStatusCode fromCode(String code) {
        for (HttpStatusCode status : values()) {
            if (status.code.equals(code)) return status;
        }
        return null;
    }
}
