package com.project.selenium;

import com.project.data.UserInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserInfoTest {

    @Autowired
    private UserInfo userInfo;

    @Value("${dev.scraper.id}")
    private String testId;

    @Value("${dev.scraper.pw}")
    private String testPw;

    @Test
    @DisplayName("프로퍼티 유저정보 잘 불러오는가?")
    public void userInfoTest1(){
        String userId = userInfo.getUserId();
        String userPassword = userInfo.getUserPassword();

        Assertions.assertDoesNotThrow(()->{userInfo.getUserId();});
        Assertions.assertDoesNotThrow(()->{userInfo.getUserPassword();});
    }

    @Test
    @DisplayName("프로퍼티 유저정보가 일치하는가?")
    public void userInfoTest2(){
        String userId = userInfo.getUserId();
        String userPassword = userInfo.getUserPassword();

        Assertions.assertEquals(this.testId, userId);
        Assertions.assertEquals(this.testPw, userPassword);
    }
}
