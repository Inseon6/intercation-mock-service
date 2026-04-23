package com.demo.interaction_mock_service.controller;

import com.demo.interaction_mock_service.dto.AgentLogoutRequest;
import com.demo.interaction_mock_service.service.AsyncAgentLogoutEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MockAgentLogoutController {

    private final AsyncAgentLogoutEventPublisher asyncAgentLogoutEventPublisher;

    @PostMapping("/api/isac/interaction/agent/logout")
    public ResponseEntity<Void> logoutAgent(@RequestBody AgentLogoutRequest agentLogoutRequest) {
        log.info("로그아웃 요청{}", agentLogoutRequest.toString());
        asyncAgentLogoutEventPublisher.publishAgentLogoutEvents(agentLogoutRequest);
        return ResponseEntity.ok().build();
    }
}
