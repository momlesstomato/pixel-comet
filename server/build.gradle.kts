plugins {
    id("com.gradleup.shadow") version "8.3.5"
    id("nu.studer.jooq") version "9.0"
}

jooq {
    version.set(libs.versions.jooq.get())
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)

    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(false)

            jooqConfiguration.apply {
                generator.apply {
                    database.apply {
                        name = "org.jooq.meta.extensions.ddl.DDLDatabase"
                        properties.add(
                            org.jooq.meta.jaxb.Property()
                                .withKey("scripts")
                                .withValue("src/main/resources/db/jooq/V1__baseline_schema.sql")
                        )
                        properties.add(
                            org.jooq.meta.jaxb.Property()
                                .withKey("sort")
                                .withValue("semantic")
                        )
                        properties.add(
                            org.jooq.meta.jaxb.Property()
                                .withKey("defaultNameCase")
                                .withValue("lower")
                        )
                    }
                    target.apply {
                        packageName = "com.cometproject.storage.jooq.generated"
                        directory = "build/generated-src/jooq/main"
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isFluentSetters = true
                    }
                }
            }
        }
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.boot.Main"
    }
}

tasks.shadowJar {
    archiveBaseName.set("server")
    archiveClassifier.set("")
    archiveVersion.set("")
    manifest {
        attributes["Main-Class"] = "com.boot.Main"
    }
    mergeServiceFiles()
}

tasks.assemble {
    dependsOn(tasks.shadowJar)
}

dependencies {
    implementation(project(":api"))
    implementation(project(":protocol"))
    implementation(project(":plugins:items"))

    implementation(libs.caffeine)
    implementation(libs.guice)
    implementation(libs.guava)
    implementation(libs.gson)
    implementation(libs.netty.all)
    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)
    implementation(libs.log4j.over.slf4j)
    implementation(libs.jul.to.slf4j)
    implementation(libs.hikari)
    implementation(libs.mysql.connector)
    implementation(libs.commons.lang3)
    implementation(libs.commons.io)
    implementation(libs.commons.collections4)
    implementation(libs.commons.codec)
    implementation(libs.jbcrypt)
    implementation(libs.javalin)
    implementation(libs.dotenv)
    implementation(libs.jedis)
    implementation(libs.jansi)
    implementation(libs.jetty.util)
    implementation(libs.validation.api)
    implementation(libs.jooq)
    implementation(libs.flyway.core)
    implementation(libs.flyway.mysql)

    testImplementation(libs.junit4)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit.jupiter)
    testImplementation(libs.h2)
    testImplementation(libs.testcontainers.junit)
    testImplementation(libs.testcontainers.mariadb)
    testRuntimeOnly(libs.junit.platform.launcher)
    testRuntimeOnly(libs.junit.vintage.engine)

    jooqGenerator(libs.jooq.meta)
    jooqGenerator(libs.jooq.meta.extensions)
    jooqGenerator(libs.h2)
}

tasks.test {
    useJUnitPlatform()
}
