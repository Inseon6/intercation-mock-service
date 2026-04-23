package com.demo.interaction_mock_service.dto.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AgentLoginEvent(
        String messageType,
        Long publishTime,
        String tenantKey,
        String tenantId,
        String agentKey,
        String agentId,
        String appUserId,
        String extensionNumberKey,
        String extensionNumber,
        Boolean isLogin,
        Long loginAt,
        Long logoutAt,
        String sessionId,
        String originalAgentState,
        String agentState,
        Long stateChangedAt,
        Integer reasonCode,
        String mediaType,
        String pbxId,
        Long eventTime,
        String directionType
) {
}
