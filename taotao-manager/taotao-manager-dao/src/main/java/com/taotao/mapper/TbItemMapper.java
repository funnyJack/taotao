package com.taotao.mapper;


import com.taotao.pojo.TbItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface TbItemMapper {
//    查询数据库tbitem表中根据商品id查询商品信息
    TbItem findTbTtemById(Long itemId);
//    查询数据库tbitem表中的总记录条数
    @Select("SELECT count(*) FROM tbitem")
    int findTbItemByCount();
    @Select("SELECT * from tbitem limit #{index},#{pageSize}")
    List<TbItem> finTbItemByPage(@Param("index") int index,@Param("pageSize") int pagaSize);

    int updateItemByIds(@Param("ids")List<Long> ids,@Param("type") int type,@Param("date") Date date);
}