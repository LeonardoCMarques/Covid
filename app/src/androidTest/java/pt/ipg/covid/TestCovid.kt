package pt.ipg.covid

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

    @Before
    fun apagaBaseDados(){
        getAppContext().deleteDatabase(BdCovidOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados(){
        val dbOpenHelper = BdCovidOpenHelper(getAppContext())
        val db = dbOpenHelper.readableDatabase
        assert(db.isOpen)
        db.close()
    }
}