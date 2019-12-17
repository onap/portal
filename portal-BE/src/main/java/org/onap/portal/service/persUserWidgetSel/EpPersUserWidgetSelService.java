package org.onap.portal.service.persUserWidgetSel;

import org.onap.portal.domain.db.ep.EpPersUserWidgetSel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EpPersUserWidgetSelService {

    private final EpPersUserWidgetSelDao epPersUserWidgetSelDao;

    @Autowired
    public EpPersUserWidgetSelService(EpPersUserWidgetSelDao epPersUserWidgetSelDao) {
        this.epPersUserWidgetSelDao = epPersUserWidgetSelDao;
    }

    public void deleteById(Long id) {
        epPersUserWidgetSelDao.deleteById(id);
    }

    public EpPersUserWidgetSel saveAndFlush(EpPersUserWidgetSel epPersUserWidgetSel) {
        return epPersUserWidgetSelDao.saveAndFlush(epPersUserWidgetSel);
    }

    public Optional<List<EpPersUserWidgetSel>> getEpPersUserWidgetSelForUserIdAndWidgetId(Long id, Long widgetId) {
        return epPersUserWidgetSelDao.getEpPersUserWidgetSelForUserIdAndWidgetId(id, widgetId);
    }
}
