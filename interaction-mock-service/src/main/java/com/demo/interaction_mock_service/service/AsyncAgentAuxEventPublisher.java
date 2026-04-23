package com.demo.interaction_mock_service.service;

import com.demo.interaction_mock_service.dto.AgentAuxRequest;
import com.demo.interaction_mock_service.dto.event.AgentAuxResponseEvent;
import com.demo.interaction_mock_service.dto.event.AgentLoginEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncAgentAuxEventPublisher {

    private final RedisStreamPublisher redisStreamPublisher;

    @Async
    public void publishAgentAuxEvents(AgentAuxRequest request) {
        long currentTime = System.currentTimeMillis();

        // AgentAuxResponse 이벤트 발행
        AgentAuxResponseEvent agentAuxResponseEvent = AgentAuxResponseEvent.builder()
                .messageType("AgentAuxResponse")
                .requestId(request.requestId())
                .tenantId(request.tenantId())
                .agentId(request.agentId())
                .webSocketSessionId(UUID.randomUUID().toString())
                .extensionNumber(request.extensionNumber())
                .isAgentTarget(request.isAgentTarget())
                .resultCode("00000")
                .build();

        redisStreamPublisher.publishEvent("MOCK:EVT:Response:Interaction", agentAuxResponseEvent);

        // AgentState 이벤트 발행
        AgentLoginEvent agentStateEvent = AgentLoginEvent.builder()
                .messageType("AgentState")
                .publishTime(currentTime)
                .tenantKey(request.tenantKey())
                .tenantId(request.tenantId())
                .agentKey(request.agentKey())
                .agentId(request.agentId())
                .appUserId(null)
                .extensionNumberKey(request.extensionNumberKey())
                .isLogin(true)
                .loginAt(currentTime)
                .logoutAt(null)
                .sessionId(UUID.randomUUID().toString())
                .originalAgentState("AUX")
                .agentState("AUX")
                .stateChangedAt(currentTime)
                .reasonCode(Integer.parseInt(request.reasonCode()))
                .mediaType(null)
                .pbxId(request.pbxId())
                .eventTime(currentTime)
                .directionType(null)
                .build();

        redisStreamPublisher.publishEvent("MOCK:EVT:AgentState", agentStateEvent);
    }
}
