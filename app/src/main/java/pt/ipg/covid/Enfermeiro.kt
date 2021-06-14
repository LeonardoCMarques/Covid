package pt.ipg.covid

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Enfermeiro (var id: Long = -1, var nome: String, var dataNascimento: String, var sexo: String){
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaEnfermeiros.CAMPO_NOME_ENFERMEIRO, nome)
            put(TabelaEnfermeiros.CAMPO_DATA_DE_NASCIMENTO, dataNascimento)
            put(TabelaEnfermeiros.CAMPO_SEXO, sexo)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Enfermeiro {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaEnfermeiros.CAMPO_NOME_ENFERMEIRO)
            val colDataNsacimento = cursor.getColumnIndex(TabelaEnfermeiros.CAMPO_DATA_DE_NASCIMENTO)
            val colSexo = cursor.getColumnIndex(TabelaEnfermeiros.CAMPO_SEXO)

            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)
            val dataNascimento = cursor.getString(colDataNsacimento)
            val sexo = cursor.getString(colSexo)

            return Enfermeiro(id, nome, dataNascimento, sexo)
        }
    }
}