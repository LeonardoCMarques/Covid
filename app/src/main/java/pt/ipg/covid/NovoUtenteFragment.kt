package pt.ipg.covid

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.ipg.covid.databinding.FragmentNovoUtenteBinding
import androidx.loader.content.CursorLoader
import android.widget.SimpleCursorAdapter


class NovoUtenteFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentNovoUtenteBinding? = null

    private lateinit var editTextNome: EditText
    private lateinit var editTextIdade: EditText
    private lateinit var editTextSexo: EditText
    private lateinit var spinnerCuidados: Spinner
    private lateinit var spinnerResponsavel: Spinner

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_utente

        _binding = FragmentNovoUtenteBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextUtenteEditaNome)
        editTextIdade = view.findViewById(R.id.editTextUtenteEditaIdade)
        editTextSexo = view.findViewById(R.id.editTextUtenteEditaSexo)
        spinnerCuidados = view.findViewById(R.id.spinnerCuidados)
        spinnerResponsavel = view.findViewById(R.id.spinnerResponsavel)

        LoaderManager.getInstance(this).initLoader(ID_LOADER_MANAGER_CUIDADOS, null, this)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_MANAGER_RESPONSAVEL, null, this)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun navegaListaUtente() {
        findNavController().navigate(R.id.action_novoUtenteFragment_to_listaUtentesFragment)
    }

    fun guardar() {
        val nomeUtente = editTextNome.text.toString()
        if (nomeUtente.isEmpty()) {
            editTextNome.setError(getString(R.string.preencha_nome))
            editTextNome.requestFocus()
            return
        }

        val idadeUtente = editTextIdade.text.toString()
        if (idadeUtente.isEmpty()) {
            editTextIdade.setError(getString(R.string.preencha_idade))
            editTextIdade.requestFocus()
            return
        }

        val sexoUtente = editTextSexo.text.toString()
        if (sexoUtente.isEmpty()) {
            editTextSexo.setError(getString(R.string.preencha_sexo))
            editTextSexo.requestFocus()
            return
        }

        val idCuidados = spinnerCuidados.selectedItemId
        val idResponsavel = spinnerResponsavel.selectedItemId

        val utente = Utente(nome = nomeUtente, dataNascimento = idadeUtente.toInt(), sexo = sexoUtente, servico_internamento = idCuidados.toString(), responsavel = idResponsavel.toString())

        val uri = activity?.contentResolver?.insert(
            ContentProviderCovid.ENDERECO_TABELA_UTENTE,
            utente.toContentValues()
        )

        if (uri == null) {
            Snackbar.make(
                editTextNome,
                getString(R.string.erro_inserir_utente),
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.utente_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaUtente()

    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_novo_utente -> guardar()
            R.id.action_cancelar_novo_utente -> navegaListaUtente()
            else -> return false
        }

        return true
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        when(id){
            0 -> return CursorLoader(
                requireContext(),
                ContentProviderCovid.ENDERECO_TABELA_CUIDADO,
                TabelaCuidados.TODAS_COLUNAS,
                null, null,
                TabelaCuidados.CAMPO_NOME_CUIDADO
            )

            else -> return CursorLoader(
                requireContext(),
                ContentProviderCovid.ENDERECO_TABELA_ENFERMEIRO,
                TabelaEnfermeiros.TODAS_COLUNAS,
                null, null,
                TabelaEnfermeiros.CAMPO_NOME_ENFERMEIRO
            )

        }

    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        when(loader.id){
            0 -> atualizaSpinnerCuidado(data)
            1 -> atualizaSpinnerResponsavel(data)
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        when(loader.id){
            0 -> atualizaSpinnerCuidado(null)
            1 -> atualizaSpinnerResponsavel(null)
        }
    }

    private fun atualizaSpinnerCuidado(data: Cursor?) {
        spinnerCuidados.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaCuidados.CAMPO_NOME_CUIDADO),
            intArrayOf(android.R.id.text1),
            0
        )
    }

    private fun atualizaSpinnerResponsavel(data: Cursor?) {
        spinnerResponsavel.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaEnfermeiros.CAMPO_NOME_ENFERMEIRO),
            intArrayOf(android.R.id.text1),
            0
        )
    }

    companion object {
        const val ID_LOADER_MANAGER_CUIDADOS = 0
        const val ID_LOADER_MANAGER_RESPONSAVEL = 1
    }


}