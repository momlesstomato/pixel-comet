dependencies {
    implementation(project(":api"))
    implementation(libs.guava)

    testImplementation(libs.junit4)
}

tasks.jar {
    archiveFileName.set("rooms.jar")
    destinationDirectory.set(rootProject.layout.projectDirectory.dir("modules"))
}
