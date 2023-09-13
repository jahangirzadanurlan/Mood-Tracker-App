# Mood Tracker App

## Table of Contents

- [Features](#features-)
- [Technologies Used](#technologies-used-)


---

## Features ✨

### 🎯 Problem Statement 🎯
- Many people today face psychological problems such as stress, nervous reactions, and depression. These issues often lead to other complications.

### 💡 Solution 💡
- I decided to leverage my programming skills to tackle this problem. How? By developing an app that measures people’s daily and weekly mood levels and recommends books and activities accordingly.

### 🚀 How It Works 🚀
- The main working logic of the program is based on PANAS (Positive and Negative Affect Schedule) questions, which consist of 20 questions. These questions are a reliable source for determining the level of positive and negative effects on a person.

- You can answer the 20 questions using a scale from “Very slightly or not at all”(1) to “Extremely”(5). This process takes about 5 minutes. Then, with a special calculation, your negative and positive feelings are determined.

- Every evening at 22:00, I send you a link to answer these questions by email. After you enter the link and answer the questions, you can see your positive and negative feelings scores. Also, I recommend books and activities based on your scores.

- Your daily results are stored in the database. Then every Sunday at 22:00, your weekly results are emailed to you. And again, suitable books and activities are recommended for you.

### 🌐 Community Space 🌐
- The project has a community space where each user can share their results with everyone and see the results of others. The program has global correspondence where users can correspond with each other instantly.

### 📝 Sentiment Analysis 📝
- Finally, the program can find the sentiment level (“Very Negative”,“Negative”,“Neutral”,“Positive”,“Very Positive”) of text in any language you enter.

---

## Technologies Used 🔨🔧\

1. **Kafka** - A distributed streaming platform.
2. **CI CD** - Continuous Integration and Continuous Delivery/Deployment.
3. **OAuth2** - Secure authentication.
4. **JWT** - Authentication and registration.
5. **I18N** - Multilingual support (Russian, Azerbaijani, English, German).
6. **Resilience4J** - Resilient and fault-tolerant architecture.
7. **RabbitMQ** - Message queue for operations like email notifications and subscription handling.
8. **Elasticsearch + Logstash + Kibana** - For powerful search and visualization capabilities.
9. **Docker/docker-compose** - Containerization and orchestration.
10. **Eureka Service/API Gateway** - Service discovery and routing.
11. **Feign Client** - Simplifies HTTP API calls within the microservices.
12. **Validation** - Robust validation mechanisms.
13. **JPA/PostgreSQL** - Database management.
14. **Spring Mail** - Provides functionalities for sending emails.



