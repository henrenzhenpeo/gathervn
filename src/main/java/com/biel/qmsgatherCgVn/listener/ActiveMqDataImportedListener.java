package com.biel.qmsgatherCgVn.listener;

import com.biel.qmsgatherCgVn.domain.*;
import com.biel.qmsgatherCgVn.event.DataImportedEvent;
import com.biel.qmsgatherCgVn.mq.ActiveMqDispatcher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ActiveMqDataImportedListener {

    @Resource
    private ActiveMqDispatcher dispatcher;

    @EventListener(condition = "#root.args[0].entityType == T(com.biel.qmsgatherCgVn.domain.DfUpBottomGapChamfer)")
    public void onBottomGapChamfer(DataImportedEvent<DfUpBottomGapChamfer> event) {
        List<DfUpBottomGapChamfer> batch = event.getPayload();
        if (batch == null || batch.isEmpty()) return;
        try {
            dispatcher.dispatch(batch, DfUpBottomGapChamfer.class);
            log.info("[DfUpBottomGapChamfer] ActiveMQ转发成功：批次条数={}", batch.size());
        } catch (Exception e) {
            log.error("[DfUpBottomGapChamfer] ActiveMQ转发失败：", e);
        }
    }

    @EventListener(condition = "#root.args[0].entityType == T(com.biel.qmsgatherCgVn.domain.DfUpChamferHypotenuse)")
    public void onChamferHypotenuse(DataImportedEvent<DfUpChamferHypotenuse> event) {
        List<DfUpChamferHypotenuse> batch = event.getPayload();
        if (batch == null || batch.isEmpty()) return;
        try {
            dispatcher.dispatch(batch, DfUpChamferHypotenuse.class);
            log.info("[DfUpChamferHypotenuse] ActiveMQ转发成功：批次条数={}", batch.size());
        } catch (Exception e) {
            log.error("[DfUpChamferHypotenuse] ActiveMQ转发失败：", e);
        }
    }

    @EventListener(condition = "#root.args[0].entityType == T(com.biel.qmsgatherCgVn.domain.DfUpScreenPrintWireftameIcp)")
    public void onScreenPrintWireftameIcp(DataImportedEvent<DfUpScreenPrintWireftameIcp> event) {
        List<DfUpScreenPrintWireftameIcp> batch = event.getPayload();
        if (batch == null || batch.isEmpty()) return;
        try {
            dispatcher.dispatch(batch, DfUpScreenPrintWireftameIcp.class);
            log.info("[DfUpScreenPrintWireftameIcp] ActiveMQ转发成功：批次条数={}", batch.size());
        } catch (Exception e) {
            log.error("[DfUpScreenPrintWireftameIcp] ActiveMQ转发失败：", e);
        }
    }

    // 新增：丝印BM2事件分发
    @EventListener(condition = "#root.args[0].entityType == T(com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingbm)")
    public void onScreenPrintingBm(DataImportedEvent<com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingbm> event) {
        List<com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingbm> batch = event.getPayload();
        if (batch == null || batch.isEmpty()) return;
        try {
            dispatcher.dispatch(batch, DfUpScreenPrintingbm.class);
            log.info("[DfUpScreenPrintingbm] ActiveMQ转发成功：批次条数={}", batch.size());
        } catch (Exception e) {
            log.error("[DfUpScreenPrintWireftameIcp] ActiveMQ转发失败：", e);
        }
    }

    // 新增：丝印光油事件分发
    @EventListener(condition = "#root.args[0].entityType == T(com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingVarnish)")
    public void onScreenPrintingVarnish(DataImportedEvent<com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingVarnish> event) {
        List<com.biel.qmsgatherCgVn.domain.DfUpScreenPrintingVarnish> batch = event.getPayload();
        if (batch == null || batch.isEmpty()) return;
        try {
            dispatcher.dispatch(batch, DfUpScreenPrintingVarnish.class);
            log.info("[DfUpScreenPrintingVarnish] ActiveMQ转发成功：批次条数={}", batch.size());
        } catch (Exception e) {
            log.error("[DfUpScreenPrintWireftameIcp] ActiveMQ转发失败：", e);
        }
    }

    // 新增：丝印线框事件分发
    @EventListener(condition = "#root.args[0].entityType == T(com.biel.qmsgatherCgVn.domain.DfUpSilkScreenWireframe)")
    public void onSilkScreenWireframe(DataImportedEvent<DfUpSilkScreenWireframe> event) {
        List<DfUpSilkScreenWireframe> batch = event.getPayload();
        if (batch == null || batch.isEmpty()) return;
        try {
            dispatcher.dispatch(batch, DfUpSilkScreenWireframe.class);
            log.info("[DfUpSilkScreenWireframe] ActiveMQ转发成功：批次条数={}", batch.size());
        } catch (Exception e) {
            log.error("[DfUpSilkScreenWireframe] ActiveMQ转发失败：", e);
        }
    }

    // 新增：线框油墨爬高事件分发
    @EventListener(condition = "#root.args[0].entityType == T(com.biel.qmsgatherCgVn.domain.DfUpWireFrameInkClimbing)")
    public void onWireFrameInkClimbing(DataImportedEvent<DfUpWireFrameInkClimbing> event) {
        List<DfUpWireFrameInkClimbing> batch = event.getPayload();
        if (batch == null || batch.isEmpty()) return;
        try {
            dispatcher.dispatch(batch, DfUpWireFrameInkClimbing.class);
            log.info("[DfUpWireFrameInkClimbing] ActiveMQ转发成功：批次条数={}", batch.size());
        } catch (Exception e) {
            log.error("[DfUpWireFrameInkClimbing] ActiveMQ转发失败：", e);
        }
    }

    // 新增：SSB3D机事件分发
    @EventListener(condition = "#root.args[0].entityType == T(com.biel.qmsgatherCgVn.domain.DfUpSSBThreeDMachine)")
    public void onSSBThreeDMachine(DataImportedEvent<DfUpSSBThreeDMachine> event) {
        List<DfUpSSBThreeDMachine> batch = event.getPayload();
        if (batch == null || batch.isEmpty()) return;
        try {
            dispatcher.dispatch(batch, DfUpSSBThreeDMachine.class);
            log.info("[DfUpSSBThreeDMachine] ActiveMQ转发成功：批次条数={}", batch.size());
        } catch (Exception e) {
            log.error("[DfUpSSBThreeDMachine] ActiveMQ转发失败：", e);
        }
    }
}