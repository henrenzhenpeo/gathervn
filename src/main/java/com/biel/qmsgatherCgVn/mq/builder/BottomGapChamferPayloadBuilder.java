package com.biel.qmsgatherCgVn.mq.builder;

import com.biel.qmsgatherCgVn.domain.DfUpBottomGapChamfer;
import com.biel.qmsgatherCgVn.mq.AbstractPayloadBuilder;
import com.biel.qmsgatherCgVn.mq.PayloadBuilder;
import com.biel.qmsgatherCgVn.mq.SendMode;
import com.biel.qmsgatherCgVn.util.CheckProcessName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

import com.biel.qmsgatherCgVn.util.CheckTypeConfig; // 新增：公共映射工具

/**
 * cnc3d底倒角数据转换器
 */
@Component
public class BottomGapChamferPayloadBuilder extends AbstractPayloadBuilder<DfUpBottomGapChamfer> implements PayloadBuilder<DfUpBottomGapChamfer> {

    @Value("${app.mq.queues.all_data_queue}")
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
        // 原：msg.put("CheckType", e.getRemark());
        msg.put("CheckType", CheckTypeConfig.mapForPayload(e.getState())); // 统一映射（CPK->1，FAI->4，未知类型回退为原始字符串）
        msg.put("MachineCode", transformMachineCode(e.getMachineCode()));        // machineCode
        msg.put("ProcessNO", CheckProcessName.mapForProcessName(e.getProcess()));
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