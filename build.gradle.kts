import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstyleExtension

allprojects {
    group = "com.cometproject"
    version = "2.9.8-TEST1"

    repositories {
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "checkstyle")

    extensions.configure<JavaPluginExtension> {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    extensions.configure<CheckstyleExtension> {
        toolVersion = "10.21.1"
        configFile = rootProject.layout.projectDirectory.file("config/checkstyle/checkstyle.xml").asFile
        configProperties["checkstyle.config.dir"] =
            rootProject.layout.projectDirectory.dir("config/checkstyle").asFile.absolutePath
        isIgnoreFailures = false
        maxWarnings = 0
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(21)
    }

    tasks.withType<Checkstyle>().configureEach {
        exclude("**/generated/**")
        exclude("**/com/cometproject/storage/jooq/generated/**")

        reports {
            xml.required.set(true)
            html.required.set(true)
        }
    }
}
