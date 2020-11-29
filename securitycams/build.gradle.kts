import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `maven-publish`
}

tasks.named("shadowJar", ShadowJar::class) {
    archiveFileName.set("securitycams-${project.version}.jar")

    relocate("io.papermc.lib", "com.github.ponclure.securitycams.paperlib")
}

publishing {
    publications {
        create("mavenJava", MavenPublication::class) {
            from(components["java"])
        }
    }
}

dependencies {
    implementation("com.github.ponclure:SimpleNPCFramework-API:2.11-SNAPSHOT")
    implementation("io.papermc:paperlib:1.0.5")
    compileOnly("org.spigotmc:spigot:1.16.4-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:20.1.0")
}
