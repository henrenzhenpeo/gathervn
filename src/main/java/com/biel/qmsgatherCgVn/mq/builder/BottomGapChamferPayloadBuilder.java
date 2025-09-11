package com.biel.qmsgatherCgVn.mq.builder;

import com.biel.qmsgatherCgVn.domain.DfUpBottomGapChamfer;
import com.biel.qmsgatherCgVn.mq.AbstractPayloadBuilder;
import com.biel.qmsgatherCgVn.mq.PayloadBuilder;
import com.biel.qmsgatherCgVn.mq.SendMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BottomGapChamferPayloadBuilder extends AbstractPayloadBuilder<DfUpBottomGapChamfer> implements PayloadBuilder<DfUpBottomGapChamfer> {

    @Value("${app.mq.queues.bottom-gap-chamfer:bottom_gap_chamfer}")
    private String bottomGapChamferQueue;

    @Override
    public Class<DfUpBottomGapChamfer> supportedType() {
        return DfUpBottomGapChamfer.class;
    }

    @Override
    public String queueName() {
        return bottomGapChamferQueue;
    }

    @Override
    public SendMode sendMode() {
        return SendMode.PER_ITEM; // 逐条发送
    }

    @Override
    public Object buildPayload(DfUpBottomGapChamfer e) {
        Map<String, Object> msg = new HashMap<>();
        // 按你的规则映射
        msg.put("CheckDevCode", null);                     // null
        msg.put("ItemName", e.getTestProject());           // testProject
        msg.put("CheckType", e.getRemark());               // remark
        msg.put("MachineCode", e.getMachineCode());        // machineCode
        msg.put("ProcessNO", e.getProcess());              // process
        msg.put("CheckTime", format(e.getDate()));         // date -> yyyy-MM-dd HH:mm:ss

        List<Map<String, Object>> items = new ArrayList<>();
        addItem(items, "上长边底倒角1", e.getUpperLongSideBottomChamfer1());
        addItem(items, "上长边底倒角2", e.getUpperLongSideBottomChamfer2());
        addItem(items, "上长边底倒角3", e.getUpperLongSideBottomChamfer3());
        addItem(items, "右短边底倒角1", e.getRightShortSideBottomChamfer1());
        addItem(items, "凹槽底倒角2", e.getGrooveBottomChamfer2());
        addItem(items, "右短边底倒角3", e.getRightShortSideBottomChamfer3());
        addItem(items, "下长边底倒角1", e.getLowerLongSideBottomChamfer1());
        addItem(items, "下长边底倒角2", e.getLowerLongSideBottomChamfer2());
        addItem(items, "下长边底倒角3", e.getLowerLongSideBottomChamfer3());
        addItem(items, "左短边底倒角1", e.getLeftShortSideBottomChamfer1());
        addItem(items, "左短边底倒角2", e.getLeftShortSideBottomChamfer2());
        addItem(items, "左短边底倒角3", e.getLeftShortSideBottomChamfer3());
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