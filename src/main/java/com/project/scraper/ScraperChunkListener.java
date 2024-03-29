package com.project.scraper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Slf4j
@JobScope
@Component
public class ScraperChunkListener implements ChunkListener {

    @Override
    public void beforeChunk(ChunkContext context) {
        log.info("<<<<<<<<<<<<<< Before Chunk >>>>>>>>>>>>>>>");
    }

    @Override
    public void afterChunk(ChunkContext context) {
        log.info("<<<<<<<<<<<<<< After Chunk >>>>>>>>>>>>>>>");
    }

}
