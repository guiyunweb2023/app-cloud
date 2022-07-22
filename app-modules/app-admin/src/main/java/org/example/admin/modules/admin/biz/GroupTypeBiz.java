package org.example.admin.modules.admin.biz;

import org.example.common.biz.BaseBiz;
import org.example.admin.modules.admin.entity.GroupType;
import org.example.admin.modules.admin.mapper.GroupTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ${DESCRIPTION}
 *
 * @author the sun
 * @create 2017-06-12 8:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GroupTypeBiz extends BaseBiz<GroupTypeMapper,GroupType> {
}
