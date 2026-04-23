package com.demo.interaction_mock_service.service;

import com.demo.interaction_mock_service.dto.AgentLoginRequest;
import com.demo.interaction_mock_service.dto.AgentLogoutRequest;
import com.demo.interaction_mock_service.dto.CallMakeRequest;
import com.demo.interaction_mock_service.dto.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncMakeCallEventPublisher {

    private final RedisStreamPublisher redisStreamPublisher;

    @Async
    public void publishMakeCallEvents(CallMakeRequest request) throws InterruptedException {
        long currentTime = System.currentTimeMillis();
        String ucid = String.format("00011058801%d", currentTime);
        int callId = (int) (currentTime % 100000);

        // 1. MakeCallResponse 이벤트 발행
        MakeCallResponseEvent makeCallResponse = MakeCallResponseEvent.builder()
                .messageType("MakeCallResponse")
                .requestId(request.requestId())
                .tenantId(request.tenantId())
                .agentId(request.agentId())
                .webSocketSessionId(request.webSocketSessionId())
                .tenantKey(request.tenantKey())
                .agentKey(request.agentKey())
                .extensionNumberKey(request.extensionNumberKey())
                .extensionNumber(request.extensionNumber())
                .destination(request.destination())
                .ucid(ucid)
                .callId(String.valueOf(callId))
                .pbxId(request.pbxId())
                .resultCode("00000")
                .build();

        redisStreamPublisher.publishEvent("MOCK:EVT:Response:Interaction", makeCallResponse);

        // 2. ServiceInitiated 이벤트 (52ms 후)
//        Thread.sleep(52);
        long serviceInitiatedTime = System.currentTimeMillis();
        CallEvent serviceInitiated = createCallEvent(
                "ServiceInitiated", "20", "30", callId, ucid, serviceInitiatedTime,
                "", "", "", "0", "", "outbound", "1", "", "",
                makeCallResponse
        );
        redisStreamPublisher.publishEvent("MOCK:EVT:Station:Interaction", serviceInitiated);

        // 3. Originated 이벤트 (3ms 후)
//        Thread.sleep(3);
        long originatedTime = System.currentTimeMillis();
        CallEvent originated = createCallEvent(
                "Originated", "17", "30", callId, ucid, originatedTime,
                "public", "01066106618", "4966", "22", "", "outbound", "1", "", "",
                makeCallResponse
        );
        redisStreamPublisher.publishEvent("MOCK:EVT:Station:Interaction", originated);

        // 4. DeliveredOut 이벤트 (약 2.3초 후)
//        Thread.sleep(2300);
        long deliveredTime = System.currentTimeMillis();
        CallEvent deliveredOut = createCallEvent(
                "DeliveredOut", "7", "30", callId, ucid, deliveredTime,
                "public", "01066106618", "4966", "22", "", "outbound", "1", "2", "1",
                makeCallResponse
        );
        redisStreamPublisher.publishEvent("MOCK:EVT:Station:Interaction", deliveredOut);

        // 5. AgentState CONNECTED(통화중) 이벤트 (DeliveredOut 직후)
        long connectingStateTime = System.currentTimeMillis();
        AgentLoginEvent connectingState = AgentLoginEvent.builder()
                .messageType("AgentState")
                .publishTime(connectingStateTime)
                .tenantKey(request.tenantKey())
                .tenantId(request.tenantId())
                .agentKey(request.agentKey())
                .agentId(request.agentId())
                .appUserId(null)
                .extensionNumberKey(request.extensionNumberKey())
                .isLogin(true)
                .loginAt(null)
                .logoutAt(null)
                .sessionId(UUID.randomUUID().toString())
                .originalAgentState("AUX")
                .agentState("CONNECTING")
                .stateChangedAt(connectingStateTime)
                .reasonCode(0)
                .mediaType(null)
                .pbxId(request.pbxId())
                .eventTime(connectingStateTime)
                .directionType(null)
                .build();
        redisStreamPublisher.publishEvent("MOCK:EVT:AgentState", connectingState);

        // 6. EstablishedOut 이벤트 (통화 연결, 약 1초 후)
//        Thread.sleep(1000);
        long establishedTime = System.currentTimeMillis();
        CallEvent establishedOut = createCallEvent(
                "EstablishedOut", "10", "30", callId, ucid, establishedTime,
                "public", "01066106618", "4966", "22", "", "outbound", "1", "2", "1",
                makeCallResponse
        );
        redisStreamPublisher.publishEvent("MOCK:EVT:Station:Interaction", establishedOut);

        // 7. AgentState CONNECTED(통화중) 이벤트 (EstablishedOut 직후)
        long connectedStateTime = System.currentTimeMillis();
        AgentLoginEvent connectedState = AgentLoginEvent.builder()
                .messageType("AgentState")
                .publishTime(connectedStateTime)
                .tenantKey(request.tenantKey())
                .tenantId(request.tenantId())
                .agentKey(request.agentKey())
                .agentId(request.agentId())
                .appUserId(null)
                .extensionNumberKey(request.extensionNumberKey())
                .isLogin(true)
                .loginAt(null)
                .logoutAt(null)
                .sessionId(UUID.randomUUID().toString())
                .originalAgentState("AUX")
                .agentState("CONNECTED")
                .stateChangedAt(connectedStateTime)
                .reasonCode(0)
                .mediaType(null)
                .pbxId(request.pbxId())
                .eventTime(connectedStateTime)
                .directionType(null)
                .build();
        redisStreamPublisher.publishEvent("MOCK:EVT:AgentState", connectedState);

        // 8. CallCleared 이벤트 (통화 종료, 약 5초 후)
//        Thread.sleep(5000);

        // 10초 ~ 30초 랜덤
        int randomDelaySec = 10 + (int)(Math.random() * 21); // 10~30
        log.info("[MOCK] CallCleared 랜덤 딜레이: {}초", randomDelaySec);
        Thread.sleep(randomDelaySec * 1000L);

        long clearedTime = System.currentTimeMillis();
        CallEvent callCleared = createCallEvent(
                "CallCleared", "1", "30", callId, ucid, clearedTime,
                "public", "01066106618", "4966", "0", "other", "outbound", "0", "", "",
                makeCallResponse
        );
        redisStreamPublisher.publishEvent("MOCK:EVT:Station:Interaction", callCleared);

        // 9. AgentState ACW(후처리) 이벤트 (CallCleared 직후)
        long acwStateTime = System.currentTimeMillis();
        AgentLoginEvent acwState = AgentLoginEvent.builder()
                .messageType("AgentState")
                .publishTime(acwStateTime)
                .tenantKey(request.tenantKey())
                .tenantId(request.tenantId())
                .agentKey(request.agentKey())
                .agentId(request.agentId())
                .appUserId(null)
                .extensionNumberKey(request.extensionNumberKey())
                .isLogin(true)
                .loginAt(null)
                .logoutAt(null)
                .sessionId(UUID.randomUUID().toString())
                .originalAgentState("ACW")
                .agentState("ACW")
                .stateChangedAt(acwStateTime)
                .reasonCode(null)
                .mediaType(null)
                .pbxId(request.pbxId())
                .eventTime(acwStateTime)
                .directionType("outbound")
                .build();
        redisStreamPublisher.publishEvent("MOCK:EVT:AgentState", acwState);
    }

    private CallEvent createCallEvent(String messageType, String messageId, String monitorId,
                                      int callId, String ucid, long eventTime,
                                      String aniType, String ani, String dnis,
                                      String cause, String releasingType, String directionType,
                                      String connectionCount, String trunkGroup, String trunkMember,
                                      MakeCallResponseEvent event) {
        return CallEvent.builder()
                .messageType(messageType)
                .messageId(messageId)
                .monitorId(monitorId)
                .domain(null)
                .callId(callId)
                .originalCallId(0)
                .ucid(ucid)
                .preUcid("")
                .originalUcid(ucid)
                .bridgeId("BACP01")
                .aniType(aniType)
                .ani(ani)
                .dnis(dnis)
                .uui("")
                .cause(cause)
                .releasingType(releasingType)
                .directionType(directionType)
                .mediaType("voice")
                .channelType("ext")
                .connectionCount(connectionCount)
                .consultMode(null)
                .consultData("")
                .pwData("")
                .inboundData(CallEvent.InboundData.builder().ivrData("").build())
                .confData(null)
                .split("")
                .pbxId(event.pbxId())
                .tenantKey(event.tenantKey())
                .tenantId(event.tenantId())
                .agentKey(event.agentKey())
                .agentId(event.agentId())
                .extensionType("0")
                .extensionNumberKey(event.extensionNumberKey())
                .extensionNumber(event.extensionNumber())
                .skillKey("")
                .queueKey(null)
                .queueId(null)
                .queueTime(null)
                .trunkGroup(trunkGroup)
                .trunkMember(trunkMember)
                .eventTime(eventTime)
                .sessionId(null)
                .publishTime(eventTime)
                .additionalInfo(null)
                .build();
    }
}
