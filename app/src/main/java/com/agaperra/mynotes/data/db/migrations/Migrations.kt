package com.agaperra.mynotes.data.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {

    val MigrationFrom8To9: Migration = object : Migration(8, 9) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE Note ADD COLUMN position INTEGER NOT NULL")
            database.execSQL("ALTER TABLE Note ADD COLUMN create_date STRING NOT NULL")
        }
    }

}
