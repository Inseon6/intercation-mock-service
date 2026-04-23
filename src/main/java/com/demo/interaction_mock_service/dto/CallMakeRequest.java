package com.demo.interaction_mock_service.dto;

import lombok.Builder;
import lombok.With;

@Builder
public record CallMakeRequest(String messageType,
                              String requestId,
                              String webSocketSessionId,
                              String tenantKey,
                              String tenantId,
                              @With String agentKey,
                              String agentId,
                              @With String extensionNumberKey,
                              String extensionNumber,
                              String destination,
                              String pbxId) implements CallRequest {
}
