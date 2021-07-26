package pt.ipg.covid

import androidx.fragment.app.Fragment

class DadosApp {
    companion object {
        lateinit var activity: MainActivity
        lateinit var fragment: Fragment

        var enfermeiroSelecionado : Enfermeiro? = null
        var cuidadoSelecionado : Cuidados? = null
        var utenteSelecionado : Utente? = null
    }
}