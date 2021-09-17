package com.dscunikom.android.footballmatchschedule.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.dscunikom.android.footballmatchschedule.R
import com.dscunikom.android.footballmatchschedule.model.Team
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class TeamAdapter(private val teams: List<Team>, private val listener: (Team) -> Unit) :
    RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    companion object {
        private const val team_badge = 1
        private const val team_name = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    class TeamUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(16)
                    orientation = LinearLayout.HORIZONTAL

                    imageView {
                        id = team_badge
                    }.lparams {
                        height = dip(50)
                        width = dip(50)
                    }

                    textView {
                        id = team_name
                        textSize = 16f
                    }.lparams {
                        margin = dip(15)
                    }

                }
            }

        }
    }

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val teamBadge: ImageView = view.find(team_badge)
        private val teamName: TextView = view.find(team_name)

        fun bindItem(teams: Team, listener: (Team) -> Unit) {
            if(teams.strTeamBadge.isNullOrEmpty()) {
                teamBadge.setImageResource(R.drawable.no_image)
            } else {
                Picasso.get().load(teams.strTeamBadge).into(teamBadge)
            }

            teamName.text = teams.strTeam
            itemView.onClick { listener(teams) }
        }
    }

}

