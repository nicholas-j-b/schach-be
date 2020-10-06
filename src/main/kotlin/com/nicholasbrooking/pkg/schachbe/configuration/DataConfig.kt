package com.nicholasbrooking.pkg.schachbe.configuration

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
class DataConfig() {

    @Bean
    fun getDataSource(): DataSource {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.driverClassName("org.mariadb.jdbc.Driver")
        dataSourceBuilder.url("jdbc:mariadb://localhost:3306/schachbe")
        dataSourceBuilder.username("schach")
        dataSourceBuilder.password("schach")
        return dataSourceBuilder.build()
    }
}
