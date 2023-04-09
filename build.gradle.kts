plugins {
    kotlin("multiplatform") version "1.8.0"
    id("io.kotest.multiplatform") version "5.5.4"
    kotlin("plugin.serialization") version "1.8.0"
    application
}

group = "nice_way"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm {
        withJava()
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0-RC")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-core:2.2.2")
                implementation("io.ktor:ktor-server-netty:2.2.2")
                implementation("io.ktor:ktor-server-content-negotiation:2.2.2")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.2")
                implementation("io.ktor:ktor-server-html-builder-jvm:2.2.2")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
                implementation("ch.qos.logback:logback-classic:1.4.5")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("io.ktor:ktor-server-test-host:2.2.2")
                implementation("io.kotest:kotest-runner-junit5:5.5.4")
                implementation("io.kotest:kotest-assertions-core:5.5.4")
            }
        }
        val jsMain by getting {
            dependencies {
                dependencies {
                    implementation(
                        project.dependencies.enforcedPlatform(
                            "org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:1.0.0-pre.490"
                        )
                    )
                    implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion")
                    implementation("org.jetbrains.kotlin-wrappers:kotlin-react")
                    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom")
                    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom")
                    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux")
                    implementation("org.jetbrains.kotlin-wrappers:kotlin-tanstack-react-query")
                    implementation("org.jetbrains.kotlin-wrappers:kotlin-tanstack-react-query-devtools")
                    implementation(npm("cross-fetch", "3.1.5"))
                }
            }
        }
        val jsTest by getting
    }
}

application {
    mainClass.set("nice_way.application.ServerKt")
}

//tasks.named<Copy>("jvmProcessResources") {
//    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
//    from(jsBrowserDistribution)
//}

tasks.register<Copy>("buildClient") {
    dependsOn("jsBrowserDevelopmentWebpack")
    from("$buildDir/developmentExecutable/")
    into("$buildDir/processedResources/jvm/main/")
}
tasks.named("run") {
    dependsOn("buildClient")
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}