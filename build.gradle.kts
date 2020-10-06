import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.4.RELEASE"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
    id("org.flywaydb.flyway") version "6.5.0"
	id("org.openapi.generator") version "4.3.1"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.3.72"
}

group = "com.nicholasbrooking.pkg"
version = "0.0.2"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	jcenter()
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-security")

	implementation("org.projectreactor:reactor-spring:1.0.1.RELEASE")

	implementation("com.squareup.moshi:moshi-kotlin:1.9.3")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("org.flywaydb:flyway-core:6.5.0")
	implementation("org.mariadb.jdbc:mariadb-java-client:2.1.2")

	implementation("io.swagger:swagger-annotations:1.6.2")
	implementation("org.openapitools:jackson-databind-nullable:0.2.1")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.11.3")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

flyway {
	url = "jdbc:mariadb://localhost:3306/schachbe"
	user = "schach"
	password = "schach"
	schemas = arrayOf("schachbe")
	locations = arrayOf("classpath:db/migration")
	defaultSchema = "schachbe"
}

sourceSets {
	main {
		java {
			setSrcDirs(listOf("${rootDir}/src/main/java"))
		}
	}
}

tasks {
	getByName<Delete>("clean") {
		delete.add("${rootDir}/src/main/java")
	}
	compileKotlin {
		dependsOn("openApiGenerate")
	}
}

openApiGenerate {
	generatorName.set("spring")
	inputSpec.set("https://raw.githubusercontent.com/nicholas-j-b/schachfish-api/master/schach-be/src/main/resources/openapi.yml")
	outputDir.set("$rootDir")

	systemProperties.set(mapOf(
			"modelDocs" to "false",
			"models" to "",
			"apis" to "",
			"supportingFiles" to "files"
	))

	configOptions.set(mapOf(
			"useOptional" to "true",
			"swaggerDocketConfig" to "false",
			"useBeanValidation" to "false",
			"useTags" to "true",
			"singleContentTypes" to "true",
			"title" to rootProject.name,
			"artifactId" to "${rootProject.name}.${version}",
			"java8" to "false",
			"generateSupportingFiles" to "false",
			"serializableModel" to "true",
			"interfaceOnly" to "true",
			"groupName" to "com.nicholasbrooking.pkg.schachbe",
			"packageName" to "com.nicholasbrooking.pkg.schachbe.api",
			"apiPackage" to "com.nicholasbrooking.pkg.schachbe.api",
			"basePackage" to "com.nicholasbrooking.pkg.schachbe.api",
			"modelPackage" to "com.nicholasbrooking.pkg.schachbe.api.model",
			"invokerPackage" to "com.nicholasbrooking.pkg.schachbe.api",
			"configPackage" to "com.nicholasbrooking.pkg.schachbe.api"
	))
}
