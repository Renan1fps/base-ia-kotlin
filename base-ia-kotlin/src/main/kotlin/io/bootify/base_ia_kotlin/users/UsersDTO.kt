package io.bootify.base_ia_kotlin.users

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID


class UsersDTO {

    var userId: UUID? = null

    @NotNull
    @Size(max = 255)
    var name: String? = null

    @NotNull
    @Size(max = 255)
    var email: String? = null

    @NotNull
    @Size(max = 255)
    var document: String? = null

    @Size(max = 255)
    var status: String? = null

}
