package pt.ipg.covid

class DadosApp {
    companion object {
        lateinit var activity: MainActivity
        lateinit var listaEnfermeiroFragment: ListaEnfermeiroFragment
        var novoEnfermeiroFragment: NovoEnfermeiroFragment? = null
        var enfermeiroSelecionado : Enfermeiro? = null
    }
}