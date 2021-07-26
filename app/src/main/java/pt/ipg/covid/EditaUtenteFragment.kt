package pt.ipg.covid

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController

class EditaUtenteFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var editTextNome: EditText
    private lateinit var editTextIdade: EditText
    private lateinit var editTextSexo: EditText
    private lateinit var spinnerCuidados: Spinner
    private lateinit var spinnerResponsavel: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_utente

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edita_utente, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextUtenteEditaEditaNome)
        editTextIdade = view.findViewById(R.id.editTextUtenteEditaEditaIdade)
        editTextSexo = view.findViewById(R.id.editTextUtenteEditaEditaSexo)
        spinnerCuidados = view.findViewById(R.id.spinnerEditaCuidados)
        spinnerResponsavel = view.findViewById(R.id.spinnerEditaResponsavel)

        editTextNome.setText(DadosApp.utenteSelecionado!!.nome)
        editTextIdade.setText(DadosApp.utenteSelecionado!!.dataNascimento.toString())
        editTextSexo.setText(DadosApp.utenteSelecionado!!.sexo)

        LoaderManager.getInstance(this).initLoader(NovoUtenteFragment.ID_LOADER_MANAGER_CUIDADOS, null, this)
        LoaderManager.getInstance(this).initLoader(NovoUtenteFragment.ID_LOADER_MANAGER_RESPONSAVEL, null, this)
    }

    fun navegaListaUtentes() {
        findNavController().navigate(R.id.action_editaUtenteFragment_to_listaUtentesFragment)
    }

    fun guardar() {
        val nome = editTextNome.text.toString()
        if (nome.isEmpty()) {
            editTextNome.setError(getString(R.string.preencha_nome))
            editTextNome.requestFocus()
            return
        }

        val idade = editTextIdade.text.toString()
        if (idade.isEmpty()) {
            editTextIdade.setError(getString(R.string.preencha_idade))
            editTextIdade.requestFocus()
            return
        }

        val sexo = editTextSexo.text.toString()
        if (sexo.isEmpty()) {
            editTextSexo.setError(getString(R.string.preencha_sexo))
            editTextSexo.requestFocus()
            return
        }

        val idCuidados = spinnerCuidados.selectedItemId
        val idResponsavel = spinnerResponsavel.selectedItemId


        val utente = DadosApp.utenteSelecionado!!
        utente.nome = nome
        utente.dataNascimento = idade.toInt()
        utente.sexo = sexo
        utente.servico_internamento = idCuidados.toString()
        utente.responsavel = idResponsavel.toString()

        val uriCovid = Uri.withAppendedPath(
            ContentProviderCovid.ENDERECO_TABELA_UTENTE,
            utente.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriCovid,
            utente.toContentValues(),
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_alterar_enfermeiro,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.utente_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaUtentes()
    }
    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_edita_utente -> guardar()
            R.id.action_cancelar_edita_utente -> navegaListaUtentes()
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