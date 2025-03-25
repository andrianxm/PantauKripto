
## Keep semua kode agar debugging tidak terganggu
-keep class com.andrian.core.** { *; }

## Mencegah kesalahan pada SQLCipher, Gson, Retrofit, Glide
-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep class com.google.gson.** { *; }
-keep class retrofit2.** { *; }
-keep class com.bumptech.glide.** { *; }

## Jangan hapus anotasi yang diperlukan
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes SourceFile, LineNumberTable

# This is generated automatically by the Android Gradle plugin.
-dontwarn java.lang.invoke.StringConcatFactory