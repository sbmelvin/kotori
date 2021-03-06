plugins {
    `java-library`
    kotlin("jvm")
    `maven-publish`
}

dependencies {
    implementation(project(":kotori"))
    implementation(Sudachi.Dependencies.Sudachi)

    implementation(Kotlin.Dependencies.Stdlib)
    implementation(Kotlin.Dependencies.Reflect)

    testImplementation(Kotlin.Dependencies.Test)
    testImplementation(Kotlin.Dependencies.TestJunit)
}

tasks.withType<Test> {
    dependsOn(":prepareTestingData")
    maxHeapSize = "4096m"
}
