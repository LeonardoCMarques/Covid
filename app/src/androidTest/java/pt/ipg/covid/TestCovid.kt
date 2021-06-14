package pt.ipg.covid

import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestBaseDados {
    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBdCovidOpenHelper() = BdCovidOpenHelper(getAppContext())

    private fun insereEnfermeiros(tabela: TabelaEnfermeiros, enfermeiro: Enfermeiro): Long {
        val id = tabela.insert(enfermeiro.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun insereUtente(tabela: TabelaUtentes, utente: Utente): Long {
        val id = tabela.insert(utente.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun getEnfermeiroBaseDados(tabela: TabelaEnfermeiros, id: Long): Enfermeiro {
        val cursor = tabela.query(
            TabelaEnfermeiros.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Enfermeiro.fromCursor(cursor)
    }

    private fun getUtenteBaseDados(tabela: TabelaUtentes, id: Long): Utente {
        val cursor = tabela.query(
            TabelaUtentes.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Utente.fromCursor(cursor)
    }

    @Before
    fun apagaBaseDados(){
        getAppContext().deleteDatabase(BdCovidOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val db = getBdCovidOpenHelper().readableDatabase
        assert(db.isOpen)
        db.close()
    }

    @Test
    fun consegueInserirEnfermeiros() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaEnfermeiros = TabelaEnfermeiros(db)

        val enfermeiro = Enfermeiro(nome = "Hugo", dataNascimento = "6-12-2000", sexo = "masculino")
        enfermeiro.id = insereEnfermeiros(tabelaEnfermeiros, enfermeiro)

       assertEquals(enfermeiro, getEnfermeiroBaseDados(tabelaEnfermeiros, enfermeiro.id))

        db.close()
    }

    @Test
    fun consegueInserirUtente() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaUtentes = TabelaUtentes(db)

        val utente = Utente(nome ="Marcelo" , dataNascimento = "17-6-2001", sexo = "masculino", servico_internamento ="infermaria", responsavel ="Hugo")
        utente.id = insereUtente(tabelaUtentes, utente)

        assertEquals(utente, getUtenteBaseDados(tabelaUtentes, utente.id))

        db.close()
    }

}