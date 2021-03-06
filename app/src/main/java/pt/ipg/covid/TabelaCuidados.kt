package pt.ipg.covid

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaCuidados(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME_CUIDADO TEXT NOT NULL, $CAMPO_CAMAS_OCUPADAS INTEGER, $CAMPO_CAMAS_DISPONIVEIS INTEGER )" )
    }

    fun insert(values: ContentValues): Long {
        return db.insert(NOME_TABELA,null, values)
    }

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>): Int {
        return db.update(NOME_TABELA, values, whereClause, whereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>): Int {
        return db.delete(NOME_TABELA,whereClause, whereArgs)
    }

    fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor? {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
    }



    companion object{
        const val NOME_TABELA = "cuidados"
        const val CAMPO_NOME_CUIDADO = "tipo_de_cuidado"
        const val CAMPO_CAMAS_OCUPADAS = "num_camas_ocupadas"
        const val CAMPO_CAMAS_DISPONIVEIS = "num_camas_disponiveis"

        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, CAMPO_NOME_CUIDADO, CAMPO_CAMAS_OCUPADAS, CAMPO_CAMAS_DISPONIVEIS)
    }
}
