package pt.ipg.covid

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Cuidados (var id: Long = -1, var nome: String, var ocupadas: String, var disponiveis: String){
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaCuidados.CAMPO_NOME_CUIDADO, nome)
            put(TabelaCuidados.CAMPO_CAMAS_OCUPADAS, ocupadas)
            put(TabelaCuidados.CAMPO_CAMAS_DISPONIVEIS, disponiveis)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Cuidados {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaCuidados.CAMPO_NOME_CUIDADO)
            val colCamasOcupadas = cursor.getColumnIndex(TabelaCuidados.CAMPO_CAMAS_OCUPADAS)
            val colCamasDisponiveis = cursor.getColumnIndex(TabelaCuidados.CAMPO_CAMAS_DISPONIVEIS)

            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)
            val ocupadas = cursor.getString(colCamasOcupadas)
            val disponiveis = cursor.getString(colCamasDisponiveis)

            return Cuidados(id, nome, ocupadas, disponiveis)
        }
    }
}