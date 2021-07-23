package pt.ipg.covid

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController


class EditaEnfermeiroFragment : Fragment() {
    private lateinit var editTextNome: EditText
    private lateinit var editTextIdade: EditText
    private lateinit var editTextSexo: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_edita_enfermeiro

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edita_enfermeiro, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextEnfermeiroEditaNome)
        editTextIdade = view.findViewById(R.id.editTextEnfermeiroEditaIdade)
        editTextSexo = view.findViewById(R.id.editTextEnfermeiroEditaSexo)

        editTextNome.setText(DadosApp.enfermeiroSelecionado!!.nome)
        editTextIdade.setText(DadosApp.enfermeiroSelecionado!!.dataNascimento.toString())
        editTextSexo.setText(DadosApp.enfermeiroSelecionado!!.sexo)
    }

    fun navegaListaEnfermeiro() {
        findNavController().navigate(R.id.action_editaEnfermeiroFragment_to_ListaEnfermeiroFragment)
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



        val enfermeiro = DadosApp.enfermeiroSelecionado!!
        enfermeiro.nome = nome
        enfermeiro.dataNascimento = idade.toInt()
        enfermeiro.sexo = sexo

        val uriCovid = Uri.withAppendedPath(
            ContentProviderCovid.ENDERECO_TABELA_ENFERMEIRO,
            enfermeiro.id.toString()
        )

        val registos = activity?.contentResolver?.update(
            uriCovid,
            enfermeiro.toContentValues(),
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
            R.string.enfermeiro_guardado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaEnfermeiro()
    }
    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_guardar_edita_enfermeiro -> guardar()
            R.id.action_cancelar_edita_enfermeiro -> navegaListaEnfermeiro()
            else -> return false
        }

        return true
    }

}