package com.taotao.portal.controller;

import com.taotao.content.service.ItemContentService;
import com.taotao.pojo.Ad1Node;
import com.taotao.pojo.ItemCatResult;
import com.taotao.service.ItemCatService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ItemCatService itemCatService;
    @Autowired
    private ItemContentService itemContentService;

    @RequestMapping("/index")
    public String showIndex(Model model){
        List<Ad1Node> nodes = itemContentService.showAd1Node();
        String ad1 = JsonUtils.objectToJson(nodes);
        model.addAttribute("ad1",ad1);
        return "index";
    }
@RequestMapping("/itemCat/all")
    @ResponseBody
    public String showItemCat(){
    ItemCatResult result=   itemCatService.getItemCats();
    String json =  JsonUtils.objectToJson(result);
        return json;
}
}
