package io.bootify.base_ia_kotlin.users

import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import java.lang.Void
import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(
    value = ["/api/userses"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class UsersResource(
    private val usersService: UsersService
) {

    @GetMapping
    fun getAllUserses(): ResponseEntity<List<UsersDTO>> = ResponseEntity.ok(usersService.findAll())

    @GetMapping("/{userId}")
    fun getUsers(@PathVariable(name = "userId") userId: UUID): ResponseEntity<UsersDTO> =
            ResponseEntity.ok(usersService.get(userId))

    @PostMapping
    @ApiResponse(responseCode = "201")
    fun createUsers(@RequestBody @Valid usersDTO: UsersDTO): ResponseEntity<UUID> {
        val createdUserId = usersService.create(usersDTO)
        return ResponseEntity(createdUserId, HttpStatus.CREATED)
    }

    @PutMapping("/{userId}")
    fun updateUsers(@PathVariable(name = "userId") userId: UUID, @RequestBody @Valid
            usersDTO: UsersDTO): ResponseEntity<UUID> {
        usersService.update(userId, usersDTO)
        return ResponseEntity.ok(userId)
    }

    @DeleteMapping("/{userId}")
    @ApiResponse(responseCode = "204")
    fun deleteUsers(@PathVariable(name = "userId") userId: UUID): ResponseEntity<Void> {
        usersService.delete(userId)
        return ResponseEntity.noContent().build()
    }

}
