package com.biel.qmsgatherCgVn.mq.builder;

import com.biel.qmsgatherCgVn.domain.DfUpChamferHypotenuse;
import com.biel.qmsgatherCgVn.domain.DfUpRadiumCodeSize;
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

/**
 * cnc3d镭码尺寸
 *
 * @Author mr.feng
 * @DATE 2025/9/19 14:56
 */
@Component
public class RediumCodeSizePayloadBuilder extends AbstractPayloadBuilder<DfUpRadiumCodeSize> implements PayloadBuilder<DfUpRadiumCodeSize> {

    @Value("${app.mq.queues.all_data_queue}")
    private String chamferHypotenuseQueue;
    @Override
    public Class<DfUpRadiumCodeSize> supportedType() {
        return DfUpRadiumCodeSize.class;
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
    public Object buildPayload(DfUpRadiumCodeSize e) {
        Map<String, Object> msg = new HashMap<>();
        msg.put("CheckDevCode", null);               // 按既有约定：null
        msg.put("ItemName", e.getTestProject());     // 测试项目
        msg.put("CheckType", CheckTypeConfig.mapForPayload(e.getState()));
        msg.put("MachineCode", transformMachineCode(e.getMachineCode()));
        msg.put("ProcessNO", CheckProcessName.mapForProcessName(e.getProcess()));
        msg.put("CheckTime", format(e.getDate()));   // yyyy-MM-dd HH:mm:ss

        List<Map<String, Object>> items = new ArrayList<>();
        addItem(items, "二维码长", e.getQrCodeLength());
        addItem(items, "二维码宽", e.getQrCodeWidth());
        addItem(items, "镭码下边到玻璃边距", e.getBarcodeToglass());
        addItem(items, "X白片镭码到玻璃中心X", e.getXWhitePlateToGlassCenter());
        addItem(items, "左边距", e.getLeftDistance());
        addItem(items, "右边距", e.getRightDistance());
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

    private String transformMachineCode(String code) {
        if (code == null) {
            return null;
        }
        // 检查字符串结尾并处理
        if (code.endsWith("-1")) {
            return code.substring(0, code.length() - 2) + "-L";
        } else if (code.endsWith("-2")) {
            return code.substring(0, code.length() - 2) + "-R";
        } else {
            return code; // 不匹配时返回原字符串
        }
    }
}
