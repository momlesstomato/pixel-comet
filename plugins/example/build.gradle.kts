dependencies {
    implementation(project(":api"))

    testImplementation(libs.junit4)
}

tasks.jar {
    archiveFileName.set("example.jar")
    destinationDirectory.set(rootProject.layout.projectDirectory.dir("modules"))
}
