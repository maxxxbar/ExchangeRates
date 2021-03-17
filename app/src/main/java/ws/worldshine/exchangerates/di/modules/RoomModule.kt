package ws.worldshine.exchangerates.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ws.worldshine.exchangerates.database.Database
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideDataBase(context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "database.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}