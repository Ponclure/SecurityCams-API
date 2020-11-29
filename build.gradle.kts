plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

defaultTasks("clean", "build", "shadowJar", "securitycams:publishToMavenLocal")

allprojects {
    project.group = "com.github.ponclure"
    project.version = "RELEASE-1.0.1"
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "com.github.johnrengelman.shadow")

    project.java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.compileJava {
        options.encoding = "UTF-8"
    }

    project.sourceSets {
        main {
            java.srcDir("src/main/java")
            resources.srcDir("src/main/resources")
        }
    }

    repositories {
        mavenCentral()
        maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
        maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
        mavenLocal()
    }
}
