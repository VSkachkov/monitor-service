package com.skachkov.monitor

import com.skachkov.monitor.entities.Article
import com.skachkov.monitor.entities.RequestResponse
import com.skachkov.monitor.entities.User
import org.springframework.data.repository.CrudRepository

interface ArticleRepository : CrudRepository<Article, Long> {
	fun findBySlug(slug: String): Article?
	fun findAllByOrderByAddedAtDesc(): Iterable<Article>
}

interface UserRepository : CrudRepository<User, Long> {
	fun findByLogin(login: String): User?
}

interface ConnectionRepository : CrudRepository<RequestResponse, Long> {
//	fun findByEdge(login: Edge): User?
}
