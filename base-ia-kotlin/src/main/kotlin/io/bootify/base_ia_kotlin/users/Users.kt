package io.bootify.base_ia_kotlin.users

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime
import java.util.UUID
import org.hibernate.annotations.UuidGenerator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener


@Entity
@Table(name = "Userses")
@EntityListeners(AuditingEntityListener::class)
class Users {

    @Id
    @Column(
        nullable = false,
        updatable = false
    )
    @GeneratedValue
    @UuidGenerator
    var userId: UUID? = null

    @Column(nullable = false)
    var name: String? = null

    @Column(nullable = false)
    var email: String? = null

    @Column(nullable = false)
    var document: String? = null

    @Column
    var status: String? = null

    @CreatedDate
    @Column(
        nullable = false,
        updatable = false
    )
    var dateCreated: OffsetDateTime? = null

    @LastModifiedDate
    @Column(nullable = false)
    var lastUpdated: OffsetDateTime? = null

}
