dependencies {
    implementation(project(":api"))
    implementation(libs.gson)
    implementation(libs.commons.lang)

    testImplementation(libs.junit4)
}
