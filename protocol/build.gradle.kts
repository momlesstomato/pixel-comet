dependencies {
    api(project(":api"))
    implementation(libs.netty.all)
    implementation(libs.commons.lang3)

    testImplementation(libs.junit4)
}
