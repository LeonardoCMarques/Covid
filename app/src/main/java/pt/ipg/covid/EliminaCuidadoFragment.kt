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

class EliminaCuidadoFragment : Fragment() {

    private lateinit var textViewNomeCuidado: TextView
    private lateinit var textViewCamasOcupadas: TextView
    private lateinit var textViewCamasDisponiveis: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_elimina_cuidado

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elimina_cuidado, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNomeCuidado = view.findViewById(R.id.textViewCuidadoEliminaCuidado)
        textViewCamasOcupadas = view.findViewById(R.id.textViewCuidadoEliminaCamaOcupada)
        textViewCamasDisponiveis = view.findViewById(R.id.textViewCuidadoEliminaCamaDisponivel)

        val cuidado = DadosApp.cuidadoSelecionado!!
        textViewNomeCuidado.setText(cuidado.nome)
        textViewCamasOcupadas.setText(cuidado.ocupadas)
        textViewCamasDisponiveis.setText(cuidado.disponiveis)
    }

    fun navegaListaCuidados() {
        findNavController().navigate(R.id.action_eliminaCuidadoFragment_to_listaCuidadosFragment)
    }

    fun elimina() {
        val uriCovid = Uri.withAppendedPath(
            ContentProviderCovid.ENDERECO_TABELA_CUIDADO,
            DadosApp.cuidadoSelecionado!!.id.toString()
        )

        val registos = activity?.contentResolver?.delete(
            uriCovid,
            null,
            null
        )

        if (registos != 1) {
            Toast.makeText(
                requireContext(),
                R.string.erro_eliminar_cuidado,
                Toast.LENGTH_LONG
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            R.string.cuidado_eliminado_sucesso,
            Toast.LENGTH_LONG
        ).show()
        navegaListaCuidados()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_confirma_eliminar_cuidado -> elimina()
            R.id.action_cancelar_eliminar_cuidado -> navegaListaCuidados()
            else -> return false
        }

        return true
    }

}