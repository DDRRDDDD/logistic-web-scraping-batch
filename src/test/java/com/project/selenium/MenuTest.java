package com.project.selenium;

import com.project.scraper.metadata.Menu;
import com.project.page.object.AllocationPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MenuTest {

    private final Logger log = LoggerFactory.getLogger(MenuTest.class);
    private final Menu<AllocationPage> allocationPageMenu;

    @Autowired
    public MenuTest(Menu<AllocationPage> allocationPageMenu){
        this.allocationPageMenu = allocationPageMenu;
    }

    @Test
    @DisplayName("배차조회 페이지의 정보가 잘 들어오는가?")
    public void menuTest1(){
        String optionText = allocationPageMenu.getOption();
        Class<?> pageClass = allocationPageMenu.getPageClass();

        log.info("optionText => {} / pageClass => {}", optionText, pageClass.toString());

        Assertions.assertEquals("배차내역조회", optionText);
        Assertions.assertEquals(AllocationPage.class, pageClass);
    }
}
