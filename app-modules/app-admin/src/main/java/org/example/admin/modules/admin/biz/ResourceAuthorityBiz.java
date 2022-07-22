package org.example.admin.modules.admin.biz;

import org.example.common.biz.BaseBiz;
import org.example.admin.modules.admin.entity.ResourceAuthority;
import org.example.admin.modules.admin.mapper.ResourceAuthorityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Ace on 2017/6/19.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceAuthorityBiz extends BaseBiz<ResourceAuthorityMapper,ResourceAuthority> {
}
