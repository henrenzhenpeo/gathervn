package com.biel.qmsgatherCgVn.mq;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractPayloadBuilder<T> implements PayloadBuilder<T> {
    protected final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected String format(Date date) {
        return date == null ? null : sdf.format(date);
    }
}