package pt.ipg.covid

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

class Utente (var id: Long = -1, var nome: String, var idade: String, var sexo: String, var estado: String, var responsavel: String){
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {
            put(TabelaUtentes.CAMPO_NOME_UTENTE, nome)
            put(TabelaUtentes.CAMPO_DATA_DE_NASCIMENTO, idade)
            put(TabelaUtentes.CAMPO_SEXO, sexo)
            put(TabelaUtentes.CAMPO_ESTADO, estado)
            put(TabelaUtentes.CAMPO_ENFERMEIRO_RESPONSAVEL, responsavel)
        }

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor): Utente {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaUtentes.CAMPO_NOME_UTENTE)
            val colIdade = cursor.getColumnIndex(TabelaUtentes.CAMPO_DATA_DE_NASCIMENTO)
            val colSexo = cursor.getColumnIndex(TabelaUtentes.CAMPO_SEXO)
            val colEstado = cursor.getColumnIndex(TabelaUtentes.CAMPO_ESTADO)
            val colResponsavel = cursor.getColumnIndex(TabelaUtentes.CAMPO_ENFERMEIRO_RESPONSAVEL)

            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)
            val idade = cursor.getString(colIdade)
            val sexo = cursor.getString(colSexo)
            val estado = cursor.getString(colEstado)
            val responsavel = cursor.getString(colResponsavel)

            return Utente(id, nome, idade, sexo, estado, responsavel)
        }
    }
}