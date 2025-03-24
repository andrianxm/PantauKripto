package com.andrian.core.di

import androidx.room.Room
import com.andrian.core.data.KriptoRepository
import com.andrian.core.data.source.local.LocalDataSource
import com.andrian.core.data.source.local.room.KriptoDatabase
import com.andrian.core.data.source.remote.RemoteDataSource
import com.andrian.core.data.source.remote.network.ApiService
import com.andrian.core.domain.repository.IKriptoRepository
import com.andrian.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<KriptoDatabase>().kriptoDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("andrian".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(), KriptoDatabase::class.java, "Kripto.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "indodax.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/M1HttNZxeN7wR6mx0CyEPmrMTRwGMkPL2tXAvdbRQ18=")
            .add(hostname, "sha256/kIdp6NNEd8wsugYyyIYFsi1ylMCED3hZbSR8ZFsa/A4=")
            .add(hostname, "sha256/mEflZT5enoR1FuXLgYYGqnVEoZvmf9c2bVBpiOjYQ0c=")
            .build()
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
        client
    }
    single {
        val retrofit = Retrofit.Builder().baseUrl("https://indodax.com/api/")
            .addConverterFactory(GsonConverterFactory.create()).client(get()).build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IKriptoRepository> { KriptoRepository(get(), get()) }
}

val coreModule = listOf(
    databaseModule, networkModule, repositoryModule
)
