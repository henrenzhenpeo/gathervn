package com.biel.qmsgatherCgVn.mq.builder;

import com.biel.qmsgatherCgVn.domain.DfUpChamferHypotenuse;
import com.biel.qmsgatherCgVn.mq.AbstractPayloadBuilder;
import com.biel.qmsgatherCgVn.mq.PayloadBuilder;
import com.biel.qmsgatherCgVn.mq.SendMode;
import com.biel.qmsgatherCgVn.util.CheckMachineCode;
import com.biel.qmsgatherCgVn.util.CheckProcessName;
import com.biel.qmsgatherCgVn.util.CheckTypeConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return SendMode.PER_ITEM; // 批量发送一次
    }

    @Override
    public Object buildPayload(DfUpChamferHypotenuse e) {
        Map<String, Object> msg = new HashMap<>();
        msg.put("CheckDevCode", null);               // 按既有约定：null
        msg.put("ItemName", e.getTestProject());     // 测试项目
        msg.put("CheckType", CheckTypeConfig.mapForPayload(e.getState()));
        msg.put("MachineCode", transformMachineCode(e.getMachineCode()));
        msg.put("ProcessNO", CheckProcessName.mapForProcessName(e.getProcess()));
        msg.put("CheckTime", format(e.getDate()));   // yyyy-MM-dd HH:mm:ss

        List<Map<String, Object>> items = new ArrayList<>();
        addItem(items, "短边右上1", e.getShortUr1());
        addItem(items, "短边右下4", e.getShortLr4());
        addItem(items, "短边左上8", e.getShortUl8());
        addItem(items, "短边左下5", e.getShortLl5());
        addItem(items, "长边右上2", e.getLongUr2());
        addItem(items, "长边右下3", e.getLongLr3());
        addItem(items, "长边左上7", e.getLongUl7());
        addItem(items, "长边左下6", e.getLongLl6());
        addItem(items, "平均值", e.getAvg());
        addItem(items, "1-4差值", e.getStd1to4());
        addItem(items, "2-7差值", e.getStd2to7());
        addItem(items, "3-6差值", e.getStd3to6());
        addItem(items, "5-8差值", e.getStd5to8());
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

    /**
     * 转换 MachineCode，将 "-1" 替换为 "-L"，将 "-2" 替换为 "-R"
     * @param originalMachineCode 原始的 MachineCode
     * @return 转换后的 MachineCode
     */
    private String transformMachineCode(String originalMachineCode) {
        if (originalMachineCode == null) {
            return null;
        }

        String transformedCode = originalMachineCode;

        // 将 "-1" 替换为 "-L"
        if (transformedCode.endsWith("-1")) {
            transformedCode = transformedCode.replace("-1", "-L");
        }
        // 将 "-2" 替换为 "-R"
        else if (transformedCode.endsWith("-2")) {
            transformedCode = transformedCode.replace("-2", "-R");
        }

        return transformedCode;
    }
}