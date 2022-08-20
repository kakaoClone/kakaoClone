package com.clone.kakaoclone.webscoket;

import com.clone.kakaoclone.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
//대화를 보는 부분?
@RequiredArgsConstructor
@Service
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessageDto chatMessageDto) {
        redisTemplate.convertAndSend(topic.getTopic(), chatMessageDto);
    }
}