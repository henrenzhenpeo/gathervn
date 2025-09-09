package com.biel.qmsgatherCgVn.config;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.messaginghub.pooled.jms.JmsPoolConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class ActiveMQSenderConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String userName;

    @Value("${spring.activemq.password}")
    private String password;

    @Value("${spring.activemq.pool.max-connections:10}")
    private int maxConnections;

    // 配置连接工厂
    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(brokerUrl);
        factory.setUserName(userName);
        factory.setPassword(password);

        // 性能优化配置
        factory.setUseAsyncSend(true); // 异步发送提高吞吐量
        factory.setAlwaysSessionAsync(true);
        factory.setDispatchAsync(true);
        factory.setUseCompression(true); // 启用压缩减少网络传输

        // 生产环境应该设置信任的包，而不是信任所有
        // factory.setTrustedPackages(Arrays.asList("com.yourcompany.model"));
        factory.setTrustAllPackages(true); // 仅限开发和测试环境

        return factory;
    }

    // 配置连接池
    @Bean
    public JmsPoolConnectionFactory pooledConnectionFactory(ActiveMQConnectionFactory connectionFactory) {
        JmsPoolConnectionFactory pool = new JmsPoolConnectionFactory();
        pool.setConnectionFactory(connectionFactory);
        pool.setMaxConnections(maxConnections);
        pool.setConnectionIdleTimeout(30000);
        pool.setBlockIfSessionPoolIsFull(true);
        pool.setBlockIfSessionPoolIsFullTimeout(5000);
        return pool;
    }

    // 配置JMS模板
    @Bean
    public JmsTemplate jmsTemplate(JmsPoolConnectionFactory pooledConnectionFactory) {
        JmsTemplate template = new JmsTemplate(pooledConnectionFactory);
        template.setDeliveryPersistent(true); // 持久化消息
        template.setExplicitQosEnabled(true); // 启用QoS设置

        // 设置消息转换器
        template.setMessageConverter(jacksonJmsMessageConverter());

        // 配置重试策略
        template.setSessionTransacted(false); // 发送者通常不需要事务

        return template;
    }

    // JSON消息转换器
    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

/*    // 配置重试策略
    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        // 指数退避策略
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000);
        backOffPolicy.setMultiplier(2.0);
        backOffPolicy.setMaxInterval(10000);

        // 简单重试策略
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);

        retryTemplate.setBackOffPolicy(backOffPolicy);
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }*/
}