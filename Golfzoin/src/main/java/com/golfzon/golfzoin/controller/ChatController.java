package com.golfzon.golfzoin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.golfzon.golfzoin.dto.ChatRoomDto;
import com.golfzon.golfzoin.dto.MessageDto;
import com.golfzon.golfzoin.repository.ChatRoomRepo;
import com.golfzon.golfzoin.service.ChatService;
import com.golfzon.golfzoin.service.MessagePublisherImpl;
import com.golfzon.golfzoin.service.MessageSubscriber;


@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
public class ChatController {
	private static Logger logger = LoggerFactory.getLogger(ChatController.class);
	@Autowired
	private ChatRoomRepo chatRoomRepo;
	
	@Autowired
	private RedisMessageListenerContainer redisMessageListener;
	
	@Autowired
	private MessagePublisherImpl messagePublisher;
	
	@Autowired
	private MessageSubscriber messageSubscriber;
	
	@Autowired
	private ChatService chatService;
	
//	@Autowired
//	private SimpMessageSendingOperations messagingTemplate;
	
	
//	@PostMapping("/publish")
//	public void publish(@RequestBody MessageDto message) {
//		logger.info(">>publishing : {}", message);
//		
//		messagePublisher.publish(chatRoomRepo.getTopic(message.getRoomId()), message);
//	}
	// test용 나중에 조인 방 생성쪽으로 이동 예정
	@GetMapping("/createroom") 
	public void createRoom(@RequestBody Map<String,String> map) {
//		List<ChatRoomDto> list = chatRoomRepo.findAllRoom();
//		for(ChatRoomDto chatRoomDto : list) {
//			System.out.println(chatRoomDto.getRoomId());
//		}
		ChannelTopic channel = new ChannelTopic(map.get("roomNo"));
		redisMessageListener.addMessageListener(messageSubscriber, channel);
		chatRoomRepo.createChatRoom(map.get("title"), map.get("roomNo"));
	}
	
	
	@GetMapping("/roomlist")
	public void roomlist() {
		List<ChatRoomDto> list = chatRoomRepo.findAllRoom();
		System.out.println(list);
	}
	@GetMapping("enterchatroom/{roomNo}")
	public List<MessageDto> enterchatroom(@PathVariable int roomNo){
		System.out.println(">>> enter chat room "+ roomNo);
		try {
			ChannelTopic channel = new ChannelTopic(Integer.toString(roomNo));
			redisMessageListener.addMessageListener(messageSubscriber, channel);
//			chatRoomRepo.createChatRoom(map.get("title"), map.get("roomNo"));
			List<MessageDto> list = chatService.chatList(roomNo);
			for(MessageDto messageDto : list) {
				messageDto.setDate(messageDto.getDate().substring(10, 16));
//				System.out.println(messageDto.getDate());
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//    /pub/api/chat/message 로 들어오는 메시지 처리
	@MessageMapping("/message")
    public void message(@RequestBody MessageDto message)	 {
		System.out.println(">>> MESSAGE "+message.toString());
//		chatRoomRepo.enterChatRoom(message.getRoomNo());
		System.out.println(">>> Message.getroomNo : "+message.getRoomNo());
        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
		ChannelTopic topic = new ChannelTopic(message.getRoomNo());
        messagePublisher.publish(topic, message);
//		messagingTemplate.convertAndSend("/sub/greetings/", message);
        
        
      //RDB에 메시지 저장
      	Map<String, String> map = new HashMap<String, String>();
      	map.put("roomNo", message.getRoomNo());
      	map.put("author", message.getAuthor());
      	map.put("data", message.getData());
      	map.put("date", message.getDate());
      	if(message.getProfile() != null) {
      		map.put("profile",  message.getProfile());
      	}
      	try {
      		chatService.pushchat(map);
      	} catch (Exception e) {
      		e.printStackTrace();
      	}
    }
	
	
	
	@GetMapping("/subscribe")
	public List<String> getMessages(){
		
		return MessageSubscriber.messageList;
	}
}
