package com.alkemy.ong.context;

import com.alkemy.ong.ContextTests;
import com.alkemy.ong.models.entity.NewsEntity;
import com.alkemy.ong.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

public abstract class NewsContextTest extends ContextTests {

    @Autowired
    private NewsRepository newsRepository;

    protected NewsEntity createNews() throws Exception{
        NewsEntity news =
                NewsEntity.builder().
                        name("News test").
                        content("content test").
                        image("image test").
                        lastModification(new Timestamp(System.currentTimeMillis())).
                        categoryId(1L).
                        build();
        newsRepository.save(news);
        return news;
    }
}
