package com.project.page;

import com.project.common.PageBeanFactory;
import lombok.AllArgsConstructor;
import org.springframework.util.Assert;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
public class Scenario {

    private Object pageObject;


    public static Scenario startWith(Class<?> pageClass) {
        Assert.isTrue(pageClass.isAnnotationPresent(Page.class),
                "This class is not registered as a page.");

        return new Scenario(PageBeanFactory.getPage(pageClass));
    }


    /**
     * 반환될 페이지 객체가 주어진 페이지 클래스와 일치하는지 확인합니다.
     * 일치하지 않는 경우 페이지 클래스와 페이지 객체의 구성에 문제가 있을 수 있습니다.
     * 이 메서드를 사용할 때는 항상 페이지 클래스와 페이지 객체가 일치하는지 확인하세요.
     * 이렇게 함으로써 페이지 객체가 잘못된 클래스로 형변환되는 것을 방지할 수 있습니다.
     * 또한, <b>Scenario</b>가 반환될 객체를 알 수 있게 <b>setup</b> 메서드를 사용하세요.
     * @param pageClass 기대하는 페이지 클래스
     * @param <T> 페이지 클래스의 타입
     * @return 주어진 페이지 클래스의 인스턴스
     */

    @SuppressWarnings("unchecked")
    public <T> T expectedPage(Class<T> pageClass) {
        Assert.isInstanceOf(pageClass, pageObject,
                "The page object does not match the expected page class, " +
                        "or did not return a page object.");
        return (T) pageObject;
    }


    public void setup(Object pageObject) {
        this.pageObject = pageObject;
    }


}
