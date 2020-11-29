import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

tasks {
    withType(ProcessResources::class) {
        rootSpec.duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    named("shadowJar", ShadowJar::class) {
        archiveFileName.set("securitycams-sandbox-${project.version}.jar")
    }
}

dependencies {
    implementation(project(":securitycams"))
    compileOnly("org.spigotmc:spigot-api:1.16.4-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:20.1.0")
}
