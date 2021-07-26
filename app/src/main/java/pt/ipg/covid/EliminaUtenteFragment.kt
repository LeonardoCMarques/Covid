package pt.ipg.covid

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class EliminaUtenteFragment : Fragment() {

    private lateinit var textViewUtenteEliminaNome: TextView
    private lateinit var textViewUtenteEliminaIdade: TextView
    private lateinit var textViewUtenteEliminaSexo: TextView
    private lateinit var textViewUtenteEliminaCuidados: TextView
    private lateinit var textViewUtenteEliminaResponsavel: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_utente

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_utente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewUtenteEliminaNome = view.findViewById(R.id.textViewUtenteEliminaNome)
        textViewUtenteEliminaIdade = view.findViewById(R.id.textViewUtenteEliminaIdade)
        textViewUtenteEliminaSexo = view.findViewById(R.id.textViewUtenteEliminaSexo)
        textViewUtenteEliminaCuidados = view.findViewById(R.id.textViewUtenteEliminaServicoInternamento)
        textViewUtenteEliminaResponsavel = view.findViewById(R.id.textViewUtenteEliminaenfermeiroResponsavel)


        textViewUtenteEliminaNome.setText(DadosApp.utenteSelecionado!!.nome)
        textViewUtenteEliminaIdade.setText(DadosApp.utenteSelecionado!!.dataNascimento.toString())
        textViewUtenteEliminaSexo.setText(DadosApp.utenteSelecionado!!.sexo)
        textViewUtenteEliminaCuidados.setText(DadosApp.utenteSelecionado!!.servico_internamento)
        textViewUtenteEliminaResponsavel.setText(DadosApp.utenteSelecionado!!.responsavel)

    }

    fun navegaListaUtente() {
        findNavController().navigate(R.id.action_eliminaUtenteFragment_to_listaUtentesFragment)
    }

    fun elimina() {
        val uriCovid = Uri.withAppendedPath(
            ContentProviderCovid.ENDERECO_TABELA_UTENTE,
            DadosApp.utenteSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriCovid,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_utente,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.utente_eliminado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaUtente()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_utente -> elimina()
            R.id.action_cancelar_eliminar_utente -> navegaListaUtente()
            else -> return false
        }

        return true
    }

}