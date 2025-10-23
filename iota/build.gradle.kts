plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

android {
    namespace = "com.operatorfoundation.iota"
    compileSdk = 36
    
    defaultConfig {
        minSdk = 33
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation("com.github.OperatorFoundation:TransmissionAndroid:v1.3.4")
    implementation("com.github.OperatorFoundation:ion-android:v1.0.7")
    testImplementation("com.github.OperatorFoundation:ion-android:v1.0.7")
    testImplementation("junit:junit:4.13.2")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.OperatorFoundation"
            artifactId = "iota-android"
            // JitPack will pass the git tag as the VERSION environment variable
            version = System.getenv("VERSION") ?: project.version.toString()

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}