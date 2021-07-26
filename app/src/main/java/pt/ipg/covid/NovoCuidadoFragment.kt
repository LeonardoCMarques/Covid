package pt.ipg.covid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.covid.databinding.FragmentNovoCuidadoBinding


class NovoCuidadoFragment : Fragment() {

    private var _binding: FragmentNovoCuidadoBinding? = null

    private lateinit var editTextNovoCuidadoNome: EditText
    private lateinit var editTextNovoCuidadoCamasOcupadas: EditText
    private lateinit var editTextNovoCuidadoCamasDisponiveis: EditText

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_cuidado

        _binding = FragmentNovoCuidadoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNovoCuidadoNome = view.findViewById(R.id.editTextCuidadoNovoNome)
        editTextNovoCuidadoCamasOcupadas = view.findViewById(R.id.editTextCuidadoCamasOcupadas)
        editTextNovoCuidadoCamasDisponiveis = view.findViewById(R.id.editTextCuidadoCamasDisponiveis)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun navegaListaCuidados() {
        findNavController().navigate(R.id.action_novoCuidadoFragment_to_listaCuidadosFragment)
    }

    fun guardar() {
        val nomeCuidado = editTextNovoCuidadoNome.text.toString()
        if (nomeCuidado.isEmpty()) {
            editTextNovoCuidadoNome.setError(getString(R.string.preencha_nome))
            editTextNovoCuidadoNome.requestFocus()
            return
        }
        val camasOcupadas = editTextNovoCuidadoCamasOcupadas.text.toString()
        if (nomeCuidado.isEmpty()) {
            editTextNovoCuidadoCamasOcupadas.setError(getString(R.string.preencha_nome))
            editTextNovoCuidadoCamasOcupadas.requestFocus()
            return
        }
        val camasDisponiveis = editTextNovoCuidadoCamasDisponiveis.text.toString()
        if (nomeCuidado.isEmpty()) {
            editTextNovoCuidadoCamasDisponiveis.setError(getString(R.string.preencha_nome))
            editTextNovoCuidadoCamasDisponiveis.requestFocus()
            return
        }

        val cuidados = Cuidados(nome= nomeCuidado, ocupadas= camasOcupadas, disponiveis = camasDisponiveis)

        val uri = activity?.contentResolver?.insert(
            ContentProviderCovid.ENDERECO_TABELA_CUIDADO,
            cuidados.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNovoCuidadoNome,
                getString(R.string.erro_inserir_nome_cuidado),
                Snackbar.LENGTH_LONG
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
            R.id.action_guardar_novo_cuidado -> guardar()
            R.id.action_cancelar_novo_cuidado -> navegaListaCuidados()
            else -> return false
        }

        return true
    }

}