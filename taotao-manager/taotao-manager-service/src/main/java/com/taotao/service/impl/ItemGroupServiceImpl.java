package com.taotao.service.impl;

import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParamGroup;
import com.taotao.pojo.TbItemParamKey;
import com.taotao.service.ItemGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemGroupServiceImpl implements ItemGroupService {
    @Autowired
    private TbItemParamMapper tbItemParamMapper;

    @Override
    public TaotaoResult findTbItemGroupByCId(Long cId) {
        List<TbItemParamGroup> groups = tbItemParamMapper.findTbItemGroupByCId(cId);
        if (groups.size() <= 0) {
            return TaotaoResult.build(500, "该商品分类下没有规格参数组");
        }
        for (TbItemParamGroup group:groups){
            List<TbItemParamKey> paramkeys = tbItemParamMapper.findTbItemParamKeyByGroupId(group.getId());
            group.setParamKeys(paramkeys);
        }
        return TaotaoResult.ok(groups);
    }

    @Override
    public TaotaoResult addGroup(Long cId, String params) {
        if (params == null || "".equals(params)) {
            return TaotaoResult.build(500, "添加规格参数失败");
        }
        List<TbItemParamGroup> groups = new ArrayList<>();
        String[] groupAndKey = params.split("clive");
        for (String str : groupAndKey) {
            TbItemParamGroup group = new TbItemParamGroup();
            group.setItemCatId(cId);
            String[] key = str.split(",");
            group.setGroupName(key[0]);
//            在这里插入组信息
            int i = tbItemParamMapper.addGroup(group);
            if (i <= 0) {
                return TaotaoResult.build(500, "添加商品规格参数失败");
            }

            List<TbItemParamKey> paramKeys = new ArrayList<>();
            for (int j = 1; j < key.length; j++) {
                TbItemParamKey tbItemParamKey = new TbItemParamKey();
                tbItemParamKey.setParamName(key[j]);
                tbItemParamKey.setParamGroup(group);
                tbItemParamKey.setGroupId(group.getId());
//                建立关系
                paramKeys.add(tbItemParamKey);
            }
//            建立关系
            group.setParamKeys(paramKeys);
            int x = tbItemParamMapper.addGroupKey(paramKeys);
            if (x <= 0) {
                return TaotaoResult.build(500, "添加商品规格参数失败");
            }
        }
        return TaotaoResult.build(200, "添加规格参数成功");

    }
}
