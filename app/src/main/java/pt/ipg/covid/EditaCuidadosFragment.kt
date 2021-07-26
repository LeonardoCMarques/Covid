package pt.ipg.covid

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class EditaCuidadosFragment : Fragment() {

    private lateinit var editTextNomeCuidado: EditText
    private lateinit var editTextCamasOcupadas: EditText
    private lateinit var editTextCamasDisponiveis: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_cuidados

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edita_cuidados, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNomeCuidado = view.findViewById(R.id.editTextCuidadoNome)
        editTextCamasOcupadas = view.findViewById(R.id.editTextCuidadoCamasOcupadas)
        editTextCamasDisponiveis = view.findViewById(R.id.editTextCuidadoCamasDisponiveis)

        editTextNomeCuidado.setText(DadosApp.cuidadoSelecionado!!.nome)
        editTextCamasOcupadas.setText(DadosApp.cuidadoSelecionado!!.ocupadas)
        editTextCamasDisponiveis.setText(DadosApp.cuidadoSelecionado!!.disponiveis)
    }

    fun navegaListaCuidados() {
        findNavController().navigate(R.id.action_editaCuidadosFragment_to_listaCuidadosFragment)
    }

    fun guardar() {
        val nomeCuidado = editTextNomeCuidado.text.toString()
        if (nomeCuidado.isEmpty()) {
            editTextNomeCuidado.setError(getString(R.string.preencha_campo))
            editTextNomeCuidado.requestFocus()
            return
        }

        val camasOcupadas = editTextCamasOcupadas.text.toString()
        if (camasOcupadas.isEmpty()) {
            editTextCamasOcupadas.setError(getString(R.string.preencha_campo))
            editTextCamasOcupadas.requestFocus()
            return
        }

        val camasDisponiveis = editTextCamasDisponiveis.text.toString()
        if (camasDisponiveis.isEmpty()) {
            editTextCamasDisponiveis.setError(getString(R.string.preencha_campo))
            editTextCamasDisponiveis.requestFocus()
            return
        }



        val cuidados = DadosApp.cuidadoSelecionado!!
        cuidados.nome = nomeCuidado
        cuidados.ocupadas = camasOcupadas
        cuidados.disponiveis = camasDisponiveis

        val uriCovid = Uri.withAppendedPath(
            ContentProviderCovid.ENDERECO_TABELA_CUIDADO,
            cuidados.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriCovid,
            cuidados.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_alterar_cuidados,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.cuidado_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaCuidados()
    }
    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_edita_cuidados -> guardar()
            R.id.action_cancelar_edita_cuidados -> navegaListaCuidados()
            else -> return false
        }

        return true
    }

}