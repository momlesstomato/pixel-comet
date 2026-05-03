dependencies {
    implementation(project(":api"))
    compileOnly(project(":server"))
    implementation(libs.guava)

    testImplementation(libs.junit4)
}

tasks.jar {
    archiveFileName.set("groups.jar")
    destinationDirectory.set(rootProject.layout.projectDirectory.dir("modules"))
}
