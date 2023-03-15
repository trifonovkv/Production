package com.trifonovkv.production.ui.journal

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object ProductionContract {
    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "entry"
        const val COLUMN_NAME_DATE = "date"
        const val COLUMN_NAME_ADRY = "adry"
        const val COLUMN_NAME_AFRESH = "afresh"
        const val COLUMN_NAME_AFROST = "afrost"
        const val COLUMN_NAME_AFRUIT = "afruit"
        const val COLUMN_NAME_ALCO = "alco"
        const val COLUMN_NAME_AMEZ = "amez"
        const val COLUMN_NAME_HOLOD3 = "holod3"
        const val COLUMN_NAME_TOTAL = "total"
    }
}


private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${ProductionContract.FeedEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${ProductionContract.FeedEntry.COLUMN_NAME_DATE} INTEGER," +
            "${ProductionContract.FeedEntry.COLUMN_NAME_ADRY} INTEGER," +
            "${ProductionContract.FeedEntry.COLUMN_NAME_AFRESH} INTEGER," +
            "${ProductionContract.FeedEntry.COLUMN_NAME_AFROST} INTEGER," +
            "${ProductionContract.FeedEntry.COLUMN_NAME_AFRUIT} INTEGER," +
            "${ProductionContract.FeedEntry.COLUMN_NAME_ALCO} INTEGER," +
            "${ProductionContract.FeedEntry.COLUMN_NAME_AMEZ} INTEGER," +
            "${ProductionContract.FeedEntry.COLUMN_NAME_HOLOD3} INTEGER," +
            "${ProductionContract.FeedEntry.COLUMN_NAME_TOTAL} INTEGER)"


private const val SQL_DELETE_ENTRIES =
    "DROP TABLE IF EXISTS ${ProductionContract.FeedEntry.TABLE_NAME}"

class ProductionDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 3
        const val DATABASE_NAME = "Production.db"
    }
}

data class ProductionEntry(
    val date: Long,
    val adry: Int,
    val afresh: Int,
    val afrost: Int,
    val afruit: Int,
    val alco: Int,
    val amez: Int,
    val holod3: Int,
    val total: Int
)

class ProductionJournal(private val dbHelper: ProductionDbHelper) {

    fun addEntry(productionEntry: ProductionEntry) {

        // Gets the data repository in write mode
        val db = dbHelper.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(ProductionContract.FeedEntry.COLUMN_NAME_DATE, productionEntry.date)
            put(ProductionContract.FeedEntry.COLUMN_NAME_ADRY, productionEntry.adry)
            put(ProductionContract.FeedEntry.COLUMN_NAME_AFRESH, productionEntry.afresh)
            put(ProductionContract.FeedEntry.COLUMN_NAME_AFROST, productionEntry.afrost)
            put(ProductionContract.FeedEntry.COLUMN_NAME_AFRUIT, productionEntry.afruit)
            put(ProductionContract.FeedEntry.COLUMN_NAME_ALCO, productionEntry.alco)
            put(ProductionContract.FeedEntry.COLUMN_NAME_AMEZ, productionEntry.amez)
            put(ProductionContract.FeedEntry.COLUMN_NAME_HOLOD3, productionEntry.holod3)
            put(ProductionContract.FeedEntry.COLUMN_NAME_TOTAL, productionEntry.total)
        }

