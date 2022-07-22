package org.example.admin.modules.admin.biz;

import org.example.common.biz.BaseBiz;
import org.example.admin.modules.admin.entity.GateLog;
import org.example.admin.modules.admin.mapper.GateLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ${DESCRIPTION}
 *
 * @author the sun
 * @create 2017-07-01 14:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GateLogBiz extends BaseBiz<GateLogMapper,GateLog> {

    @Override
    public void insert(GateLog entity) {
        mapper.insert(entity);
    }

    @Override
    public void insertSelective(GateLog entity) {
        mapper.insertSelective(entity);
    }
}
