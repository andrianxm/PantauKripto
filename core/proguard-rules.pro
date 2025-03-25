## Hapus debugging info di mode release
-dontwarn android.support.**
-dontwarn sun.misc.**
-dontwarn java.lang.invoke.*

## Pastikan semua model data yang digunakan tidak dihapus
-keep class com.andrian.core.data.source.remote.response.** { *; }

## Pastikan Retrofit tetap bekerja
-keep class retrofit2.** { *; }
# Mencegah penghapusan Resource
-keep class com.andrian.core.data.Resource { *; }
-keep class com.andrian.core.data.Resource$* { *; }

# Mencegah penghapusan Model & Repository
-keep class com.andrian.core.domain.model.** { *; }
-keep class com.andrian.core.domain.repository.** { *; }
-keep class com.andrian.core.domain.usecase.** { *; }

# Mencegah penghapusan Adapter & UI Components
-keep class com.andrian.core.ui.** { *; }

# Mencegah penghapusan Utility Functions
-keep class com.andrian.core.utils.** { *; }

# Mencegah penghapusan Modul Dependency Injection
-keep class com.andrian.core.di.** { *; }
-keep class org.koin.** { *; }
