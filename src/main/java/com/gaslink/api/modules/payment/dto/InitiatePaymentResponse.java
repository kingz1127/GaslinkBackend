package com.gaslink.api.modules.payment.dto;

public class InitiatePaymentResponse {
    private String authorizationUrl;
    private String accessCode;
    private String reference;

    // Default constructor
    public InitiatePaymentResponse() {}

    // All args constructor
    public InitiatePaymentResponse(String authorizationUrl, String accessCode, String reference) {
        this.authorizationUrl = authorizationUrl;
        this.accessCode = accessCode;
        this.reference = reference;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String authorizationUrl;
        private String accessCode;
        private String reference;

        public Builder authorizationUrl(String authorizationUrl) {
            this.authorizationUrl = authorizationUrl;
            return this;
        }

        public Builder accessCode(String accessCode) {
            this.accessCode = accessCode;
            return this;
        }

        public Builder reference(String reference) {
            this.reference = reference;
            return this;
        }

        public InitiatePaymentResponse build() {
            return new InitiatePaymentResponse(authorizationUrl, accessCode, reference);
        }
    }

    // Getters and Setters
    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "InitiatePaymentResponse{" +
                "authorizationUrl='" + authorizationUrl + '\'' +
                ", accessCode='" + accessCode + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }
}