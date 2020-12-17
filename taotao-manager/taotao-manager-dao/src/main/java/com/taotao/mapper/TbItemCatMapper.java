package com.taotao.mapper;


import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.ZtreeResult;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TbItemCatMapper {
    @Select("SELECT * FROM tbitemcat WHERE parentId = #{id}")
    List<TbItemCat> findTbItemCatByParentId(Long id);
}