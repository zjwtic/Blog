package com.zhou.runner;

import com.zhou.domain.entity.Article;
import com.zhou.mapper.ArticleMapper;
import com.zhou.service.ArticleService;
import com.zhou.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
   private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        List<Article> articleslist = articleMapper.selectList(null);
        Map<String, Integer> collects = articleslist.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        //把查询到的id作为key，viewCount作为value，一起存入Redis
        redisCache.setCacheMap("article:viewCount",collects);

    }

}
