package com.project.scraper;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;

@Slf4j
public abstract class WebScraperItemReader<T> extends AbstractItemCountingItemStreamItemReader<T> {


    public int getCurrentItemIndex() {
        return getCurrentItemCount() - 1;
    }


    /** 이 메서드는 기본적으로 아무 동작을 수행하지 않습니다. 필요에 따라 재정의하여 사용하세요. */
    @Override
    protected void doClose()  {}

    /** 이 메서드는 기본적으로 아무 동작을 수행하지 않습니다. 필요에 따라 재정의하여 사용하세요. */
    @Override
    protected void jumpToItem(int itemIndex) {}
}
