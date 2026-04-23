package com.demo.interaction_mock_service.controller;

import com.demo.interaction_mock_service.dto.AgentLoginRequest;
import com.demo.interaction_mock_service.service.AsyncAgentLoginEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MockAgentLoginController {

    private final AsyncAgentLoginEventPublisher asyncAgentLoginEventPublisher;

    @PostMapping("/api/isac/interaction/agent/login")
    public ResponseEntity<Void> loginAgent(@RequestBody AgentLoginRequest agentLoginRequest) {
        log.info("로그인 요청{}", agentLoginRequest.toString());
        asyncAgentLoginEventPublisher.publishAgentLoginEvents(agentLoginRequest);
        return ResponseEntity.ok().build();
    }
}
