dependencies {
    api(project(":api"))
    implementation(libs.netty.all)
    implementation(libs.commons.lang)

    testImplementation(libs.junit4)
}
