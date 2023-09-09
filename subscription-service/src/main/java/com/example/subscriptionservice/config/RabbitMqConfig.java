package com.example.subscriptionservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${rabbitmq.exchange}")
    String exchange;

    @Value("${rabbitmq.routingKey}")
    String routingKey;

    @Bean
    DirectExchange exchange(){
        return new DirectExchange(exchange);
    }

    @Bean
    Queue firstStepQueue(){
        return new Queue("firstStepQueue",false);
    }

    @Bean
    Queue secondStepQueue(){
        return new Queue("secondStepQueue",true);
    }

    @Bean
    Queue thirdStepQueue(){
        return new Queue("thirdStepQueue",true);
    }

    @Bean
    Queue fourthStepQueue(){
        return new Queue("fourthStepQueue",true);
    }

    @Bean
    Queue fifthStepQueue(){
        return new Queue("fifthStepQueue",true);
    }

    @Bean
    Queue sixthStepQueue(){
        return new Queue("sixthStepQueue",true);
    }

    @Bean
    Binding binding(Queue firstStepQueue,DirectExchange exchange){
        return BindingBuilder.bind(firstStepQueue).to(exchange).with("firstRoute");
    }

    @Bean
    Binding secondBinding(Queue secondStepQueue,DirectExchange exchange){
        return BindingBuilder.bind(secondStepQueue).to(exchange).with("secondRoute");
    }

    @Bean
    Binding thirdBinding(Queue thirdStepQueue,DirectExchange exchange){
        return BindingBuilder.bind(thirdStepQueue).to(exchange).with(routingKey);
    }

    @Bean
    Binding fourthBinding(Queue fourthStepQueue,DirectExchange exchange){
        return BindingBuilder.bind(fourthStepQueue).to(exchange).with("fourthRoute");
    }

    @Bean
    Binding fifthBinding(Queue fifthStepQueue,DirectExchange exchange){
        return BindingBuilder.bind(fifthStepQueue).to(exchange).with("fifthRoute");
    }

    @Bean
    Binding sixthBinding(Queue sixthStepQueue,DirectExchange exchange){
        return BindingBuilder.bind(sixthStepQueue).to(exchange).with("sixthRoute");
    }

    @Bean
    MessageConverter jsonMessageConverter(){
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules(); //Fixes an issue with LocalDateTime serialization
        return new Jackson2JsonMessageConverter(mapper);
    }


}













