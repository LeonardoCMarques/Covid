package pt.ipg.covid

import androidx.fragment.app.Fragment

class DadosApp {
    companion object {
        lateinit var activity: MainActivity
        lateinit var listaEnfermeiroFragment: ListaEnfermeiroFragment
        lateinit var fragment: Fragment

        var enfermeiroSelecionado : Enfermeiro? = null
        var cuidadoSelecionado : Cuidados? = null
    }
}