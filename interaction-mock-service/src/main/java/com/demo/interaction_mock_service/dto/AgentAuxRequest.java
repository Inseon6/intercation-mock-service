package com.demo.interaction_mock_service.dto;

import lombok.Builder;

@Builder
public record AgentAuxRequest(
        String agentId,
        String tenantId,
        String webSocketSessionId,
        String tenantKey,
        String messageType,
        String requestId,
        String agentKey,
        String extensionNumber,
        String reasonCode,
        Boolean isAgentTarget,
        String pbxId,
        String extensionNumberKey
) implements CallRequest {
}
