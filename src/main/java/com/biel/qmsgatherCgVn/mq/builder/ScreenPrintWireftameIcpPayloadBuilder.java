package com.biel.qmsgatherCgVn.mq.builder;

import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintWireftameIcp;
import com.biel.qmsgatherCgVn.mq.AbstractPayloadBuilder;
import com.biel.qmsgatherCgVn.mq.PayloadBuilder;
import com.biel.qmsgatherCgVn.mq.SendMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ScreenPrintWireftameIcpPayloadBuilder extends AbstractPayloadBuilder<DfUpScreenPrintWireftameIcp> implements PayloadBuilder<DfUpScreenPrintWireftameIcp> {

    @Value("${app.mq.queues.all_data_queue}")
    private String queueName;

    @Override
    public Class<DfUpScreenPrintWireftameIcp> supportedType() {
        return DfUpScreenPrintWireftameIcp.class;
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
    public Object buildPayload(DfUpScreenPrintWireftameIcp e) {
        Map<String, Object> msg = new HashMap<>();
        msg.put("CheckDevCode", null);                     // 要求：null
        msg.put("ItemName", e.getTestProject());           // testProject
        msg.put("CheckType", e.getState());                // 先默认映射 state（如需改为其他字段请告知）
        msg.put("MachineCode", e.getMachineCode());        // 机台号
        msg.put("ProcessNO", e.getProcess());              // 工序
        msg.put("CheckTime", format(e.getDate()));         // yyyy-MM-dd HH:mm:ss

        List<Map<String, Object>> items = new ArrayList<>();
        addItem(items, "左上R角", e.getR1());
        addItem(items, "右上R角", e.getR2());
        addItem(items, "左下R角", e.getR3());
        addItem(items, "右下R角", e.getR4());

        addItem(items, "上长边左", e.getUpperLongSide1());
        addItem(items, "上长边中", e.getUpperLongSide2());
        addItem(items, "上长边右", e.getUpperLongSide3());

        addItem(items, "下长边左", e.getLowerLongSide1());
        addItem(items, "中长边", e.getLowerLongSide2());
        addItem(items, "右长边", e.getLowerLongSide3());

        addItem(items, "凹槽短边1", e.getShortDegeGroove1());
        addItem(items, "凹槽短边2", e.getShortDegeGroove2());
        addItem(items, "凹槽短边3", e.getNoShortDegeGroove1()); // 注意：实体字段名与中文标题对照
        addItem(items, "凹槽短边4", e.getNoShortDegeGroove2());

        addItem(items, "凹槽", e.getDegeGroove());

        addItem(items, "外形长1", e.getLongAppearance1());
        addItem(items, "外形长2", e.getLongAppearance2());
        addItem(items, "外形宽1", e.getExteriorWidth());
        addItem(items, "外形宽2", e.getExteriorWidth2());

        addItem(items, "max", e.getMaxValue());

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