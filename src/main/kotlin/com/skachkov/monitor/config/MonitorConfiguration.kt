package com.skachkov.monitor.config

import com.skachkov.monitor.entities.Article
import com.skachkov.monitor.ArticleRepository
import com.skachkov.monitor.entities.User
import com.skachkov.monitor.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MonitorConfiguration {

    @Bean
    fun databaseInitializer(userRepository: UserRepository,
                            articleRepository: ArticleRepository
//                            ,
//                            connectionRepository: ConnectionRepository
    ) = ApplicationRunner {
        val smaldini = userRepository.save(User("smaldini", "Stéphane", "Maldini"))
//        val sampleRequest = connectionRepository.save(RequestResponse(Date(), 500, kotlin.random.Random(LongRange(Long.MIN_VALUE, Long.MAX_VALUE).hashCode()).nextLong(), "DS-BOOKING", "DS-VEHICLE", null))
        articleRepository.save(Article(
                title = "Reactor Bismuth is out",
                headline = "Lorem ipsum",
                content = "dolor sit amet",
                author = smaldini
        ))
        articleRepository.save(Article(
                title = "Reactor Aluminium has landed",
                headline = "Lorem ipsum",
                content = "dolor sit amet",
                author = smaldini
        ))
    }
}
