package com.example.userservice.config;

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
    Binding binding(Queue firstStepQueue,DirectExchange exchange){
        return BindingBuilder.bind(firstStepQueue).to(exchange).with("firstRoute");
    }

    @Bean
    Binding secondBinding(Queue secondStepQueue,DirectExchange exchange){
        return BindingBuilder.bind(secondStepQueue).to(exchange).with(routingKey);
    }

    @Bean
    Binding thirdBinding(Queue thirdStepQueue,DirectExchange exchange){
        return BindingBuilder.bind(thirdStepQueue).to(exchange).with("thirdRoute");
    }

    @Bean
    MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }


}













