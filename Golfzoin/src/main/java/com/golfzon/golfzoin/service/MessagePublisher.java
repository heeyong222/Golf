package com.golfzon.golfzoin.service;

import org.springframework.data.redis.listener.ChannelTopic;

import com.golfzon.golfzoin.dto.MessageDto;

public interface MessagePublisher {
	void publish(ChannelTopic topic, MessageDto message);
}
