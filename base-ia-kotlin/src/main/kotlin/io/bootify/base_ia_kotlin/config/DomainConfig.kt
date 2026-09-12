package io.bootify.base_ia_kotlin.config

import java.time.OffsetDateTime
import java.util.Optional
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement


@Configuration
@EntityScan("io.bootify.base_ia_kotlin")
@EnableJpaRepositories("io.bootify.base_ia_kotlin")
@EnableTransactionManagement
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
class DomainConfig {

    @Bean(name = ["auditingDateTimeProvider"])
    fun dateTimeProvider(): DateTimeProvider =
            DateTimeProvider { Optional.of(OffsetDateTime.now()) }

}
