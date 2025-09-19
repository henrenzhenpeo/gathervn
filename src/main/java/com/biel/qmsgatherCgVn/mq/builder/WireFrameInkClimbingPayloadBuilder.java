package com.biel.qmsgatherCgVn.mq.builder;

import com.biel.qmsgatherCgVn.domain.DfUpWireFrameInkClimbing;
import com.biel.qmsgatherCgVn.mq.AbstractPayloadBuilder;
import com.biel.qmsgatherCgVn.mq.PayloadBuilder;
import com.biel.qmsgatherCgVn.mq.SendMode;
import com.biel.qmsgatherCgVn.util.CheckMachineCode;
import com.biel.qmsgatherCgVn.util.CheckProcessName;
import com.biel.qmsgatherCgVn.util.CheckTypeConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WireFrameInkClimbingPayloadBuilder extends AbstractPayloadBuilder<DfUpWireFrameInkClimbing> implements PayloadBuilder<DfUpWireFrameInkClimbing> {

    @Value("${app.mq.queues.all_data_queue}")
    private String queueName;

    @Override
    public Class<DfUpWireFrameInkClimbing> supportedType() {
        return DfUpWireFrameInkClimbing.class;
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
    public Object buildPayload(DfUpWireFrameInkClimbing e) {
        Map<String, Object> msg = new HashMap<>();
        msg.put("CheckDevCode", null);
        msg.put("ItemName", e.getTestProject());
        msg.put("CheckType", CheckTypeConfig.mapForPayload(e.getState()));
        msg.put("MachineCode", CheckMachineCode.mapForMachineCode(e.getMachineCode()));
        msg.put("ProcessNO", CheckProcessName.mapForProcessName(e.getProcess()));
        msg.put("CheckTime", format(e.getDate()));

        List<Map<String, Object>> items = new ArrayList<>();
        addItem(items, "长边1", e.getLongSide1());
        addItem(items, "长边2", e.getLongSide2());
        addItem(items, "凹槽", e.getGroove());
        addItem(items, "凹槽短边", e.getShortGroove());
        addItem(items, "短边", e.getShortSide());

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