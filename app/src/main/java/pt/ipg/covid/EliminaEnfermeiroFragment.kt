package pt.ipg.covid

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class EliminaEnfermeiroFragment : Fragment() {

    private lateinit var textViewNome: TextView
    private lateinit var textViewIdade: TextView
    private lateinit var textViewSexo: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_enfermeiro

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_enfermeiro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNome = view.findViewById(R.id.textViewEnfermeiroEliminaNome)
        textViewIdade = view.findViewById(R.id.textViewEnfermeiroEliminaIdade)
        textViewSexo = view.findViewById(R.id.textViewEnfermeiroEliminaSexo)

        val enfermeiro = DadosApp.enfermeiroSelecionado!!
        textViewNome.setText(enfermeiro.nome)
        textViewIdade.setText(enfermeiro.dataNascimento.toString())
        textViewSexo.setText(enfermeiro.sexo)
    }

    fun navegaListaEnfermeiro() {
        findNavController().navigate(R.id.action_eliminaEnfermeiroFragment_to_ListaEnfermeiroFragment)
    }

    fun elimina() {
        val uriLivro = Uri.withAppendedPath(
            ContentProviderCovid.ENDERECO_TABELA_ENFERMEIRO,
            DadosApp.enfermeiroSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriLivro,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_enfermeiro,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.enfermeiro_eliminado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaEnfermeiro()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_enfermeiro -> elimina()
            R.id.action_cancelar_eliminar_enfermeiro -> navegaListaEnfermeiro()
            else -> return false
        }

        return true
    }

}