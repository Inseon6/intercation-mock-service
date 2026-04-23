package com.demo.interaction_mock_service.dto;

import lombok.Builder;
import lombok.With;

@Builder
public record AgentLoginRequest(String messageType,
                                String requestId,
                                String webSocketSessionId,
                                @With String tenantKey,
                                String tenantId,
                                @With String agentKey,
                                String agentId,
                                @With String extensionNumberKey,
                                String extensionNumber,
                                @With String loginType,
                                String pbxId) implements CallRequest {
}