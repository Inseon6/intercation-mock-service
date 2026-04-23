package com.demo.interaction_mock_service.controller;

import com.demo.interaction_mock_service.dto.CallMakeRequest;
import com.demo.interaction_mock_service.service.AsyncMakeCallEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MockMakeCallController {
    private final AsyncMakeCallEventPublisher asyncMakeCallEventPublisher;

    @PostMapping("/api/isac/interaction/call/make")
    public ResponseEntity<Void> makeCall(@RequestBody CallMakeRequest makeCallRequest) throws InterruptedException {
        log.info("아웃바운드 요청: {}", makeCallRequest);
        asyncMakeCallEventPublisher.publishMakeCallEvents(makeCallRequest);
        return ResponseEntity.ok().build();
    }
}
