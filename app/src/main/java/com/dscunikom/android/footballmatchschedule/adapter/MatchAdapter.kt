package com.dscunikom.android.footballmatchschedule.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.dscunikom.android.footballmatchschedule.R
import com.dscunikom.android.footballmatchschedule.utils.Date
import com.dscunikom.android.footballmatchschedule.model.Match
import org.jetbrains.anko.*

class MatchAdapter(private val items: List<Match>, private val listener: (Match) -> Unit) : RecyclerView.Adapter
<MatchAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            MatchUI().createView(
                AnkoContext.create(parent.context, parent)
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvMatchDate: TextView = view.findViewById(ID_DATE)
        private val tvHomeClub: TextView = view.findViewById(ID_HOME_TEAM)
        private val tvHomeScore: TextView = view.findViewById(ID_HOME_SCORE)
        private val tvAayClub: TextView = view.findViewById(ID_AWAY_TEAM)
        private val tvAwayScore: TextView = view.findViewById(ID_AWAY_SCORE)

        fun bindItem(items: Match, listener: (Match) -> Unit) {
            tvMatchDate.text = Date.getLongDate(items.dateEvent)
            tvHomeClub.text = items.strHomeTeam
            tvHomeScore.text = items.intHomeScore
            tvAayClub.text = items.strAwayTeam
            tvAwayScore.text = items.intAwayScore

            itemView.setOnClickListener { listener(items) }
        }

    }

    companion object {
        const val ID_DATE = 1
        const val ID_HOME_TEAM = 2
        const val ID_HOME_SCORE = 3
        const val ID_AWAY_TEAM = 4
        const val ID_AWAY_SCORE = 5
    }

    class MatchUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams(matchParent, wrapContent)
                orientation = LinearLayout.VERTICAL

                linearLayout {
                    backgroundColor = Color.WHITE
                    orientation = LinearLayout.VERTICAL
                    padding = dip(8)

                    textView {
                        id = ID_DATE
                        textColor = ContextCompat.getColor(
                            ctx,
                            R.color.colorPrimary
                        )
                        gravity = Gravity.CENTER
                    }.lparams(matchParent, wrapContent)

                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        textView {
                            id = ID_HOME_TEAM
                            gravity = Gravity.CENTER
                            textSize = 18f
                            text = "home"
                        }.lparams(matchParent, wrapContent, 1f)

                        linearLayout {
                            gravity = Gravity.CENTER_VERTICAL

                            textView {
                                id =
                                        ID_HOME_SCORE
                                padding = dip(8)
                                textSize = 20f
                                setTypeface(null, Typeface.BOLD)
                                text = "0"
                            }

                            textView {
                                text = "vs"
                            }

                            textView {
                                id =
                                        ID_AWAY_SCORE
                                padding = dip(8)
                                textSize = 20f
                                setTypeface(null, Typeface.BOLD)
                                text = "0"
                            }
                        }

                        textView {
                            id = ID_AWAY_TEAM
                            gravity = Gravity.CENTER
                            textSize = 18f
                            text = "away"
                        }.lparams(matchParent, wrapContent, 1f)
                    }
                }.lparams(matchParent, matchParent) {
                    setMargins(dip(16), dip(8), dip(16), dip(8))
                }
            }
        }

    }

}

