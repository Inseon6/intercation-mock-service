package com.demo.interaction_mock_service.dto.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MakeCallResponseEvent(
        String messageType,
        String requestId,
        String tenantId,
        String agentId,
        String webSocketSessionId,
        String tenantKey,
        String agentKey,
        String extensionNumberKey,
        String extensionNumber,
        String destination,
        String ucid,
        String callId,
        String resultCode,
        String pbxId
) {
}
