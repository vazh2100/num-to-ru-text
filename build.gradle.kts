plugins {
    kotlin("jvm") version "2.1.0"
    `maven-publish`
}

group = "com.vazh2100"
version = "1.0.0"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = "com.vazh2100"
            artifactId = "num-to-ru-text"
            version = version
        }
    }
}
