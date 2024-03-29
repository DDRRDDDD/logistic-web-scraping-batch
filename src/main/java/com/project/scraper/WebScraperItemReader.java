package com.project.scraper;


import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class WebScraperItemReader<T> extends AbstractItemCountingItemStreamItemReader<T> {

    private WebDriverManager webDriverManager;

    /** 데이터 추출 전 준비를 합니다*/
    protected abstract void prepare();


    @Autowired
    public final void setWebDriverManager(WebDriverManager webDriverManager) {
        if(ObjectUtils.isNotEmpty(this.webDriverManager)) {
            log.warn("WebDriverManager is already set. Ignoring new assignment.");
            return;
        }
        this.webDriverManager = webDriverManager;
    }

    public int getCurrentItemIndex() {
        return getCurrentItemCount() - 1;
    }

    @Override
    protected void doOpen() {
        try {
            prepare();
        } catch(Throwable e) {
            closeWebDriverSession();
            throw new ItemStreamException("An error occurred while opening the scraper.", e);
        }
    }

    @Override
    protected void doClose()  {
        closeWebDriverSession();
    }

    private void closeWebDriverSession() {
        if(ObjectUtils.isNotEmpty(webDriverManager)) {
            webDriverManager.quit();
            webDriverManager = null;
            log.info("WebDriverManager session is closed");
        }
    }

    /** 이 메서드는 기본적으로 아무 동작을 수행하지 않습니다. 필요에 따라 재정의하여 사용하세요. */
    @Override
    protected void jumpToItem(int itemIndex) {}
}
