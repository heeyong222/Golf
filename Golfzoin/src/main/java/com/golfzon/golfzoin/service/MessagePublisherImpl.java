package com.golfzon.golfzoin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.golfzon.golfzoin.dto.MessageDto;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class MessagePublisherImpl implements MessagePublisher {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	
//	public MessagePublisherImpl(RedisTemplate<String, Object> redisTemplate, ChannelTopic topic) {
//		this.redisTemplate = redisTemplate;
//		this.topic = topic;
//	}
	
	@Override
	public void publish(ChannelTopic topic, MessageDto message) {
//		System.out.println("######"+topic);
//		System.out.println(message);
		redisTemplate.convertAndSend(topic.getTopic(), message);
		
	}
	

}
