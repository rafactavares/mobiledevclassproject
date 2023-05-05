package com.example.shoppingapp
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "dblite.db"
        private const val TABLE_NAME = "mytable"
        private const val COLUMN_STATUS = "status"
        private const val COLUMN_LOGGED_IN = "logged_in"
        private const val COLUMN_USER_ID = "userId"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $TABLE_NAME ($COLUMN_STATUS TEXT, $COLUMN_LOGGED_IN INTEGER, $COLUMN_USER_ID INTEGER PRIMARY KEY)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun updateLoggedInStatus(status: String, userId: Int, loggedIn: Boolean): Boolean {

        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_LOGGED_IN, loggedIn.toString())
            put(COLUMN_USER_ID, userId)
        }

        val selection = "$COLUMN_STATUS = ?"
        val selectionArgs = arrayOf(status)

        val numRowsUpdated = db.update(TABLE_NAME, values, selection, selectionArgs)

        db.close()

        return numRowsUpdated > 0
    }



    @SuppressLint("Range")
    fun getLoggedInStatus(status: String): Pair<Int, Boolean>? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_USER_ID, COLUMN_LOGGED_IN),
            "$COLUMN_STATUS=?",
            arrayOf(status),
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            val userId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID))
            val loggedIn = cursor.getInt(cursor.getColumnIndex(COLUMN_LOGGED_IN)) == 1
            return Pair(userId, loggedIn)
        }
        cursor.close()
        db.close()
        return null
    }


    fun insertLoggedInStatus(loggedIn: Boolean, userId: Int){
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_STATUS, "status")
            put(COLUMN_LOGGED_IN, loggedIn.toString())
            put(COLUMN_USER_ID, userId.toString())
        }

        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE)

        db.close()
    }

}
