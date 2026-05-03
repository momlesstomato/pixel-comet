dependencies {
    implementation(project(":api"))
    implementation(project(":protocol"))
    compileOnly(project(":server"))
    implementation(libs.guava)
    implementation(libs.netty.all)

    testImplementation(libs.junit4)
}

tasks.jar {
    archiveFileName.set("fastfood.jar")
    destinationDirectory.set(rootProject.layout.projectDirectory.dir("modules"))
}
