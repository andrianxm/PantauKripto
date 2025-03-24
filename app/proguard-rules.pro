# ===== Debugging (optional) =====
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# ===== Retrofit & Gson =====
# Keep Retrofit annotations and Gson models
-keepattributes Signature, InnerClasses, EnclosingMethod, RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn retrofit2.**
-dontwarn kotlin.Unit
-dontwarn javax.annotation.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# Gson serialized models
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keepattributes *Annotation*, Signature

# ===== Room Database =====
-keepclassmembers class * {
    @androidx.room.* <methods>;
}
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# ===== Glide =====
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule { <init>(...); }
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
    *** rewind();
}

# ===== SQLCipher =====
-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }

# ===== Navigation Component =====
-keep class androidx.navigation.** { *; }
-dontwarn androidx.navigation.**

# ===== ViewModel & LiveData =====
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>();
}
-keepclassmembers class * {
    @androidx.lifecycle.* <methods>;
}
-dontwarn androidx.lifecycle.**

# ===== Koin Core =====
-dontwarn org.koin.**
-keep class org.koin.** { *; }

# Keep module definitions and inject functions
-keepclassmembers class * {
    public <init>(...);
}
-keepclassmembers class * {
    *** getKoin(...);
    *** inject(...);
    *** get(...);
}

# Keep Koin modules (if you define modules using DSL)
-keepclassmembers class * {
    public static <fields>;
}

# Keep ViewModels for Koin
-keep class * extends androidx.lifecycle.ViewModel { *; }


# ===== Prevent Shrinking UI Elements =====
-keepclassmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclassmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
# navigation components
-keep class androidx.navigation.NavDeepLinkBuilder { *; }
-keep class androidx.navigation.NavController { *; }
-keep class androidx.navigation.fragment.NavHostFragment { *; }
-keep class androidx.navigation.NavGraph { *; }
-keep class * extends androidx.navigation.NavDestination { *; }
-keepclassmembers class * {
    @androidx.navigation.NavDeepLink <methods>;
}

# ===== Protect Custom Views =====
-keep class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# ===== App-specific Packages (Core, Favorite, dll) =====
-keep class com.andrian.core.** { *; }
-keep class com.andrian.favorite.** { *; }
-keep class com.andrian.pantaukripto.** { *; }

# Optional: disable obfuscation for logs
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
}
