package com.biel.qmsgatherCgVn.mq.builder;

import com.biel.qmsgatherCgVn.domain.DfUpBottomGapChamfer;
import com.biel.qmsgatherCgVn.domain.DfUpSSBThreeDMachine;
import com.biel.qmsgatherCgVn.mq.AbstractPayloadBuilder;
import com.biel.qmsgatherCgVn.mq.PayloadBuilder;
import com.biel.qmsgatherCgVn.mq.SendMode;
import com.biel.qmsgatherCgVn.util.CheckProcessName;
import com.biel.qmsgatherCgVn.util.CheckTypeConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @Author mr.feng
 * @DATE 2025/9/15 16:00
 */
@Component
public class SSBThreeDMachineBuilder extends AbstractPayloadBuilder<DfUpSSBThreeDMachine> implements PayloadBuilder<DfUpSSBThreeDMachine> {
    @Value("${app.mq.queues.all_data_queue}")
    private String queueName;

    @Override
    public Class<DfUpSSBThreeDMachine> supportedType() {
        return DfUpSSBThreeDMachine.class;
    }

    @Override
    public String queueName() {
        return queueName;
    }

    @Override
    public SendMode sendMode() {
        return SendMode.PER_ITEM;
    }

    @Override
    public Object buildPayload(DfUpSSBThreeDMachine e) {
        Map<String, Object> msg = new HashMap<>();
        // 按你的规则映射
        msg.put("CheckDevCode", null);                     // null
        msg.put("ItemName", e.getTestProject());           // testProject
        // 原：msg.put("CheckType", e.getRemark());
        msg.put("CheckType", CheckTypeConfig.mapForPayload(e.getState())); // 统一映射（CPK->1，FAI->4，未知类型回退为原始字符串）
        msg.put("MachineCode", e.getMachineCode());        // machineCode
        msg.put("ProcessNO", CheckProcessName.mapForProcessName(e.getProcess()));
        msg.put("CheckTime", format(e.getDate()));         // date -> yyyy-MM-dd HH:mm:ss

        List<Map<String, Object>> items = new ArrayList<>();
        addItem(items, "外形长1", e.getExternalLong1());
        addItem(items, "外形长2", e.getExternalLong2());
        addItem(items, "外形宽1", e.getExternalWidth1());
        addItem(items, "外形宽2", e.getExternalWidth2());
        addItem(items, "外形宽3", e.getExternalWidth3());
        addItem(items, "切角角度", e.getCutAngle());
        addItem(items, "切角长", e.getCutAngleLong());
        addItem(items, "切角宽", e.getCutAngleWidth());
        addItem(items, "二维码长", e.getQrCodeLength());
        addItem(items, "二维码宽", e.getQrCodeWidth());
        addItem(items, "白片镭码下边到玻璃距", e.getWhitePlateToglass());
        addItem(items, "白片镭码到玻璃中心", e.getWhitePlateToGlassCenter());

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
