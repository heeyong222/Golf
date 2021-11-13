package com.golfzon.golfzoin.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import com.golfzon.golfzoin.dto.ChatRoomDto;
import com.golfzon.golfzoin.service.MessageSubscriber;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Repository
public class ChatRoomRepo {
	@Autowired
	private RedisMessageListenerContainer redisMessageListener;
	@Autowired
	private MessageSubscriber messageSubscriber;
	@Autowired
	private Map<String, ChannelTopic> topics;
//	private HashOperations<String, String, ChatRoomDto> opsHashChatRoom;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	private static final String CHAT_ROOMS = "CHAT_ROOM";
	
	@Resource(name = "redisTemplate")
    private HashOperations<String, String, ChatRoomDto> hashOpsChatRoom;
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOpsEnterInfo;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOps;
    
    
	@PostConstruct
    private void init() {
//        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }
	
	// 모든 채팅방 조회
    public List<ChatRoomDto> findAllRoom() {
        return hashOpsChatRoom.values(CHAT_ROOMS);
    }
    
    
	public void createChatRoom(String title, String roomNo) {
		ChannelTopic topic = new ChannelTopic(roomNo);
		topics.put(roomNo, topic);
//		System.out.println("repo");
        ChatRoomDto chatRoomDto = ChatRoomDto.create(title, roomNo);
//        opsHashChatRoom.put(CHAT_ROOMS, chatRoomDto.getRoomId(), chatRoomDto);
//        System.out.println(topics.get(roomId));
        hashOpsChatRoom.put(CHAT_ROOMS, chatRoomDto.getRoomNo(), chatRoomDto);
    }
	
	public void enterChatRoom(String roomNo) {
        ChannelTopic topic = topics.get(roomNo);
        if (topic == null)
            topic = new ChannelTopic(roomNo);
        redisMessageListener.addMessageListener(messageSubscriber, topic);
        topics.put(roomNo, topic);
    }
	
	public ChannelTopic getTopic(String roomNo) {
		System.out.println("room No : " + topics.get(roomNo));
        return topics.get(roomNo);
    }
}