        // Insert the new row, returning the primary key value of the new row
        db?.insert(ProductionContract.FeedEntry.TABLE_NAME, null, values)

    }

    fun getEntriesForLastMonth(): List<ProductionEntry> {
        val db = dbHelper.readableDatabase

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(
            BaseColumns._ID,
            ProductionContract.FeedEntry.COLUMN_NAME_DATE,
            ProductionContract.FeedEntry.COLUMN_NAME_ADRY,
            ProductionContract.FeedEntry.COLUMN_NAME_AFRESH,
            ProductionContract.FeedEntry.COLUMN_NAME_AFROST,
            ProductionContract.FeedEntry.COLUMN_NAME_AFRUIT,
            ProductionContract.FeedEntry.COLUMN_NAME_ALCO,
            ProductionContract.FeedEntry.COLUMN_NAME_AMEZ,
            ProductionContract.FeedEntry.COLUMN_NAME_HOLOD3,
            ProductionContract.FeedEntry.COLUMN_NAME_TOTAL
        )


        val sortOrder = "${ProductionContract.FeedEntry.COLUMN_NAME_DATE} ASC"
        val cursor = db.query(
            ProductionContract.FeedEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )


        // the cursor starts at position -1
        val productionEntries = arrayListOf<ProductionEntry>()
        with(cursor) {
            while (moveToNext()) {
                productionEntries.add(
                    ProductionEntry(
                        getLong(getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_DATE)),
                        getInt(getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_ADRY)),
                        getInt(getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_AFRESH)),
                        getInt(getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_AFROST)),
                        getInt(getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_AFRUIT)),
                        getInt(getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_ALCO)),
                        getInt(getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_AMEZ)),
                        getInt(getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_HOLOD3)),
                        getInt(getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_TOTAL))
                    )
                )
            }
        }
        cursor.close()

        return productionEntries.filter {
            isDateInWorkingMonth(it.date, 0)
        }
    }


    fun getEntriesForPreviousMonth(): List<ProductionEntry> {
        val db = dbHelper.readableDatabase

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(
            BaseColumns._ID,
            ProductionContract.FeedEntry.COLUMN_NAME_DATE,
            ProductionContract.FeedEntry.COLUMN_NAME_ADRY,
            ProductionContract.FeedEntry.COLUMN_NAME_AFRESH,
            ProductionContract.FeedEntry.COLUMN_NAME_AFROST,
            ProductionContract.FeedEntry.COLUMN_NAME_AFRUIT,
            ProductionContract.FeedEntry.COLUMN_NAME_ALCO,
            ProductionContract.FeedEntry.COLUMN_NAME_AMEZ,
            ProductionContract.FeedEntry.COLUMN_NAME_HOLOD3,
            ProductionContract.FeedEntry.COLUMN_NAME_TOTAL
        )


        val sortOrder = "${ProductionContract.FeedEntry.COLUMN_NAME_DATE} ASC"
        val cursor = db.query(
            ProductionContract.FeedEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )


        // the cursor starts at position -1
        val productionEntries = arrayListOf<ProductionEntry>()
        with(cursor) {
            while (moveToNext()) {
                productionEntries.add(
                    ProductionEntry(
                        getLong(getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_DATE)),
                        getInt(getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_ADRY)),
                        getInt(getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_AFRESH)),
                        getInt(getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_AFROST)),
                        getInt(getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_AFRUIT)),
                        getInt(getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_ALCO)),
                        getInt(getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_AMEZ)),
                        getInt(getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_HOLOD3)),
                        getInt(getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_TOTAL))
                    )
                )
            }
        }
        cursor.close()

        return productionEntries.filter {
            isDateInWorkingMonth(it.date, -1)
        }
    }


    fun hasEntryWithSameDate(date: Long): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            ProductionContract.FeedEntry.TABLE_NAME,
            arrayOf(ProductionContract.FeedEntry.COLUMN_NAME_DATE),
            null,
            null,
            null,
            null,
            ProductionContract.FeedEntry.COLUMN_NAME_DATE + " DESC",
            "1"
        )

        cursor.moveToNext()
        // check if db is first run and empty
        val date2: Long
        try {
            date2 =
                cursor.getLong(cursor.getColumnIndexOrThrow(ProductionContract.FeedEntry.COLUMN_NAME_DATE))
        } catch (e: Exception) {
            return false
        }
        cursor.close()

        // fixme if enter date < 24
        return date - date2 < 8.64e+7 // 24 hours in milliseconds

    }
}

// shift :0 - current -1 - last
fun isDateInWorkingMonth(epochMilli: Long, monthShift: Long): Boolean {
    val testDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneId.systemDefault())
    val today = LocalDateTime.now()
    // if today =< 25.20:00
    val start: LocalDateTime
    val end: LocalDateTime
    if (today.dayOfMonth < 25 || (today.dayOfMonth == 25 && today.hour < 20)) {
        // last_month-1.25 20:00 < date < current_month-1.25 20:00
        end = LocalDateTime.of(today.year, today.plusMonths(monthShift).month, 25, 20, 0, 0)
        start = end.minusMonths(1)
    }
    // if today > 25 20:00
    else {
        start = LocalDateTime.of(today.year, today.plusMonths(monthShift).month, 25, 20, 0, 0)
        end = start.plusMonths(1)
    }

    return testDate.isAfter(start) && testDate.isBefore(end)
}