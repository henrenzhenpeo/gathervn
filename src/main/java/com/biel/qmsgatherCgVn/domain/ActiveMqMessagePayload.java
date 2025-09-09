package com.biel.qmsgatherCgVn.domain;


import lombok.Data;

/**
 * @Author rookieXun
 * @Date 2025/6/12 17:45
 **/
@Data
public class ActiveMqMessagePayload {

    private String topic;

    private String sourceCode;

    private String message;

    //private String receiveMethod;

    public ActiveMqMessagePayload(String topic, String sourceCode, String message) {
        this.topic = topic;
        this.sourceCode = sourceCode;
        this.message = message;
    }

}
