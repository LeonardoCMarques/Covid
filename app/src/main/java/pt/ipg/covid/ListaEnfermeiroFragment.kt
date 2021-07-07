package pt.ipg.covid

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.ipg.covid.databinding.FragmentListaEnfermeiroBinding
import pt.ipg.livros.ContentProviderCovid

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListaEnfermeiroFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentListaEnfermeiroBinding? = null
    private var adapterEnfermeiros : AdapterEnfermeiros? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.listaEnfermeiroFragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_lista_enfermeiros
        _binding = FragmentListaEnfermeiroBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewEnfermeiros = view.findViewById<RecyclerView>(R.id.recyclerViewEnfermeiros)
        adapterEnfermeiros = AdapterEnfermeiros((this))
        recyclerViewEnfermeiros.adapter = adapterEnfermeiros
        recyclerViewEnfermeiros.layoutManager = LinearLayoutManager(requireContext())

        LoaderManager.getInstance(this)
            .initLoader(ID_LOADER_MANAGER_EFERMEIRO, null, this)
    }
    fun navegaNovoEnfermeiro() {
        findNavController().navigate(R.id.action_ListaEnfermeiroFragment_to_NovoEnfermeiroFragment)
    }
    fun navegaAlterarEnfermeiro() {
        //todo: navegar para o fragmento da edição de um enfermeiro
    }

    fun navegaEliminarEnfermeiro() {
        //todo: navegar para o fragmento para confirmar eliminação de um enfermeiro
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_novo_enfermeiro -> navegaNovoEnfermeiro()
            R.id.action_alterar_enfermeiro -> navegaAlterarEnfermeiro()
            R.id.action_eliminar_enfermeiro -> navegaEliminarEnfermeiro()
            else -> return false
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        return CursorLoader(
            requireContext(),
            ContentProviderCovid.ENDERECO_TABELA_ENFERMEIRO,
            TabelaEnfermeiros.TODAS_COLUNAS,
            null, null,
            TabelaEnfermeiros.CAMPO_NOME_ENFERMEIRO
        )
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterEnfermeiros!!.cursor = data
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterEnfermeiros!!.cursor = null
    }

    companion object {
        const val ID_LOADER_MANAGER_EFERMEIRO = 0
    }

}