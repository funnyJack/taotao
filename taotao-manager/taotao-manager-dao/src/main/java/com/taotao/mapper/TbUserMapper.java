package com.taotao.mapper;


import com.taotao.pojo.TbUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TbUserMapper {
    @Select("SELECT count(*) from tbuser where userName = #{param}")
    int checkUserName(String param);

    @Select("SELECT count(*) from tbuser where phone = #{param}")
    int checkPhone(String param);

    @Select("SELECT count(*) from tbuser where email = #{param}")
    int checkEmail(String param);
@Insert("insert into tbuser (userName, passWord, phone, email, created, updated) values (#{userName},#{passWord},#{phone},#{email},#{created},#{updated})")
    int addUser(TbUser tbUser);
@Select("select * from tbuser where userName = #{userName} and passWord = #{passWord}")
    TbUser findUserByNameAndPass(@Param("userName") String userName,@Param("passWord") String passWord);
}