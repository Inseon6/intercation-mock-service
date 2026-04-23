package com.demo.interaction_mock_service.service;

import com.demo.interaction_mock_service.dto.AgentLogoutRequest;
import com.demo.interaction_mock_service.dto.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncAgentLogoutEventPublisher {

    private final RedisStreamPublisher redisStreamPublisher;

    @Async
    public void publishAgentLogoutEvents(AgentLogoutRequest request) {
        long currentTime = System.currentTimeMillis();

        // AgentLogout 이벤트 발행
        AgentLogoutEvent agentLogoutEvent = AgentLogoutEvent.builder()
                .messageType("AgentLogout")
                .publishTime(currentTime)
                .tenantKey(request.tenantKey())
                .tenantId(request.tenantId())
                .agentKey(request.agentKey())
                .agentId(request.agentId())
                .appUserId(null)
                .extensionNumberKey(request.extensionNumberKey())
                .isLogin(false)
                .loginAt(null)
                .logoutAt(currentTime)
                .sessionId(UUID.randomUUID().toString())
                .originalAgentState("LOGOUT")
                .agentState("LOGOUT")
                .stateChangedAt(currentTime)
                .reasonCode(null)
                .mediaType(null)
                .pbxId(request.pbxId())
                .eventTime(currentTime)
                .directionType(null)
                .build();

        redisStreamPublisher.publishEvent("MOCK:EVT:AgentState", agentLogoutEvent);

        // AgentLogoutResponse 이벤트 발행
        AgentLogoutResponseEvent agentLogoutResponseEvent = AgentLogoutResponseEvent.builder()
                .messageType("AgentLogoutResponse")
                .requestId(request.requestId())
                .tenantId(request.tenantId())
                .agentId(request.agentId())
                .webSocketSessionId(request.webSocketSessionId())
                .tenantKey(request.tenantKey())
                .agentKey(request.agentKey())
                .loginType(request.loginType())
                .extensionNumberKey(request.extensionNumberKey())
                .extensionNumber(request.extensionNumber())
                .isAgentTarget(request.isAgentTarget())
                .resultCode("00000")
                .publishTime(currentTime)
                .build();

        redisStreamPublisher.publishEvent("MOCK:EVT:Response:Interaction", agentLogoutResponseEvent);
    }
}
