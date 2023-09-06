package com.example.subscriptionservice.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PanasSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Integer Interested;
    Integer Distressed;
    Integer Excited;
    Integer Upset;
    Integer Strong;
    Integer Guilty;
    Integer Scared;
    Integer Hostile;
    Integer Enthusiastic;
    Integer Proud;
    Integer Irritable;
    Integer Alert;
    Integer Ashamed;
    Integer Inspired;
    Integer Nervous;
    Integer Determined;
    Integer Attentive;
    Integer Jittery;
    Integer Active;
    Integer Afraid;
}
