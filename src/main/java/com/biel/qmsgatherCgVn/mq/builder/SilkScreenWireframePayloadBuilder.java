package com.biel.qmsgatherCgVn.mq.builder;

import com.biel.qmsgatherCgVn.domain.DfUpSilkScreenWireframe;
import com.biel.qmsgatherCgVn.mq.AbstractPayloadBuilder;
import com.biel.qmsgatherCgVn.mq.PayloadBuilder;
import com.biel.qmsgatherCgVn.mq.SendMode;
import com.biel.qmsgatherCgVn.util.CheckTypeConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SilkScreenWireframePayloadBuilder extends AbstractPayloadBuilder<DfUpSilkScreenWireframe> implements PayloadBuilder<DfUpSilkScreenWireframe> {

    @Value("${app.mq.queues.all_data_queue}")
    private String queueName;

    @Override
    public Class<DfUpSilkScreenWireframe> supportedType() {
        return DfUpSilkScreenWireframe.class;
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
    public Object buildPayload(DfUpSilkScreenWireframe e) {
        Map<String, Object> msg = new HashMap<>();
        msg.put("CheckDevCode", null);
        msg.put("ItemName", e.getTestProject());
        msg.put("CheckType", CheckTypeConfig.mapForPayload(e.getState()));
        msg.put("MachineCode", e.getMachineCode());
        msg.put("ProcessNO", e.getProcess());
        msg.put("CheckTime", format(e.getDate()));

        List<Map<String, Object>> items = new ArrayList<>();
        addItem(items, "1点y2", e.getOnePointy2());
        addItem(items, "2点y1", e.getTwoPointy1());
        addItem(items, "3点x", e.getThreePointx());
        addItem(items, "4点x", e.getFourPointx());
        addItem(items, "5点y1", e.getFivePointy1());
        addItem(items, "6点y2", e.getSixPointy2());
        addItem(items, "7点x", e.getSevenPointx());
        addItem(items, "8点x", e.getEightPointx());

        addItem(items, "左上R角", e.getR1());
        addItem(items, "右上R角", e.getR2());
        addItem(items, "左下R角", e.getR3());
        addItem(items, "右下R角", e.getR4());

        addItem(items, "2D镭码位置", e.getTwoRadiumCode());

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