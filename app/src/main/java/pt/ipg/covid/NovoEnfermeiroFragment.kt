package pt.ipg.covid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.covid.databinding.FragmentNovoEnfermeiroBinding
import pt.ipg.livros.ContentProviderCovid

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NovoEnfermeiroFragment : Fragment() {

    private var _binding: FragmentNovoEnfermeiroBinding? = null

    private lateinit var editTextNome: EditText
    private lateinit var editTextIdade: EditText
    private lateinit var editTextSexo: EditText

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_enfermeiro

        _binding = FragmentNovoEnfermeiroBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextEnfermeiroNome)
        editTextIdade = view.findViewById(R.id.editTextEnfermeiroIdade)
        editTextSexo = view.findViewById(R.id.editTextEnfermeiroSexo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun navegaListaEnfermeiro() {
        findNavController().navigate(R.id.action_ListaEnfermeiroFragment_to_NovoEnfermeiroFragment)
    }

    fun guardar() {
        val nomeEnfermeiro = editTextNome.text.toString()
        if (nomeEnfermeiro.isEmpty()) {
            editTextNome.setError(getString(R.string.preencha_nome))
            return
        }

        val idadeEnfermeiro = editTextIdade.text.toString()
        if (idadeEnfermeiro.isEmpty()) {
            editTextIdade.setError(getString(R.string.preencha_idade))
            return
        }

        val sexoEnfermeiro = editTextSexo.text.toString()
        if (sexoEnfermeiro.isEmpty()) {
            editTextSexo.setError(getString(R.string.preencha_sexo))
            return
        }


        val enfermeiro = Enfermeiro(nome = nomeEnfermeiro, dataNascimento = idadeEnfermeiro, sexo = sexoEnfermeiro)

        val uri = activity?.contentResolver?.insert(
            ContentProviderCovid.ENDERECO_TABELA_ENFERMEIRO,
            enfermeiro.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNome,
                getString(R.string.erro_inserir_enfermeiro),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        navegaListaEnfermeiro()

    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_enfermeiro -> guardar()
            R.id.action_cancelar_novo_enfermeiro -> navegaListaEnfermeiro()
            else -> return false
        }

        return true
    }
}