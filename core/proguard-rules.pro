## Hapus debugging info di mode release
-dontwarn android.support.**
-dontwarn sun.misc.**
-dontwarn java.lang.invoke.*

## Pastikan semua model data yang digunakan tidak dihapus
-keep class com.andrian.core.data.source.remote.response.** { *; }

## Pastikan Retrofit tetap bekerja
-keep class retrofit2.** { *; }
