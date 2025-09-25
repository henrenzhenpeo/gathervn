package com.biel.qmsgatherCgVn.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ActiveMqDispatcher {

    @Resource
    private JmsTemplate jmsTemplate;

    @Resource
    private List<PayloadBuilder<?>> builders = Collections.emptyList();

    private Map<Class<?>, PayloadBuilder<?>> builderMap;

    @PostConstruct
    public void init() {
        builderMap = builders.stream().collect(Collectors.toMap(PayloadBuilder::supportedType, b -> b));
    }

    public <T> void dispatch(List<T> batch, Class<T> type) {

        if (batch == null || batch.isEmpty()) {
            log.warn("[ActiveMqDispatcher] 类型 {} 的消息列表为空，跳过发送", type.getName());
            return;
        }

        PayloadBuilder<T> builder = (PayloadBuilder<T>) builderMap.get(type);
        if (builder == null) {
            log.warn("[ActiveMqDispatcher] 未找到类型 {} 对应的 PayloadBuilder，跳过发送", type.getName());
            return;
        }

        String queue = builder.queueName();
        try {
            if (builder.sendMode() == SendMode.PER_ITEM) {
                for (T e : batch) {
                    try {
                        Object payload = builder.buildPayload(e);
                        jmsTemplate.convertAndSend(queue, payload);
                    } catch (Exception ex) {
                        log.error("[ActiveMqDispatcher] 发送单条消息到队列 {} 失败，消息内容：{}，异常：{}", queue, e, ex.getMessage(), ex);
                    }
                }
            } else { // BATCH
                Object payload = builder.buildBatchPayload(batch);
                jmsTemplate.convertAndSend(queue, payload);
            }
        } catch (JmsException ex) {
            log.error("[ActiveMqDispatcher] 发送消息到队列 {} 时发生 JMS 异常：{}", queue, ex.getMessage(), ex);
        } catch (Exception ex) {
            log.error("[ActiveMqDispatcher] 发送消息到队列 {} 时发生未知异常：{}", queue, ex.getMessage(), ex);
        }
    }
}