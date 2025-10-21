plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
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
    implementation("io.github.OperatorFoundation:ion-android:v1.0.2")
    testImplementation("io.github.OperatorFoundation:ion-android:v1.0.2")  // Add this line
    testImplementation("junit:junit:4.13.2")
}
