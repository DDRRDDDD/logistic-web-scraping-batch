package com.project.data;

import com.project.page.object.AllocationPage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@RequiredArgsConstructor(access= AccessLevel.PRIVATE)
public class Menu<T> {

    private final String option;
    private final Class<T> pageClass;

    @Configuration
    static class MenuFactory {
        @Bean
        public Menu<AllocationPage> allocationPageMenu(){
            return new Menu<>("배차내역조회", AllocationPage.class);
        }

    }

}
