package com.demo.interaction_mock_service.dto.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CallEvent(
        String messageType,
        String messageId,
        String monitorId,
        String domain,
        Integer callId,
        Integer originalCallId,
        String ucid,
        String preUcid,
        String originalUcid,
        String bridgeId,
        String aniType,
        String ani,
        String dnis,
        String uui,
        String cause,
        String releasingType,
        String directionType,
        String mediaType,
        String channelType,
        String connectionCount,
        String consultMode,
        String consultData,
        String pwData,
        InboundData inboundData,
        Object confData,
        String split,
        String pbxId,
        String tenantKey,
        String tenantId,
        String agentKey,
        String agentId,
        String extensionType,
        String extensionNumberKey,
        String extensionNumber,
        String skillKey,
        String queueKey,
        String queueId,
        Long queueTime,
        String trunkGroup,
        String trunkMember,
        Long eventTime,
        String sessionId,
        Long publishTime,
        Object additionalInfo
) {
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record InboundData(String ivrData) {}
}
