package com.taotao.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemCatService;
import com.taotao.service.JedisClient;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("ITEMCAT")
    private String ITEMCAT;

    @Override
    public List<ZtreeResult> getZtreeResult(Long id) {
        List<TbItemCat> tbItemCats = tbItemCatMapper.findTbItemCatByParentId(id);
        List<ZtreeResult> results = new ArrayList<>();
        for (TbItemCat tbItemCat : tbItemCats) {
            ZtreeResult result = new ZtreeResult();
            result.setId(tbItemCat.getId());
            result.setName(tbItemCat.getName());
            result.setIsParent(tbItemCat.getIsParent());
            results.add(result);
        }
        return results;
    }

    @Override
    public ItemCatResult getItemCats() {

        ItemCatResult result = new ItemCatResult();
        String json = jedisClient.get(ITEMCAT);
        if (StringUtils.isNotBlank(json)){
            List list = JsonUtils.jsonToPojo(json,List.class);
            result.setData(list);
            return result;
        }
        //商品类目展示 加入缓存
        List<?> list = getItemCatList(0L);
        result.setData(list);
        jedisClient.set(ITEMCAT,JsonUtils.objectToJson(list));
        return result;
    }

    private List getItemCatList(Long parentId) {
        int count = 0;
        List list = new ArrayList();
//        这里会得到一级类目
        List<TbItemCat> itemCats = tbItemCatMapper.findTbItemCatByParentId(parentId);
//       遍历一级类目
        for (TbItemCat itemCat : itemCats) {
            ItemCat item = new ItemCat();
//            判断他是父级目录还是最底级目录
            if (itemCat.getIsParent()) {
//                代表一定是第一级或者第二级目录
                item.setUrl("/products/"+itemCat.getId()+".html");
                if (itemCat.getParentId() == 0) {
//                一定是第一级类目
//                    "<a href='/products/1.html'>图书、音像、电子书刊</a>",
                    item.setName("<a href='/products/"+itemCat.getId()+".html' >"+itemCat.getName()+"</a>");
                } else {
//                一定是第二级类目
                    item.setName(itemCat.getName());
                }
                item.setItem(getItemCatList(itemCat.getId()));
                list.add(item);
                count++;
                if (parentId==0&&count>=14){
                    break;
                }

            } else {
                list.add("/products/"+itemCat.getId()+".html|"+itemCat.getName());
            }


        }
        return list;
    }
}
