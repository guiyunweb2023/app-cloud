package org.example.admin.modules.admin.mapper;

import org.example.admin.modules.admin.entity.ResourceAuthority;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ResourceAuthorityMapper extends Mapper<ResourceAuthority> {
    public void deleteByAuthorityIdAndResourceType(@Param("authorityId")String authorityId,@Param("resourceType") String resourceType);
}