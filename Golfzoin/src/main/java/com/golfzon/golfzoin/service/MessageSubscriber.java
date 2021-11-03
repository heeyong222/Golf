package com.golfzon.golfzoin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.golfzon.golfzoin.dto.MessageDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RequiredArgsConstructor
@Service
public class MessageSubscriber implements MessageListener{
	
	public static List<String> messageList = new ArrayList<String>();
	@Autowired
	private SimpMessageSendingOperations messageTemplate;
	@Autowired
	private RedisTemplate redisTemplate;
	private ObjectMapper objectMapper = new ObjectMapper();
//	@Override
//	public void onMessage(Message message, byte[] bytes) {
//		messageList.add(message.toString());
//		System.out.println("Message received : "+ message.toString());
//	}
	
	 @Override
	    public void onMessage(Message message, byte[] pattern) {
		 System.out.println("onmessage접근");
	        try {
	        	messageList.add(message.toString());
	            // redis에서 발행된 데이터를 받아 deserialize
	            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
//	            System.out.println("###message : "+ publishMessage);
	            // ChatMessage 객채로 맵핑
	            MessageDto roomMessage = objectMapper.readValue(publishMessage, MessageDto.class);
	            
	            System.out.println("Room - Message : "+ publishMessage.toString());
	            // Websocket 구독자에게 채팅 메시지 Send
	            System.out.println("# onmessage RoomNo : " + roomMessage.getRoomNo());
	            messageTemplate.convertAndSend("/sub/chat/"+roomMessage.getRoomNo(), roomMessage);
	            
//	            System.out.println(messageList);
	        } catch (Exception e) {
//	            log.error(e.getMessage());
	        }
	    }

}
