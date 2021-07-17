package pt.ipg.covid

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.loader.app.LoaderManager
import androidx.navigation.fragment.findNavController
import pt.ipg.covid.DadosApp.Companion.activity

class EliminaEnfermeiroFragment {

    private lateinit var textViewEnfermeiro: TextView
    private lateinit var textViewIdade: TextView
    private lateinit var textViewSexo: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_enfermeiro
        return inflater.inflate(R.layout.fragment_elimina_enfermeiro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewEnfermeiro = view.findViewById(R.id.textViewNomeEnfermeiro)
        textViewIdade = view.findViewById(R.id.textViewIdade)
        textViewSexo = view.findViewById(R.id.textViewSexo)

        val enfermeiro = DadosApp.enfermeiroSelecionado!!
        textViewEnfermeiro.setText(enfermeiro.nome)
        textViewIdade.setText(enfermeiro.dataNascimento)
        textViewSexo.setText(enfermeiro.sexo)
    }

    fun elimina() {
        val uriEnfermeiro = Uri.withAppendedPath(
            ContentProviderCovid.ENDERECO_TABELA_ENFERMEIRO,
            DadosApp.enfermeiroSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriEnfermeiro,
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