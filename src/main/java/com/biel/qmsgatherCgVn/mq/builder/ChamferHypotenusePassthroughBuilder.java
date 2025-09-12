package com.biel.qmsgatherCgVn.mq.builder;

import com.biel.qmsgatherCgVn.domain.DfUpChamferHypotenuse;
import com.biel.qmsgatherCgVn.mq.AbstractPayloadBuilder;
import com.biel.qmsgatherCgVn.mq.PayloadBuilder;
import com.biel.qmsgatherCgVn.mq.SendMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChamferHypotenusePassthroughBuilder extends AbstractPayloadBuilder<DfUpChamferHypotenuse> implements PayloadBuilder<DfUpChamferHypotenuse> {

    @Value("${app.mq.queues.all_data_queue}")
    private String chamferHypotenuseQueue;

    @Override
    public Class<DfUpChamferHypotenuse> supportedType() {
        return DfUpChamferHypotenuse.class;
    }

    @Override
    public String queueName() {
        return chamferHypotenuseQueue;
    }

    @Override
    public SendMode sendMode() {
        return SendMode.BATCH; // 批量发送一次
    }

    @Override
    public Object buildPayload(DfUpChamferHypotenuse e) {
        // 不会走到（BATCH 模式直接走 buildBatchPayload）
        return e;
    }

    @Override
    public Object buildBatchPayload(List<DfUpChamferHypotenuse> batch) {
        // 直接批量发送原始列表
        return batch;
    }
}