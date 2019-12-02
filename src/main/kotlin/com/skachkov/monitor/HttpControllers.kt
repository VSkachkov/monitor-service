package com.skachkov.monitor

import com.skachkov.monitor.service.ConnectionDataService
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/article")
class ArticleController(private val repository: ArticleRepository) {

	@GetMapping("/")
	fun findAll() = repository.findAllByOrderByAddedAtDesc()

	@GetMapping("/{slug}")
	fun findOne(@PathVariable slug: String) =
			repository.findBySlug(slug) ?: throw ResponseStatusException(NOT_FOUND, "This article does not exist")

}

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository) {

	@GetMapping("/")
	fun findAll() = repository.findAll()

	@GetMapping("/{login}")
	fun findOne(@PathVariable login: String) = repository.findByLogin(login) ?: throw ResponseStatusException(NOT_FOUND, "This user does not exist")
}

@CrossOrigin(origins = arrayOf("http://localhost:4200"))
@RestController
@RequestMapping("/stats")
class NodesController(private val dataService: ConnectionDataService) {
	@GetMapping("/edges/")
	fun findEdges() = dataService.findEdges()
	@GetMapping("/edges/update/")
	fun findUpdates() = dataService.findUpdates()
	@GetMapping("/nodes/")
	fun findNodes() = dataService.findNodes()
}
