package com.demo.interaction_mock_service.dto.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AgentAuxResponseEvent(
        String messageType,
        String requestId,
        String tenantId,
        String agentId,
        String webSocketSessionId,
        String extensionNumber,
        Boolean isAgentTarget,
        String resultCode
) {
}
