package com.demo.interaction_mock_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisStreamPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public void publishEvent(String streamKey, Object event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            MapRecord<String, String, String> record = MapRecord.create(streamKey, Map.of("payload", message));
            redisTemplate.opsForStream().add(record);
            log.info("publishEvent: streamKey={}, event={}", streamKey, event);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize event to JSON", e);
            throw new RuntimeException(e);
        }
    }
}
