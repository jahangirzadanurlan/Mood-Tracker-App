package com.example.subscriptionservice.repository;

import com.example.subscriptionservice.model.entity.DailySubscribeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailySubscribeUserRepository extends JpaRepository<DailySubscribeUser,Long> {
}
