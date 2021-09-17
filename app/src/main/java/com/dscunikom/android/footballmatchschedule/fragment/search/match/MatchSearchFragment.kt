package com.dscunikom.android.footballmatchschedule.fragment.search.match

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.SearchView
import com.dscunikom.android.footballmatchschedule.R
import com.dscunikom.android.footballmatchschedule.activity.matchdetail.MatchDetailActivity
import com.dscunikom.android.footballmatchschedule.adapter.MatchAdapter
import com.dscunikom.android.footballmatchschedule.api.ApiRepository
import com.dscunikom.android.footballmatchschedule.model.Match
import com.dscunikom.android.footballmatchschedule.utils.invisible
import com.dscunikom.android.footballmatchschedule.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_search_match.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.startActivity

class MatchSearchFragment : Fragment(), MatchSearchView {

    private lateinit var presenter: MatchSearchPresenter
    private var match: MutableList<Match> = mutableListOf()
    private lateinit var matchAdapter: MatchAdapter
    private lateinit var listMatch: RecyclerView

    override fun showLoading() {
        pbMatchSearch?.visible()
    }

    override fun hideLoading() {
        pbMatchSearch?.invisible()
    }

    override fun showMatchList(data: MutableList<Match>) {
        match.clear()
        match.addAll(data)
        matchAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_search_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar_match)
        }
        presenter = MatchSearchPresenter(
            this,
            ApiRepository(),
            Gson()
        )
        matchAdapter = MatchAdapter(match) {
            startActivity<MatchDetailActivity>(MatchDetailActivity.INTENT_DETAIL to it)
        }
        listMatch = find(R.id.rvMatch)
        listMatch.layoutManager = LinearLayoutManager(ctx)
        listMatch.adapter = matchAdapter

        presenter.getMatchSearch()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search_view_menu, menu)

        val searchView = menu?.findItem(R.id.mnSearchView)
        val searchMatch = searchView?.actionView as SearchView

        searchMatch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                presenter.getMatchSearch(query.toString())
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.getMatchSearch(query.toString())
                return true
            }
        })
    }
}