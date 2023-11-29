package com.project.selenium;

import com.project.data.DateRange;
import com.project.data.Menu;
import com.project.data.UserInfo;
import com.project.page.object.AllocationDataPopup;
import com.project.page.object.AllocationPage;
import com.project.page.object.MainPage;
import com.project.page.object.base.Header;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.MalformedURLException;
import java.net.URL;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PageObjectTest {

    private final Logger log = LoggerFactory.getLogger(PageObjectTest.class);

    private final UserInfo userInfo;
    private final WebDriver webDriver;

    private final String mainUrl;
    private final Menu<AllocationPage> allocationPageMenu;
    private final MainPage mainPage;



    @Autowired
    public PageObjectTest(UserInfo userInfo, WebDriver webDriver, MainPage mainPage,
                          Menu<AllocationPage> allocationPageMenu,
                          @Value("${dev.scraper.mainUrl}")String mainUrl)
    {
        this.userInfo = userInfo;
        this.webDriver = webDriver;
        this.mainUrl = mainUrl;
        this.mainPage = mainPage;
        this.allocationPageMenu = allocationPageMenu;
    }

    @BeforeEach
    public void beforePageTest(){
        webDriver.get(mainUrl);
    }


    @Test
    @DisplayName("페이지 접속 테스트")
    public void domainTest() throws MalformedURLException {
        String expectUrlToDomain = new URL(mainUrl).getHost();
        String currentUrlToDomain = new URL(webDriver.getCurrentUrl()).getHost();

        log.info("예상 URL : {}", expectUrlToDomain);
        log.info("현재 URL : {}", currentUrlToDomain);

        Assertions.assertEquals(expectUrlToDomain, currentUrlToDomain);
    }

    @Test
    @DisplayName("메인 페이지 로그인 테스트")
    public void loginTest(){
        mainPage.login(userInfo);
    }

    @Test
    @DisplayName("배차내역조회 페이지 이동 테스트")
    public void navigateToAllocationPageTest() {
        AllocationPage page = mainPage.login(userInfo).toHeader()
                .goToPageByMyPageMenu(allocationPageMenu);
    }

    @Test
    @DisplayName("배차내역조회 검색 버튼 테스트")
    public void dataSearchButtonTest() {
        Header header = mainPage.login(userInfo)
                .toHeader();

        header.goToPageByMyPageMenu(allocationPageMenu)
                .setDateRange(DateRange.of(2023, 11))
                .clickSearchButton();
    }

    @Test
    @DisplayName("데이터 팝업창 테스트")
    public void allocationDataPopupOpenTest() {
        Header header = mainPage.login(userInfo)
                .toHeader();

        AllocationDataPopup dataPopup =
                header.goToPageByMyPageMenu(allocationPageMenu)
                .setDateRange(DateRange.of(2023, 11))
                .clickSearchButton()
                .openAllocationDataPopupByOrderCodeIndex(0);
    }

    @AfterEach
    public void afterPageTest() throws InterruptedException {
        Thread.sleep(5000);
        webDriver.close();
    }
}
