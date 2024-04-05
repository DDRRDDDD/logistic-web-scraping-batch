# 물류 데이터 웹 스크래퍼

## 프로젝트 목적
1. 매일 반복되는 물류 데이터 작성을 자동화하여 작업의 효율성을 향상시키기
2. Selenium과 Spring의 페러다임을 맞추어 새로운 프레임워크를 구축하여 웹 자동화 작업의 일관성과 유지 보수성을 높이기
3. 변경 가능하고 구조화된 프로젝트로 추후 다른 웹 스크래퍼도 빠르게 만들 수 있게 하는 것

## 시스템 아키텍처
![image](https://github.com/DDRRDDDD/logistic-web-scraping-batch/assets/112861844/bba91373-e1f0-4376-8030-fcd8aa1573ba)


## 디렉토리 구조
```
├── batch : batch 관련 패키지
│   ├── sql : jdbc 쿼리 패키지
│   │   └── SqlMappedContext
│   ├── DataSourceConfig
│   ├── ItemConfig
│   ├── JobConfig
│   └── StepConfig
├── common : page bean 설정 패키지
│   ├── PageBeanFactory : page bean을 반환하는 유틸 클래스
│   ├── PageLoadAdvice : page bean의 메서드 로딩을 돕는 클래스
│   ├── PagePostProcessor : page bean의 생성 전/후를 관리하는 클래스
│   └── ReactiveElementLocatorFactory : 동적 웹 요소를 찾기 위한 클래스
├── metadata : job prameter, page metadata 패키지
│   ├── DateRange
│   ├── Menu
│   ├── MetaDataConfig
│   └── UserInfo
├── page : page object 패키지
│   ├── object
│   │   ├── AllocationDataPopup
│   │   ├── AllocationPage
│   │   ├── BasePage
│   │   ├── Header
│   │   └── MainPage
│   ├── Page : page bean component 어노테이션
│   └── Scenario : page의 시나리오를 표현한 클래스
├── scraper
│   ├── WebScraperBuilder : scraper를 DSL로 표현하기 위한 webscraper 빌더 클래스
│   └── WebScraperItemReader : ItemStreamReader의 추상화 클래스
└── webdriver
    ├── SeleniumConfig : selenium, webdriver의 타입, webdrivermanager 설정 클래스
    └── WebControlAgent : webdriver의 동작을 정의한 유틸 클래스
```
## 프로젝트 가이드

- Page Bean
  - 기존 Selenium POM 패턴을 사용
  - @Page 어노테이션을 사용하여 Page Bean을 정의
  - Page Bean은 빌더패턴 사용을 권장 (시나리오를 구성하기 편리해짐)
  - ex)
    ```java
    @Page(homePage = true, url = "${dev.scraper.mainUrl}")
    public class MainPage extends BasePage {

      @FindBy(id = "login_id")
      private WebElement loginIdTextInput;
  
      @FindBy(id = "login_pass")
      private WebElement loginPwTextInput;
  
      @FindBy(id = "btn_login")
      private WebElement loginButton;
  
      @FindBy(id = "allo_list")
      private WebElement allocationPageButton;


      public MainPage login(UserInfo userInfo) {
          loginIdTextInput.click();
          loginIdTextInput.sendKeys(userInfo.getUserId());
  
          loginPwTextInput.click();
          loginPwTextInput.sendKeys(userInfo.getUserPassword());
  
          loginButton.click();
          return this;
      }


      public AllocationPage goToAllocationPage() {
          allocationPageButton.click();
          return PageBeanFactory.getPage(AllocationPage.class);
      }


    }
    ```

- Web Scraper DSL
  - 절차적으로 시나리오를 구성
  - ex)
    ```java
    @Configuration
    @RequiredArgsConstructor
    public class ItemConfig {

      private final DateRange dailyDateRange;

      private final UserInfo userInfo;
 
      @Bean
      @StepScope
      public WebScraperItemReader<Map<String, String>> dailyAScenarioScraper() {
          return new WebScraperBuilder<Map<String, String>>("daily.A.scenario")
                  .scenario(Scenario.startWith(MainPage.class))
                  .prepare((reader, scenario) ->
                      scenario.expectedPage(MainPage.class)
                              .login(userInfo)
                              .goToAllocationPage()
                              .setDateRange(dailyDateRange)
                              .clickSearchButton()
                  )
                  .read((reader, scenario) ->
                      scenario.expectedPage(AllocationPage.class)
                              .openAllocationDataPopupByOrderCodeIndex(reader.getCurrentItemIndex())
                              .extractAllocationData()
                  )
                  .build();
      }
    
    }     
    ```
