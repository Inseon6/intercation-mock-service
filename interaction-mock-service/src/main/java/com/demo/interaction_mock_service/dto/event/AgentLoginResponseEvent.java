package com.demo.interaction_mock_service.dto.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AgentLoginResponseEvent(
        String messageType,
        String callId,
        String ucid,
        String requestId,
        String webSocketSessionId,
        String tenantKey,
        String tenantId,
        String agentKey,
        String agentId,
        String extensionNumberKey,
        String extensionNumber,
        String loginType,
        String destination,
        String resultCode,
        String reasonCode,
        Boolean isAgentTarget,
        String targetAgentKey,
        String targetAgentId,
        String targetExtensionNumberKey,
        String targetExtensionNumber,
        String domain,
        String authInfo,
        Long publishTime
) {
}
