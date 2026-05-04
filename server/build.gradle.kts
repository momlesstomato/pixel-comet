plugins {
    id("com.gradleup.shadow") version "8.3.5"
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
    implementation(libs.hikari)
    implementation(libs.mysql.connector)
    implementation(libs.commons.lang3)
    implementation(libs.commons.io)
    implementation(libs.commons.collections4)
    implementation(libs.commons.codec)
    implementation(libs.jbcrypt)
    implementation(libs.spark.core)
    implementation(libs.dotenv)
    implementation(libs.jedis)
    implementation(libs.jansi)
    implementation(libs.jetty.util)
    implementation(libs.validation.api)

    testImplementation(libs.junit4)
}
