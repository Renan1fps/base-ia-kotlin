package io.bootify.base_ia_kotlin.users

import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository


interface UsersRepository : JpaRepository<Users, UUID>
