package com.golfzon.golfzoin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.golfzon.golfzoin.service.MessagePublisher;
import com.golfzon.golfzoin.service.MessagePublisherImpl;
import com.golfzon.golfzoin.service.MessageSubscriber;

@Component
public class RedisConfig {
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	
//	@Bean
//	public RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter messageListenerAdapter) {
//		RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
//		redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
//		redisMessageListenerContainer.addMessageListener(messageListenerAdapter, topic());
//		return redisMessageListenerContainer;
//	}
	@Bean
	public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory) {
	    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
	    container.setConnectionFactory(connectionFactory);
	    return container;
	}
//	@Bean
//	public MessageListenerAdapter messageListenerAdapter() {
//		return new MessageListenerAdapter(new MessageSubscriber());
//	}
	
	@Bean
	private ChannelTopic topic() {
		return new ChannelTopic("test");
	}
	// Json타입 변환을 위해 기존 GenericToStringSerializer를 jackson2Json으로 변환
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
//		redisTemplate.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		return redisTemplate;
	}
	
	
//	@Bean
//	public MessagePublisher messagePublisher() {
//		return new MessagePublisherImpl(redisTemplate(redisConnectionFactory), topic());
//	}
}
