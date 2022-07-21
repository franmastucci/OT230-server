package com.alkemy.ong.context;

import com.alkemy.ong.ContextTests;
import com.alkemy.ong.models.entity.CommentEntity;
import com.alkemy.ong.models.entity.NewsEntity;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

public abstract class NewsContextTest extends ContextTests {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CommentRepository commentRepository;

    protected NewsEntity createNews() throws Exception{
        NewsEntity news =
                NewsEntity.builder().
                        name("news test").
                        content("content test").
                        image("imageTest").
                        lastModification(new Timestamp(System.currentTimeMillis())).
                        categoryId(1L).
                        build();
        newsRepository.save(news);
        return news;
    }

    protected NewsEntity createNewsParams(String name, String content, String image, Long categoryId) throws Exception{
        NewsEntity news =
                NewsEntity.builder().
                        name(name).
                        content(content).
                        image(image).
                        lastModification(new Timestamp(System.currentTimeMillis())).
                        categoryId(categoryId).
                        build();
        newsRepository.save(news);
        return news;
    }

    protected CommentEntity createComment(NewsEntity news) throws Exception{
        CommentEntity comment =
                CommentEntity.builder()
                        .body("body test")
                        .newsId(news.getId())
                        .userId(1L)
                        .build();
        commentRepository.save(comment);
        return comment;
    }
}
