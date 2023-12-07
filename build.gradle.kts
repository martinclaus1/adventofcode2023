plugins {
    kotlin("jvm") version "1.9.20"
}

group = "dev.martinclaus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")

}

tasks.test {
    useJUnitPlatform()
}