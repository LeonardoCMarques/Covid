package pt.ipg.covid

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaNumCasosCovid(db: SQLiteDatabase) {
    private val db: SQLiteDatabase = db

    fun cria(){
        db.execSQL("CREATE TABLE $NOME_TABELA ( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_ID_NUM_CASOS_INFETADOS INTEGER, $CAMPO_ID_NUM_CASOS_RECUPERADOS INTEGER, $CAMPO_ID_NUM_VACINADOS INTEGER, FOREIGN KEY ($CAMPO_ID_NUM_CASOS_INFETADOS) REFERENCES ${TabelaNumCasosInfetados.NOME_TABELA}, FOREIGN KEY ($CAMPO_ID_NUM_CASOS_RECUPERADOS) REFERENCES ${TabelaNumCasosRecuperados.NOME_TABELA}, FOREIGN KEY ($CAMPO_ID_NUM_VACINADOS) REFERENCES ${TabelaNumVacinados.NOME_TABELA} )" )
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
        selection: String,
        selectionArgs: Array<String>,
        groupBy: String,
        having: String,
        orderBy: String
    ): Cursor? {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object{
        const val NOME_TABELA = "casosCovid"
        const val CAMPO_ID_NUM_CASOS_INFETADOS = "id-num-casos-infetados"
        const val CAMPO_ID_NUM_CASOS_RECUPERADOS = "id_num_casos_recuperados"
        const val CAMPO_ID_NUM_VACINADOS = "id_num_vacinados"
    }

}
