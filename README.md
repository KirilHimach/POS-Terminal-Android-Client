# POS Terminal Android Client

## Overview

This is the client application for a POS terminal on Android.

## Requirements

- **Android Studio**: 2023.3.1 or higher
- **Gradle**: 8.11.1
- **Android SDK**: API 26+
- **JDK**: 17

## Cloning the Repository

1. Clone the repository and navigate to the project folder:

   ```bash
   git clone https://github.com/KirilHimach/POS-Terminal-Android-Client.git
  
2. Open the project in Android Studio and sync it.

Key Configuration
1. Use the provided keys or generate your own:
RSA: 2048-bit, PKCS#8 format
HMAC: SHA-256
2. Ensure the HMAC keys match on both the application and the server for the initial launch.
3. Keys are located in data.repositories.TestData:
4. Use your own MERCHANT_ID

internal object TestData {
internal const val MERCHANT_ID = "your_id"
internal var HMAC_KEY = FreshHMACDto(
hmacKey = "your_key",
count = 1
)
internal const val RSA_PUBLIC_KEY = """-----BEGIN PUBLIC KEY-----
                        .................
-----END PUBLIC KEY-----"""
}

## Configuring BASE_URL

1. Update the `BASE_URL` value in the `build.gradle` file to match your server address:

```gradle

buildTypes {
    release {
        isMinifyEnabled = false
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
        isDebuggable = false
        buildConfigField(type = "String", name = "BASE_URL", value = "\"http://10.0.2.2:8080/\"")
        }
    debug {
        isDebuggable = true
        buildConfigField(type = "String", name = "BASE_URL", value = "\"http://10.0.2.2:8080/\"")
        }
    }

2. If the server and application run on the same device, keep the IP address 10.0.2.2 unchanged.
3. Sync the project after making changes.