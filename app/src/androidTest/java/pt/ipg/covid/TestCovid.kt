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

    private fun insereUtentes(tabela: TabelaUtentes, utente: Utente): Long {
        val id = tabela.insert(utente.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun insereCuidados(tabela: TabelaCuidados, cuidados: Cuidados): Long {
        val id = tabela.insert(cuidados.toContentValues())
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

    private fun getCuidadoBaseDados(tabela: TabelaCuidados, id: Long): Cuidados {
        val cursor = tabela.query(
            TabelaCuidados.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Cuidados.fromCursor(cursor)
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

        val enfermeiro = Enfermeiro(nome = "Hugo", dataNascimento = 6121997, sexo = "masculino")
        enfermeiro.id = insereEnfermeiros(tabelaEnfermeiros, enfermeiro)

       assertEquals(enfermeiro, getEnfermeiroBaseDados(tabelaEnfermeiros, enfermeiro.id))

        db.close()
    }

    @Test
    fun consegueAlterarEnfermeiros() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaEnfermeiros = TabelaEnfermeiros(db)

        val enfermeiro = Enfermeiro(nome = "Filipa", dataNascimento = 7022001, sexo = "feminino")
        enfermeiro.id = insereEnfermeiros(tabelaEnfermeiros, enfermeiro)

        enfermeiro.nome = "Maria"

        val registosAlterados = tabelaEnfermeiros.update(
            enfermeiro.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(enfermeiro.id.toString())
        )

        assertEquals(1, registosAlterados)
        assertEquals(enfermeiro, getEnfermeiroBaseDados(tabelaEnfermeiros, enfermeiro.id))
        db.close()
    }

    @Test
    fun consegueEliminarEnfermeiros() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaEnfermeiros = TabelaEnfermeiros(db)

        val enfermeiro = Enfermeiro(nome = "Maria", dataNascimento = 7022001, sexo = "feminino")
        enfermeiro.id = insereEnfermeiros(tabelaEnfermeiros, enfermeiro)

        val registosEliminados = tabelaEnfermeiros.delete(
            "${BaseColumns._ID}=?",
            arrayOf(enfermeiro.id.toString())
        )

        assertEquals(1, registosEliminados)
        db.close()
    }

    @Test
    fun consegueLerEnfermeiros() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaEnfermeiros = TabelaEnfermeiros(db)

        val enfermeiro = Enfermeiro(nome = "Hugo", dataNascimento = 6121997,sexo = "masculino")
        enfermeiro.id = insereEnfermeiros(tabelaEnfermeiros, enfermeiro)

        assertEquals(enfermeiro, getEnfermeiroBaseDados(tabelaEnfermeiros, enfermeiro.id))
        db.close()
    }

    @Test
    fun consegueInserirUtentes() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaUtentes = TabelaUtentes(db)

        val utente = Utente(nome ="Marcelo" , dataNascimento = 17062001, sexo = "masculino", servico_internamento ="infermaria", responsavel ="Hugo")
        utente.id = insereUtentes(tabelaUtentes, utente)

        assertEquals(utente, getUtenteBaseDados(tabelaUtentes, utente.id))

        db.close()
    }

    @Test
    fun consegueAlterarUtentes() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaUtentes = TabelaUtentes(db)

        val utente = Utente(nome ="Gabriela" , dataNascimento = 13012003, sexo = "feminino", servico_internamento ="infermaria", responsavel ="Miguel")
        utente.id = insereUtentes(tabelaUtentes, utente)

        utente.nome = "Diana"

        val registosAlterados = tabelaUtentes.update(
            utente.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(utente.id.toString())
        )

        assertEquals(1, registosAlterados)
        assertEquals(utente, getUtenteBaseDados(tabelaUtentes, utente.id))
        db.close()
    }

    @Test
    fun consegueEliminarUtentes() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaUtentes = TabelaUtentes(db)

        val utente = Utente(nome ="Diana" , dataNascimento = 13012003, sexo = "feminino", servico_internamento ="infermaria", responsavel ="Miguel")
        utente.id = insereUtentes(tabelaUtentes, utente)

        val registosEliminados = tabelaUtentes.delete(
            "${BaseColumns._ID}=?",
            arrayOf(utente.id.toString())
        )

        assertEquals(1, registosEliminados)
        db.close()
    }

    @Test
    fun consegueLerUtentes() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaUtentes = TabelaUtentes(db)

        val utente = Utente(nome ="Marcelo" , dataNascimento = 17062001, sexo = "masculino", servico_internamento ="infermaria", responsavel ="Hugo")
        utente.id = insereUtentes(tabelaUtentes, utente)

        assertEquals(utente, getUtenteBaseDados(tabelaUtentes, utente.id))
        db.close()
    }

    @Test
    fun consegueInserirCuidados() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaCuidados = TabelaCuidados(db)

        val cuidados = Cuidados(nome ="Marcelo" , ocupadas = "3", disponiveis = "0")
        cuidados.id = insereCuidados(tabelaCuidados, cuidados)

        assertEquals(cuidados, getCuidadoBaseDados(tabelaCuidados, cuidados.id))

        db.close()
    }

    @Test
    fun consegueAlterarCuidados() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaCuidados = TabelaCuidados(db)

        val cuidados = Cuidados(nome ="Diana" , ocupadas = "2", disponiveis = "1")
        cuidados.id = insereCuidados(tabelaCuidados, cuidados)

        cuidados.nome = "Diana"

        val registosAlterados = tabelaCuidados.update(
            cuidados.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(cuidados.id.toString())
        )

        assertEquals(1, registosAlterados)
        assertEquals(cuidados, getCuidadoBaseDados(tabelaCuidados, cuidados.id))
        db.close()
    }

    @Test
    fun consegueEliminarCuidados() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaCuidados = TabelaCuidados(db)

        val cuidados = Cuidados(nome ="Diana" , ocupadas = "2", disponiveis = "1")
        cuidados.id = insereCuidados(tabelaCuidados, cuidados)

        val registosEliminados = tabelaCuidados.delete(
            "${BaseColumns._ID}=?",
            arrayOf(cuidados.id.toString())
        )

        assertEquals(1, registosEliminados)
        db.close()
    }

    @Test
    fun consegueLerCuidados() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaCuidados = TabelaCuidados(db)

        val cuidados = Cuidados(nome ="Marcelo" , ocupadas = "3", disponiveis = "0")
        cuidados.id = insereCuidados(tabelaCuidados, cuidados)

        assertEquals(cuidados, getCuidadoBaseDados(tabelaCuidados, cuidados.id))
        db.close()
    }

}