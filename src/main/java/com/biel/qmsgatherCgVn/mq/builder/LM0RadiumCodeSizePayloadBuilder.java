package com.biel.qmsgatherCgVn.mq.builder;

import com.biel.qmsgatherCgVn.domain.DfUpChamferHypotenuse;
import com.biel.qmsgatherCgVn.domain.DfUpLM0RadiumSize;
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
 * @DATE 2025/9/25 19:29
 */
@Component
public class LM0RadiumCodeSizePayloadBuilder  extends AbstractPayloadBuilder<DfUpLM0RadiumSize> implements PayloadBuilder<DfUpLM0RadiumSize> {

    @Value("${app.mq.queues.all_data_queue}")
    private String LM0RadiumSizeQueue;
    @Override
    public Class<DfUpLM0RadiumSize> supportedType() {
        return DfUpLM0RadiumSize.class;
    }

    @Override
    public String queueName() {
        return LM0RadiumSizeQueue;
    }

    @Override
    public SendMode sendMode() {
        return SendMode.PER_ITEM;
    }

    @Override
    public Object buildPayload(DfUpLM0RadiumSize e) {
        Map<String, Object> msg = new HashMap<>();
        msg.put("CheckDevCode", null);               // 按既有约定：null
        msg.put("ItemName", e.getTestProject());     // 测试项目
        msg.put("CheckType", CheckTypeConfig.mapForPayload(e.getState()));
        msg.put("MachineCode", extractNumberAfterDash(e.getMachineCode()));
        msg.put("ProcessNO", CheckProcessName.mapForProcessName(e.getProcess()));
        msg.put("CheckTime", format(e.getDate()));   // yyyy-MM-dd HH:mm:ss

        List<Map<String, Object>> items = new ArrayList<>();
        addItem(items, "外形长", e.getExternalLong());
        addItem(items, "外形宽", e.getExternalWidth());
        addItem(items, "码长", e.getCodeLength());
        addItem(items, "码宽", e.getCodeWidth());
        addItem(items, "码到视窗", e.getCodeToView());
        addItem(items, "码到中心X", e.getCodeToCenterX());
        addItem(items, "码到中心Y", e.getCodeToCenterY());
        addItem(items, "苹果上下长度", e.getAppleUpDownLength());
        addItem(items, "苹果宽", e.getAppleWidth());
        addItem(items, "叶子下端到上端水平距离", e.getLeafDownToUpHorizontalDistance());
        addItem(items, "苹果logo主体上端到底端", e.getAppleLogoBodyUpToDown());
        addItem(items, "叶子下端到苹果logo主体下端距离", e.getLeafDownToAppleLogoBodyDown());
        addItem(items, "苹果底到二维码", e.getAppleBottomToQrCode());
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

    // 提取“-”后末尾的数字；如“38#-3” -> “3”；返回字符串形式；不符合格式时返回 null
    private String extractNumberAfterDash(String machineCode) {
        if (machineCode == null) return null;
        java.util.regex.Matcher m = java.util.regex.Pattern
                .compile("-(\\d+)\\s*$")
                .matcher(machineCode);
        if (m.find()) {
            return m.group(1);
        }
        int idx = machineCode.lastIndexOf('-');
        return (idx >= 0 && idx < machineCode.length() - 1)
                ? machineCode.substring(idx + 1).trim()
                : null;
    }
}
