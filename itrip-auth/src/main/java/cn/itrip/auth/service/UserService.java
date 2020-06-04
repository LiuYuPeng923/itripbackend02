package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;

public interface UserService {
    ItripUser findByUserCode(String userCode) throws Exception;

    ItripUser login(String name, String password) throws Exception;


    void creatUserByPhone(ItripUser user) throws Exception;

    boolean validatePhone(String phoneNum, String code) throws Exception;


    void creatUserByMail(ItripUser user) throws Exception;

    boolean validateMail(String mail, String code) throws Exception;


}
