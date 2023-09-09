package com.example.communityservice.service;

import com.example.communityservice.model.dto.response.WeeklyPanasResultDto;
import com.example.communityservice.model.entity.Post;
import com.example.communityservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final AmqpTemplate rabbitTemplate;
    private final DirectExchange exchange;

    public String savePost(Post post){
        postRepository.save(post);
        return "Save is successfully";
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    @RabbitListener(queues = "fifthStepQueue")
    public void savePost(WeeklyPanasResultDto weeklyPanasSurvey){
        Post post = Post.builder()
                .title("Weekly Panas Result")
                .content("Today I want to share with you my weekly panas score")
                .username(weeklyPanasSurvey.getEmail())
                .weeklyPanasResultDto(weeklyPanasSurvey)
                .build();
        log.info("fifthStepQueue post title => {}",post.getTitle());
        postRepository.save(post);

        rabbitTemplate.convertAndSend(exchange.getName(),"sixthRoute",weeklyPanasSurvey.getEmail());
    }


}
