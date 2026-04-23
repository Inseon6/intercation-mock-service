package com.demo.interaction_mock_service.dto;

public interface CallRequest {
    String messageType();
    String requestId();
    String tenantId();
    String agentId();
    String webSocketSessionId();
    String extensionNumber();
    String tenantKey();
    String agentKey();
    String extensionNumberKey();
}
