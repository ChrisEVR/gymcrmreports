package com.epam.gymcrmreports.config;

import jakarta.jms.Queue;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;


@Configuration
public class ActiveMQConfig {
    @Value("${spring.activemq.broker-url}")
    public String brokerUrl;
    @Value("${spring.activemq.user}")
    public String brokerUsername;
    @Value("${spring.activemq.password}")
    public String brokerPassword;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("reports");
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();

        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        activeMQConnectionFactory.setUserName(brokerUsername);
        activeMQConnectionFactory.setPassword(brokerPassword);
        return activeMQConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(activeMQConnectionFactory());
    }
}
