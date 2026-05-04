plugins {
    application
}

application {
    mainClass.set("com.boot.Main")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":protocol"))
    implementation(project(":plugins:items"))

    implementation(libs.guice)
    implementation(libs.guava)
    implementation(libs.gson)
    implementation(libs.netty.all)
    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)
    implementation(libs.log4j)
    implementation(libs.hikari)
    implementation(libs.mysql.connector)
    implementation(libs.commons.lang)
    implementation(libs.commons.lang3)
    implementation(libs.commons.io)
    implementation(libs.commons.collections4)
    implementation(libs.commons.codec)
    implementation(libs.jbcrypt)
    implementation(libs.spark.core)
    implementation(libs.async.http.client)
    implementation(libs.ehcache)
    implementation(libs.jedis)
    implementation(libs.java.websocket)
    implementation(libs.joda.time)
    implementation(libs.dnsjava)
    implementation(libs.jansi)
    implementation(libs.jna.platform)
    implementation(libs.trove4j)
    implementation(libs.jetty.util)
    implementation(libs.validation.api)

    testImplementation(libs.junit4)
}
