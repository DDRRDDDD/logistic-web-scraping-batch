package com.project.scraper;

import com.project.page.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@Slf4j
public class WebScraperBuilder<T> {

    private String contextName;

    private Scenario scenario;

    private List<BiFunction<WebScraperItemReader<T>, Scenario, ?>> prepareScrapers;

    private BiFunction<WebScraperItemReader<T>, Scenario, T> readScraper;


    public WebScraperBuilder(String contextName) {
        this.contextName = contextName;
    }

    public WebScraperBuilder(String contextName, Scenario scenario) {
        this.contextName = contextName;
        this.scenario = scenario;
    }


    public WebScraperBuilder<T> contextName(String contextName) {
        this.contextName = contextName;
        return this;
    }

    public WebScraperBuilder<T> scenario(Scenario scenario) {
        this.scenario = scenario;
        return this;
    }

    public WebScraperBuilder<T> prepare(BiFunction<WebScraperItemReader<T>, Scenario, ?> prepareScraper) {
        if(ObjectUtils.isEmpty(prepareScrapers)){
            prepareScrapers = new ArrayList<>();
        }
        prepareScrapers.add(prepareScraper);
        return this;
    }

    public WebScraperBuilder<T> read(BiFunction<WebScraperItemReader<T>, Scenario, T> readScraper) {
        this.readScraper = readScraper;
        return this;
    }

    public WebScraperItemReader<T> build() {
        return new WebScraperItemReader<>() {
            @Override
            protected void prepare() {
                setName(contextName);

                if(!ObjectUtils.isEmpty(prepareScrapers)){
                    return ;
                }

                prepareScrapers.forEach(
                        (prepare) -> scenario.setup(prepare.apply(this, scenario))
                );
            }
            @Override
            protected T doRead() {
                if(ObjectUtils.isEmpty(readScraper)){
                    return null;
                }

                T result = readScraper.apply(this, scenario);

                if(ObjectUtils.isEmpty(result)){
                    return null;
                }

                log.info("Record  was retrieved. - [Item counts : {}]", getCurrentItemCount());
                return result;
            }
        };
    }
}
