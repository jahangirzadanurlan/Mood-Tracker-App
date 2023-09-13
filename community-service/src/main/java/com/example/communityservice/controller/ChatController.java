package com.example.communityservice.controller;

import com.example.communityservice.model.entity.Post;
import com.example.communityservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatController {
    private final PostRepository postRepository;

    @GetMapping("/chat")
    public String chatPage(){
        return "chat";
    }

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public String sendMessage(String message){
        log.info("Send message metoduna girdi => {}",message);
        return "User: " + message;
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        List<Post> posts = postRepository.findAll();  // Veritabanından tüm post'ları alıyoruz
        model.addAttribute("posts", posts);
        return "home";
    }

}
