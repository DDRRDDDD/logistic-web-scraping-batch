package com.project.data;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class UserInfo {

    private final String userId;

    private final String userPassword;
    public UserInfo(@Value("${dev.scraper.id}") String userId,
                    @Value("${dev.scraper.pw}") String userPassword)
    {
        this.userId = userId;
        this.userPassword = userPassword;
    }

}
