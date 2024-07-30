plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.wangzhen.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    api("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.google.code.gson:gson:2.10.1")
}

sourceSets {
    create("main") {
        java.srcDir("src/main/java")
    }
}

val androidSourceJar by tasks.registering(Jar::class) {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(sourceSets["main"].allSource)
    archiveClassifier.set("sources")
}

afterEvaluate {
    publishing.publications {
        create<MavenPublication>("release") {
            artifact(androidSourceJar)
            artifact(tasks.getByName("bundleReleaseAar"))
        }
    }
}