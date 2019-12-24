package com.brolo.jackal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map
import com.brolo.jackal.ui.main.*
import com.brolo.jackal.viewmodel.GamesViewModel
import com.brolo.jackal.viewmodel.MapsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(R.layout.main_activity),
    LogGameDialogFragment.LogGameDialogFragmentListener,
    GameAdapter.OnGameClickListener,
    GameOptionsDialogFragment.GameOptionsListener {

    private lateinit var viewModel: GamesViewModel
    private lateinit var mapsViewModel: MapsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    companion object {
        const val EDIT_GAME_REQUEST = 1
        const val RESULT_GAME_ID = "RESULT_GAME_ID"
        const val RESULT_ARG_DID_WIN = "RESULT_ARG_DID_WIN"
    }

    private val gameObserver = Observer<List<Game>> {
        message.text = resources.getQuantityString(R.plurals.total_games, it.size, it.size)

        val allMaps = mapsViewModel.allMaps.value

        if (allMaps != null) {
            setupRecyclerView(it, allMaps)
        }
    }

    private val mapsObserver = Observer<List<Map>> {
        val allGames = viewModel.allGames.value

        if (allGames != null) {
            setupRecyclerView(allGames, it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)
        mapsViewModel = ViewModelProviders.of(this).get(MapsViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(R.id.chart_fragment_container, PieChartFragment.newInstance())
            .commit()

        observeGamesViewModel(viewModel)

        setupLogGameClick()
        setupFilterChips()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EDIT_GAME_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val gameId = data?.extras?.getInt(RESULT_GAME_ID)
                val game = viewModel.allGames.value?.find { g -> g.id == gameId }
                game?.didWin = data?.extras?.getBoolean(RESULT_ARG_DID_WIN)

                if (game != null) {
                    updateGame(game)
                }
            }
        }
    }

    override fun onGameCreated(game: Game) {
        insertGame(game)
    }

    override fun onGameDeleted(gameId: Int) {
        viewModel.allGames.value?.let {
            val game = it.find { game -> game.id == gameId }

            if (game != null) {
                viewModel.delete(game)

                Toast.makeText(applicationContext, R.string.game_deleted, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRecordGameResult(gameId: Int) {
        viewModel.allGames.value?.let {
            val game = it.find { game -> game.id == gameId }

            if (game != null) {
                val intent = GameEditActivity.newIntent(this, game)

                startActivityForResult(intent, EDIT_GAME_REQUEST)
            }
        }
    }

    override fun onGameClick(position: Int) {
        viewModel.allGames.value?.let {
            val game = it[position]
            val gameOptionsFragment = GameOptionsDialogFragment.newInstance(game.id)

            gameOptionsFragment.show(supportFragmentManager, "game_options_fragment")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_options_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option_export_csv -> writeCSVFile()
            R.id.option_delete_all_games -> confirmAndDeleteAllGameData()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateGame(game: Game) {
        viewModel.update(game)
    }

    private fun observeGamesViewModel(gamesViewModel: GamesViewModel) {
        gamesViewModel.allGames.observe(this, gameObserver)
        mapsViewModel.allMaps.observe(this, mapsObserver)
    }

    private fun setupRecyclerView(games: List<Game>, maps: List<Map>) {
        viewManager = LinearLayoutManager(this)
        viewAdapter = GameAdapter(games.take(20), maps, this)

        recyclerView = game_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun setupLogGameClick() {
        logGame.setOnClickListener {
            LogGameDialogFragment().show(supportFragmentManager, "log_game_dialog")
        }
    }

    private fun insertGame(game: Game) {
        viewModel.insert(game)

        showGameAddedSnackbar()
    }

    private fun showGameAddedSnackbar() {
        Snackbar.make(main, R.string.game_logged, Snackbar.LENGTH_LONG).also {
            it.setAction(R.string.undo) {
                val lastGame = viewModel.allGames.value?.last()

                if (lastGame != null) {
                    viewModel.delete(lastGame)
                }
            }

            it.show()
        }
    }

    private fun setupFilterChips() {
        chip_group.setOnCheckedChangeListener { _, checkedId ->
            setChartFragment(checkedId)
        }
    }

    private fun setChartFragment(checkedId: Int) {
        when (checkedId) {
            R.id.chip_start_side ->
                supportFragmentManager.beginTransaction().apply {
                    setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                                        R.anim.enter_from_left, R.anim.exit_to_right)
                    replace(R.id.chart_fragment_container, PieChartFragment.newInstance())
                    commit()
                }
            R.id.chip_maps ->
                supportFragmentManager.beginTransaction().apply {
                    setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                                        R.anim.enter_from_right, R.anim.exit_to_left)
                    replace(R.id.chart_fragment_container, MapStatsFragment.newInstance())
                    commit()
                }
            R.id.chip_win_loss ->
                supportFragmentManager.beginTransaction().apply {
                    setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_left)
                    replace(R.id.chart_fragment_container, WinLossFragment.newInstance())
                    commit()
                }
        }
    }

    private fun writeCSVFile() {
        Toast.makeText(this, "Not Implemented yet, try again later!", Toast.LENGTH_LONG)
            .show()
    }

    private fun confirmAndDeleteAllGameData() {
        val builder = MaterialAlertDialogBuilder(this)

        builder.setTitle(R.string.delete_all_games)
        builder.setMessage(R.string.confirm_delete_all_games)
        builder.setPositiveButton("DELETE EVERYTHING") { dialog, which ->
            viewModel.deleteAll()
        }

        builder.setNegativeButton("CANCEL") { dialog, which ->
            dialog.dismiss()
        }

        builder.create().show()
    }
}
