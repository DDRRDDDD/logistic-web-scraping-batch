## [물류 데이터 웹 스크래퍼 배치 프로그램](https://velog.io/@ddrrdddd/%EB%B0%B0%EC%B0%A8-%EC%9D%BC%EC%A7%80-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-1)
👽 물류 데이터를 스크래핑합니다!!

#### [💡 배차 일지 프로젝트 개요](https://velog.io/@ddrrdddd/%EB%B0%B0%EC%B0%A8-%EC%9D%BC%EC%A7%80-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-1)
#### [💡 중간 점검](https://velog.io/@ddrrdddd/%EB%B0%B0%EC%B0%A8-%EC%9D%BC%EC%A7%80-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%A4%91%EA%B0%84-%EC%A0%90%EA%B2%80)

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
