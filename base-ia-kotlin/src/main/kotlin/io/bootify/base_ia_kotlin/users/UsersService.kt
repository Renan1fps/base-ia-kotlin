package io.bootify.base_ia_kotlin.users

import io.bootify.base_ia_kotlin.util.NotFoundException
import java.util.UUID
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class UsersService(
    private val usersRepository: UsersRepository
) {

    fun findAll(): List<UsersDTO> {
        val userses = usersRepository.findAll(Sort.by("userId"))
        return userses.map { users -> mapToDTO(users, UsersDTO()) }
    }

    fun `get`(userId: UUID): UsersDTO = usersRepository.findById(userId)
            .map { users -> mapToDTO(users, UsersDTO()) }
            .orElseThrow { NotFoundException() }

    fun create(usersDTO: UsersDTO): UUID {
        val users = Users()
        mapToEntity(usersDTO, users)
        return usersRepository.save(users).userId!!
    }

    fun update(userId: UUID, usersDTO: UsersDTO) {
        val users = usersRepository.findById(userId)
                .orElseThrow { NotFoundException() }
        mapToEntity(usersDTO, users)
        usersRepository.save(users)
    }

    fun delete(userId: UUID) {
        val users = usersRepository.findById(userId)
                .orElseThrow { NotFoundException() }
        usersRepository.delete(users)
    }

    private fun mapToDTO(users: Users, usersDTO: UsersDTO): UsersDTO {
        usersDTO.userId = users.userId
        usersDTO.name = users.name
        usersDTO.email = users.email
        usersDTO.document = users.document
        usersDTO.status = users.status
        return usersDTO
    }

    private fun mapToEntity(usersDTO: UsersDTO, users: Users): Users {
        users.name = usersDTO.name
        users.email = usersDTO.email
        users.document = usersDTO.document
        users.status = usersDTO.status
        return users
    }

}
