package com.hengxunda.app.service;

import com.hengxunda.app.dto.LoginDto;
import com.hengxunda.app.dto.RegisterDto;
import com.hengxunda.app.dto.UpdateAppDto;
import com.hengxunda.app.dto.UpdateLoginPasswordDto;
import com.hengxunda.app.vo.MyInfoVo;
import com.hengxunda.app.vo.UserInfoVo;
import com.hengxunda.dao.entity.AppVersion;
import com.hengxunda.dao.entity.User;

import java.util.List;

public interface IUserService {

    User getUserById(String id);

    User getUserByPhone(String phone);

    User getUserByUid(String uid);

    void register(RegisterDto registerDto, User refereeUser);

    UserInfoVo login(LoginDto loginDto);

    MyInfoVo getById();

    void updateNick(String nick);

    void updateName(String name);

    void checkPassword(String password);

    void logOut();

    void changePhone(String nationalCode, String phone);

    void forgetPassword(UpdateLoginPasswordDto updateLoginPasswordDto);

    List<AppVersion> appInfo();

    AppVersion getAppBySourceAndOsType(UpdateAppDto updateAppDto);
}
