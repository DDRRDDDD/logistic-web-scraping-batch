package com.project.selenium;


import com.project.common.BeanManager;
import com.project.page.Page;
import org.apache.hc.core5.function.Callback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;



@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AnnotationTest {

    private final Logger log = LoggerFactory.getLogger(AnnotationTest.class);

    private final APage aPage;

    private final BPage bPage;

    private final ConfigurableApplicationContext context;

    @Autowired
    public AnnotationTest(APage aPage, BPage bPage, ConfigurableApplicationContext context){
        this.aPage = aPage;
        this.bPage = bPage;
        this.context = context;
    }

    @Test
    @DisplayName("어노테이션 적용이 정상적으로 되는지 테스트")
    public void annotationTest1(){
        Assertions.assertEquals("this is A Page", aPage.toString());
        Assertions.assertEquals("this is B Page", bPage.toString());
    }

    @Test
    @DisplayName("어노테이션 설정 정보를 가져오는 테스트")
    public void annotationTest2(){
        Page pageAnnotation = AopProxyUtils.ultimateTargetClass(aPage).getDeclaredAnnotation(Page.class);

        log.info("@Page homePage() => {}", pageAnnotation.homePage());
        log.info("@Page url() => {}", pageAnnotation.url());

        Assertions.assertFalse(pageAnnotation.homePage());
        Assertions.assertEquals("www.test.com", pageAnnotation.url());
    }

    @Test
    @DisplayName("어노테이의 생명주기 확인")
    public void annotationTest3(){
       String mainPageScope = context.getBeanFactory().getBeanDefinition("mainPage").getScope();
       String popupPageScope = context.getBeanFactory().getBeanDefinition("allocationDataPopup").getScope();

       log.info("MainPage Bean Scope => {}", mainPageScope);
       log.info("AllocationPopup Bean Scope => {}", popupPageScope);

       Assertions.assertEquals("singleton", mainPageScope);
       Assertions.assertEquals("prototype", popupPageScope);
    }
}


@Page(url = "www.test.com")
class APage{
    @Override
    public String toString(){
        return "this is A Page";
    }
}

@Page(pageScope = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class BPage{
    @Override
    public String toString(){
        return "this is B Page";
    }
}
