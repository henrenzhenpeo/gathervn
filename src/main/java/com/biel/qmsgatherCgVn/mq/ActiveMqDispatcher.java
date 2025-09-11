package com.biel.qmsgatherCgVn.mq;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
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

    @SuppressWarnings("unchecked")
    public <T> void dispatch(List<T> batch, Class<T> type) {
        PayloadBuilder<T> builder = (PayloadBuilder<T>) builderMap.get(type);
        if (builder == null) {
            // 未提供对应构建器，记录警告并跳过
            System.err.println("[ActiveMqDispatcher] No PayloadBuilder found for type: " + type.getName());
            return;
        }

        String queue = builder.queueName();
        if (builder.sendMode() == SendMode.PER_ITEM) {
            for (T e : batch) {
                Object payload = builder.buildPayload(e);
                jmsTemplate.convertAndSend(queue, payload);
            }
        } else { // BATCH
            Object payload = builder.buildBatchPayload(batch);
            jmsTemplate.convertAndSend(queue, payload);
        }
    }
}