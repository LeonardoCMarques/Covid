package pt.ipg.covid

class DadosApp {
    companion object {
        lateinit var activity: MainActivity
        lateinit var listaEnfermeiroFragment: ListaEnfermeiroFragment
        var enfermeiroSelecionado : Enfermeiro? = null
    }
}