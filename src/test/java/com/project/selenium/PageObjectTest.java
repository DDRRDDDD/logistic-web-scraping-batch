package com.project.selenium;

import com.project.scraper.metadata.DateRange;
import com.project.scraper.metadata.Menu;
import com.project.scraper.metadata.UserInfo;
import com.project.page.object.AllocationDataPopup;
import com.project.page.object.AllocationPage;
import com.project.page.object.MainPage;
import com.project.page.base.Header;
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
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;


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
                .setDateRange(DateRange.ofMonth(2023, 11))
                .clickSearchButton();
    }

    @Test
    @DisplayName("데이터 팝업창 테스트")
    public void allocationDataPopupOpenTest() {
        Header header = mainPage.login(userInfo)
                .toHeader();

        AllocationDataPopup dataPopup =
                header.goToPageByMyPageMenu(allocationPageMenu)
                .setDateRange(DateRange.ofMonth(2023, 11))
                .clickSearchButton()
                .openAllocationDataPopupByOrderCodeIndex(0);
    }

    @Test
    @DisplayName("팝업 페이지에서 배차 데이터를 불러오는 테스트 (List)")
    public void extractAllocationDataTest(){
        Header header = mainPage.login(userInfo)
                .toHeader();

        AllocationDataPopup dataPopup =
                header.goToPageByMyPageMenu(allocationPageMenu)
                        .setDateRange(DateRange.ofMonth(2023, 11))
                        .clickSearchButton()
                        .openAllocationDataPopupByOrderCodeIndex(0);

        Map<String, String> data = dataPopup.extractAllocationData();
        data.keySet().forEach((key) -> log.info("{} : {}", key, data.get(key)));
    }


    @Test
    @DisplayName("11월 배차 정보 전부 불러오는 테스트 (List)")
    public void fetchNovemberAllocationData(){
        Header header = mainPage.login(userInfo)
                .toHeader();

        AllocationPage allocationPage = header.goToPageByMyPageMenu(allocationPageMenu);

        allocationPage.setDateRange(DateRange.ofMonth(2023, 11))
                    .clickSearchButton();


        int index = 0;
        List<Map<String, String>> resultMap = new ArrayList<>();
        AtomicBoolean isTrue = new AtomicBoolean(true);

        while(isTrue.get()){
            Optional.ofNullable(allocationPage.openAllocationDataPopupByOrderCodeIndex(index++))
                    .ifPresentOrElse(
                        (page) -> resultMap.add(page.extractAllocationData()),
                        () -> isTrue.set(false)
                    );
        }

        resultMap.forEach(
                (map) -> map.keySet().forEach(
                        (key) -> log.info("{} : {}\n", key, map.get(key))
                )
        );

        log.info("Data Count >>> {}", index);
    }


    @Test
    @DisplayName("만약 배차내역이 없는 날 조회 테스트")
    public void noDispatchRecordsOnDayTest(){
        Header header = mainPage.login(userInfo)
                .toHeader();

        AllocationPage allocationPage =
                header.goToPageByMyPageMenu(allocationPageMenu)
                        .setDateRange(DateRange.ofDay(2023, 11, 1))
                        .clickSearchButton();

        int index = 0;
        List<Map<String, String>> dataMap = new ArrayList<>();

        if(allocationPage.getDataTableCount() != 0){
            dataMap.add(allocationPage.openAllocationDataPopupByOrderCodeIndex(0).extractAllocationData());
        }

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> dataMap.get(index));
    }


    @AfterEach
    public void afterPageTest() throws InterruptedException {
        Thread.sleep(5000);
        webDriver.close();
    }
}
