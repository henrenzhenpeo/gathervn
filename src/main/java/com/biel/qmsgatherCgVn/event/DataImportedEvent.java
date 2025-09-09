package com.biel.qmsgatherCgVn.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * 通用“导入完成（批次）”领域事件，承载批量数据与类型信息
 */
public class DataImportedEvent<T> extends ApplicationEvent {

    @Getter
    private final List<T> payload;

    @Getter
    private final Class<T> entityType;

    public DataImportedEvent(List<T> payload, Class<T> entityType) {
        super(payload);
        this.payload = payload;
        this.entityType = entityType;
    }
}