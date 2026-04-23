package com.demo.interaction_mock_service.service;

import com.demo.interaction_mock_service.dto.AgentLoginRequest;
import com.demo.interaction_mock_service.dto.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncAgentLoginEventPublisher {

    private final RedisStreamPublisher redisStreamPublisher;

    @Async
    public void publishAgentLoginEvents(AgentLoginRequest request) {
        long currentTime = System.currentTimeMillis();

        // AgentLoginResponse 이벤트 발행
        AgentLoginResponseEvent agentLoginResponseEvent = AgentLoginResponseEvent.builder()
                .messageType("AgentLoginResponse")
                .callId(null)
                .ucid(null)
                .requestId(request.requestId())
                .webSocketSessionId(request.webSocketSessionId())
                .tenantKey(request.tenantKey())
                .tenantId(request.tenantId())
                .agentKey(request.agentKey())
                .agentId(request.agentId())
                .extensionNumberKey(request.extensionNumberKey())
                .extensionNumber(request.extensionNumber())
                .loginType(request.loginType())
                .destination(null)
                .resultCode("00000")
                .reasonCode(null)
                .isAgentTarget(null)
                .targetAgentKey(null)
                .targetAgentId(null)
                .targetExtensionNumberKey(null)
                .targetExtensionNumber(null)
                .domain(null)
                .authInfo(null)
                .publishTime(currentTime)
                .build();

        redisStreamPublisher.publishEvent("MOCK:EVT:Response:Interaction", agentLoginResponseEvent);

        // AgentLogin 이벤트 발행
        AgentLoginEvent agentLoginEvent = AgentLoginEvent.builder()
            .messageType("AgentLogin")
            .publishTime(currentTime)
            .tenantKey(request.tenantKey())
            .tenantId(request.tenantId())
            .agentKey(request.agentKey())
            .agentId(request.agentId())
            .appUserId(null)
            .extensionNumberKey(request.extensionNumberKey())
            .extensionNumber(request.extensionNumber())
            .isLogin(true)
            .loginAt(currentTime)
            .logoutAt(null)
            .sessionId(UUID.randomUUID().toString())
            .originalAgentState("AUX")
            .agentState("AUX")
            .stateChangedAt(currentTime)
            .reasonCode(0)

            .mediaType(null)
            .pbxId(request.pbxId())
            .eventTime(currentTime)
            .directionType(null)
            .build();

        redisStreamPublisher.publishEvent("MOCK:EVT:AgentState", agentLoginEvent);
    }
}
