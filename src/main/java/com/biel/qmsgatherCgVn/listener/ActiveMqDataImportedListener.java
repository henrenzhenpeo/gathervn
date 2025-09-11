package com.biel.qmsgatherCgVn.listener;

import com.biel.qmsgatherCgVn.domain.DfUpBottomGapChamfer;
import com.biel.qmsgatherCgVn.domain.DfUpChamferHypotenuse;
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


}