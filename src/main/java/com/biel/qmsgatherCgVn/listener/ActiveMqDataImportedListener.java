package com.biel.qmsgatherCgVn.listener;

import com.biel.qmsgatherCgVn.domain.DfUpBottomGapChamfer;
import com.biel.qmsgatherCgVn.domain.DfUpChamferHypotenuse;
import com.biel.qmsgatherCgVn.event.DataImportedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 监听导入事件并按类型路由到不同 ActiveMQ 队列（批量）
 */
@Component
public class ActiveMqDataImportedListener {

    @Resource
    private JmsTemplate jmsTemplate;

    // 不同实体类型路由到不同队列，可在 application.yml 配置覆盖默认值
    @Value("${app.mq.queues.bottom-gap-chamfer:bottom_gap_chamfer}")
    private String bottomGapChamferQueue;

    @Value("${app.mq.queues.chamfer-hypotenuse:chamfer_hypotenuse}")
    private String chamferHypotenuseQueue;

    @EventListener(condition = "#root.args[0].entityType == T(com.biel.qmsgatherCgVn.domain.DfUpBottomGapChamfer)")
    public void onBottomGapChamfer(DataImportedEvent<DfUpBottomGapChamfer> event) {
        List<DfUpBottomGapChamfer> batch = event.getPayload();
        if (batch == null || batch.isEmpty()) return;
        jmsTemplate.convertAndSend(bottomGapChamferQueue, batch);
    }

    @EventListener(condition = "#root.args[0].entityType == T(com.biel.qmsgatherCgVn.domain.DfUpChamferHypotenuse)")
    public void onChamferHypotenuse(DataImportedEvent<DfUpChamferHypotenuse> event) {
        List<DfUpChamferHypotenuse> batch = event.getPayload();
        if (batch == null || batch.isEmpty()) return;
        jmsTemplate.convertAndSend(chamferHypotenuseQueue, batch);
    }
}