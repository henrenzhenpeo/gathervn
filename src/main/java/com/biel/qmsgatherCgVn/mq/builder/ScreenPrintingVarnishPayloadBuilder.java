package com.biel.qmsgatherCgVn.mq.builder;

import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingVarnish;
import com.biel.qmsgatherCgVn.mq.AbstractPayloadBuilder;
import com.biel.qmsgatherCgVn.mq.PayloadBuilder;
import com.biel.qmsgatherCgVn.mq.SendMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ScreenPrintingVarnishPayloadBuilder extends AbstractPayloadBuilder<DfUpScreenPrintingVarnish> implements PayloadBuilder<DfUpScreenPrintingVarnish> {

    @Value("${app.mq.queues.all_data_queue}")
    private String queueName;

    @Override
    public Class<DfUpScreenPrintingVarnish> supportedType() {
        return DfUpScreenPrintingVarnish.class;
    }

    @Override
    public String queueName() {
        return queueName;
    }

    @Override
    public SendMode sendMode() {
        return SendMode.PER_ITEM; // 逐条发送
    }

    @Override
    public Object buildPayload(DfUpScreenPrintingVarnish e) {
        Map<String, Object> msg = new HashMap<>();
        msg.put("CheckDevCode", null);                     // 按既有约定：null
        msg.put("ItemName", e.getTestProject());           // 测量项目
        msg.put("CheckType", e.getRemark());                // 默认映射为 state（如需用备注请改为 e.getRemark()）
        msg.put("MachineCode", e.getMachineCode());        // 机台号
        msg.put("ProcessNO", e.getProcess());              // 工序
        msg.put("CheckTime", format(e.getDate()));         // yyyy-MM-dd HH:mm:ss

        List<Map<String, Object>> items = new ArrayList<>();
        addItem(items, "1点Y2", e.getOnePointy2());
        addItem(items, "2点Y1", e.getTwoPointy1());
        addItem(items, "3点", e.getThreePoint());
        addItem(items, "aoc", e.getGroove());
        addItem(items, "四点x", e.getFourPointx());
        addItem(items, "5点Y1", e.getFivePointy1());
        addItem(items, "6点Y2", e.getSixPointy2());
        addItem(items, "7点", e.getSevenPoint());
        addItem(items, "8点x", e.getEightPointx());

        addItem(items, "2D码到视窗1", e.getTwoCodeWindow1());
        addItem(items, "2D码到视窗2", e.getTwoCodeWindow2());
        addItem(items, "光油上边到基准1", e.getLightOilTopReference1());
        addItem(items, "光油上边到基准2", e.getLightOilTopReference2());

        addItem(items, "光油内边到玻璃中心距离1", e.getTwoCodeCenter1());
        addItem(items, "光油内边到玻璃中心距离2", e.getTwoCodeCenter2());
        addItem(items, "2D码上到玻中心1", e.getTwoCodeTopCenter1());
        addItem(items, "2D码上到玻中心2", e.getTwoCodeTopCenter2());

        addItem(items, "调机x", e.getDebugMachinex());
        addItem(items, "调机y1", e.getDebugMachiney1());
        addItem(items, "调机y2", e.getDebugMachiney2());

        msg.put("CheckItemInfos", items);
        return msg;
    }

    private void addItem(List<Map<String, Object>> list, String name, Double value) {
        if (value != null) {
            Map<String, Object> item = new HashMap<>();
            item.put("ItemName", name);
            item.put("CheckValue", value);
            list.add(item);
        }
    }
}