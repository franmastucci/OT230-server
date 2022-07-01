package com.alkemy.ong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ong.exception.OrgNotFoundException;
import com.alkemy.ong.models.entity.NewsEntity;
import com.alkemy.ong.models.mapper.NewsMapper;
import com.alkemy.ong.models.request.NewsRequest;
import com.alkemy.ong.models.response.NewsResponse;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	NewsRepository newsRepository;
	
	@Autowired
	NewsMapper newsMapper;
	
	@Override
	public NewsResponse getNewsById(Long id) {
		NewsEntity newsFound = newsRepository.findById(id)
				.orElseThrow(() -> new OrgNotFoundException("That news doesn't exist"));	
		NewsResponse newsResponse = newsMapper.toNewsResponse(newsFound);
		return newsResponse;	
	}

	@Override
	public NewsResponse createNews(NewsRequest newsRequest) {
		NewsEntity news = newsMapper.toNewsEntity(newsRequest);
		newsRepository.save(news);
		NewsResponse newsResponse = newsMapper.toNewsResponse(news);
		return newsResponse;
	}

	@Override
	public NewsResponse updateNews(Long id, NewsRequest newsRequest) {
		NewsEntity newsFound = newsRepository.findById(id)
				.orElseThrow(() -> new OrgNotFoundException("That news doesn't exist"));
		NewsEntity updatedNews = newsMapper.updatedNews(newsFound, newsRequest);
		newsRepository.save(updatedNews);
		NewsResponse newsResponse = newsMapper.toNewsResponse(updatedNews);
		return newsResponse;
	}

	@Override
	public String removeNews(Long id) {
		NewsEntity newsFound = newsRepository.findById(id)
				.orElseThrow(() -> new OrgNotFoundException("That news doesn't exist"));
		newsRepository.delete(newsFound);
		return "Successfull removal";
	}

	@Override
	public List<NewsResponse> getNewsList() {
		List<NewsEntity> news = newsRepository.findAll();
		List<NewsResponse> newsResponse = newsMapper.listNewsResponse(news);
		return newsResponse;
	}
}
