package com.biel.qmsgatherCgVn.mq;

import java.util.List;

public interface PayloadBuilder<T> {

    // 声明该 Builder 支持的实体类型
    Class<T> supportedType();

    // 此类型对应的 ActiveMQ 队列名（可从配置注入）
    String queueName();

    // 发送模式（逐条 or 批量）
    SendMode sendMode();

    // 逐条发送时的消息体构建
    Object buildPayload(T entity);

    // 批量发送时的消息体构建（默认把每条逐条构建再组成数组）
    default Object buildBatchPayload(List<T> batch) {
        return batch.stream().map(this::buildPayload).toArray();
    }
}