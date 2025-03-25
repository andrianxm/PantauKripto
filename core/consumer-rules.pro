##--------------- SQLCipher ----------
-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }

##--------------- Gson ----------
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**

# Keep your model class for Gson
-keep class com.andrian.core.data.source.remote.response.** { <fields>; <init>(...); }

# Gson type adapters
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

##--------------- Retrofit ----------
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

-keepclassmembers,allowshrinking,allowobfuscation interface * {
  @retrofit2.http.* <methods>;
}

-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
-dontwarn kotlinx.**

# Pastikan semua class di dalam package core.di tetap ada
-keep class com.andrian.core.di.** { *; }

# Keep retrofit interfaces
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# If using GsonConverterFactory
-keep class retrofit2.converter.gson.** { *; }

##--------------- Glide ----------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule { <init>(...); }
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** { **[] $VALUES; public *; }
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder { *** rewind(); }

##--------------- RxJava ----------
# Uncomment if you use RxJava 2/3
# -dontwarn java.util.concurrent.Flow*

# Optional: keep line number for debug
-keepattributes SourceFile,LineNumberTable
