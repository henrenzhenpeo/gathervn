package com.biel.qmsgatherCgVn.listener;

import com.biel.qmsgatherCgVn.domain.DfUpBottomGapChamfer;
import com.biel.qmsgatherCgVn.domain.DfUpChamferHypotenuse;
import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintWireftameIcp;
import com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingVarnish; // 新增
import com.biel.qmsgatherCgVn.domain.DfUpSilkScreenWireframe; // 新增
import com.biel.qmsgatherCgVn.domain.DfUpWireFrameInkClimbing; // 新增
import com.biel.qmsgatherCgVn.event.DataImportedEvent;
import com.biel.qmsgatherCgVn.mq.ActiveMqDispatcher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ActiveMqDataImportedListener {

    @Resource
    private ActiveMqDispatcher dispatcher;

    @EventListener(condition = "#root.args[0].entityType == T(com.biel.qmsgatherCgVn.domain.DfUpBottomGapChamfer)")
    public void onBottomGapChamfer(DataImportedEvent<DfUpBottomGapChamfer> event) {
        List<DfUpBottomGapChamfer> batch = event.getPayload();
        if (batch == null || batch.isEmpty()) return;
        dispatcher.dispatch(batch, DfUpBottomGapChamfer.class);
    }

    @EventListener(condition = "#root.args[0].entityType == T(com.biel.qmsgatherCgVn.domain.DfUpChamferHypotenuse)")
    public void onChamferHypotenuse(DataImportedEvent<DfUpChamferHypotenuse> event) {
        List<DfUpChamferHypotenuse> batch = event.getPayload();
        if (batch == null || batch.isEmpty()) return;
        dispatcher.dispatch(batch, DfUpChamferHypotenuse.class);
    }

    @EventListener(condition = "#root.args[0].entityType == T(com.biel.qmsgatherCgVn.domain.DfUpScreenPrintWireftameIcp)")
    public void onScreenPrintWireftameIcp(DataImportedEvent<DfUpScreenPrintWireftameIcp> event) {
        List<DfUpScreenPrintWireftameIcp> batch = event.getPayload();
        if (batch == null || batch.isEmpty()) return;
        dispatcher.dispatch(batch, DfUpScreenPrintWireftameIcp.class);
    }

    // 新增：丝印BM2事件分发
    @EventListener(condition = "#root.args[0].entityType == T(com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingbm)")
    public void onScreenPrintingBm(DataImportedEvent<com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingbm> event) {
        List<com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingbm> batch = event.getPayload();
        if (batch == null || batch.isEmpty()) return;
        dispatcher.dispatch(batch, com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingbm.class);
    }

    // 新增：丝印光油事件分发
    @EventListener(condition = "#root.args[0].entityType == T(com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingVarnish)")
    public void onScreenPrintingVarnish(DataImportedEvent<com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingVarnish> event) {
        List<com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingVarnish> batch = event.getPayload();
        if (batch == null || batch.isEmpty()) return;
        dispatcher.dispatch(batch, com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingVarnish.class);
    }

    // 新增：丝印线框事件分发
    @EventListener(condition = "#root.args[0].entityType == T(com.biel.qmsgatherCgVn.domain.DfUpSilkScreenWireframe)")
    public void onSilkScreenWireframe(DataImportedEvent<DfUpSilkScreenWireframe> event) {
        List<DfUpSilkScreenWireframe> batch = event.getPayload();
        if (batch == null || batch.isEmpty()) return;
        dispatcher.dispatch(batch, DfUpSilkScreenWireframe.class);
    }

    // 新增：线框油墨爬高事件分发
    @EventListener(condition = "#root.args[0].entityType == T(com.biel.qmsgatherCgVn.domain.DfUpWireFrameInkClimbing)")
    public void onWireFrameInkClimbing(DataImportedEvent<DfUpWireFrameInkClimbing> event) {
        List<DfUpWireFrameInkClimbing> batch = event.getPayload();
        if (batch == null || batch.isEmpty()) return;
        dispatcher.dispatch(batch, DfUpWireFrameInkClimbing.class);
    }
}