package com.taotao.sso.controller;

import com.taotao.constant.RedisConstant;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //限定请求方式为get请求
    @RequestMapping(value = "/check/{param}/{type}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public Object checkData(@PathVariable String param, @PathVariable Integer type,String callback) {
        TaotaoResult result = userService.getCheckDataResult(param, type);
        if (StringUtils.isNotBlank(callback)){
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return JsonUtils.objectToJson(result);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult register(TbUser tbUser) {
        TaotaoResult result = userService.addUser(tbUser);
        return result;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String userName, String passWord, HttpServletRequest request, HttpServletResponse response) {
        TaotaoResult result = userService.getToken(userName, passWord);
        CookieUtils.setCookie(request,response, RedisConstant.TT_TOKEN,result.getData().toString());
        return result;
    }
    @RequestMapping(value = "/token/{token}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public String getUserByToken(@PathVariable String token,String callback) {
        TaotaoResult result = userService.getUserByToken(token);
        if (StringUtils.isNotBlank(callback)){
            return callback+"("+JsonUtils.objectToJson(result)+");";
        }
        return JsonUtils.objectToJson(result);
    }
    @RequestMapping(value = "/logout/{token}", method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult logout(@PathVariable String token) {
        TaotaoResult result = userService.deleteToken(token);
        return result;
    }

}
