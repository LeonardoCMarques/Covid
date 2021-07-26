package pt.ipg.covid

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import pt.ipg.covid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var menu: Menu

    var menuAtual = R.menu.menu_lista_enfermeiros
        set(value) {
            field = value
            invalidateOptionsMenu()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        DadosApp.activity = this

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(menuAtual, menu)

        this.menu = menu
        if (menuAtual == R.menu.menu_lista_enfermeiros) {
            atualizaMenuListaEnfermeiros(false)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val opcaoProcessada = when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, R.string.versao, Toast.LENGTH_LONG).show()
                true
            }

            else -> when(menuAtual) {
                R.menu.menu_lista_enfermeiros -> (DadosApp.fragment as ListaEnfermeiroFragment).processaOpcaoMenu(item)
                R.menu.menu_novo_enfermeiro -> (DadosApp.fragment as NovoEnfermeiroFragment).processaOpcaoMenu(item)
                R.menu.menu_edita_enfermeiro -> (DadosApp.fragment as EditaEnfermeiroFragment).processaOpcaoMenu(item)
                R.menu.menu_elimina_enfermeiro -> (DadosApp.fragment as EliminaEnfermeiroFragment).processaOpcaoMenu(item)
                R.menu.menu_lista_cuidados -> (DadosApp.fragment as ListaCuidadosFragment).processaOpcaoMenu(item)
                R.menu.menu_novo_cuidado -> (DadosApp.fragment as NovoCuidadoFragment).processaOpcaoMenu(item)
                R.menu.menu_edita_cuidados -> (DadosApp.fragment as EditaCuidadosFragment).processaOpcaoMenu(item)
                R.menu.menu_elimina_cuidado -> (DadosApp.fragment as EliminaCuidadoFragment).processaOpcaoMenu(item)
                R.menu.menu_lista_utentes -> (DadosApp.fragment as ListaUtentesFragment).processaOpcaoMenu(item)
                R.menu.menu_novo_utente -> (DadosApp.fragment as NovoUtenteFragment).processaOpcaoMenu(item)
                else -> false
            }
        }
        return if(opcaoProcessada) true else super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun atualizaMenuListaEnfermeiros(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_enfermeiro).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_enfermeiro).setVisible(mostraBotoesAlterarEliminar)
    }

    fun atualizaMenuListaCuidados(mostraBotoesAlterarEliminar: Boolean){
        menu.findItem(R.id.action_alterar_cuidado).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_cuidado).setVisible(mostraBotoesAlterarEliminar)
    }

    fun atualizaMenuListaUtentes(mostraBotoesAlterarEliminar: Boolean){
        menu.findItem(R.id.action_alterar_utente).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_utente).setVisible(mostraBotoesAlterarEliminar)
    }

    fun navegaListaEnfermeiro(view: View) {
        findNavController(view.id).navigate(R.id.action_mainPage_to_ListaEnfermeiroFragment)
    }
    fun navegaListaCuidados(view: View) {
        findNavController(view.id).navigate(R.id.action_mainPage_to_listaCuidadosFragment)
    }

    fun navegaListaUtentes(view: View) {
        findNavController(view.id).navigate(R.id.action_mainPage_to_listaUtentesFragment)
    }

}