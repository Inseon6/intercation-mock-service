package com.demo.interaction_mock_service.controller;

import com.demo.interaction_mock_service.dto.AgentAuxRequest;
import com.demo.interaction_mock_service.service.AsyncAgentAuxEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MockAgentStateController {

    private final AsyncAgentAuxEventPublisher asyncAgentAuxEventPublisher;

    @PostMapping("/api/isac/interaction/agent/aux")
    public ResponseEntity<Void> changeAgentStateAux(@RequestBody AgentAuxRequest agentAuxRequest) {
        log.info("AUX 상태 변경 요청: {}", agentAuxRequest.toString());
        asyncAgentAuxEventPublisher.publishAgentAuxEvents(agentAuxRequest);
        return ResponseEntity.ok().build();
    }
}
