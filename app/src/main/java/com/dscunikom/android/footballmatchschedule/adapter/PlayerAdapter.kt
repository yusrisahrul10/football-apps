package com.dscunikom.android.footballmatchschedule.adapter

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.dscunikom.android.footballmatchschedule.R
import com.dscunikom.android.footballmatchschedule.model.Player
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class PlayerAdapter(private val player: MutableList<Player>, private val listener: (Player) -> Unit) :
    RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(PlayerAdapter.PlayerUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount() = player.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(player[position], listener)
    }

    companion object {
        private const val player_badge = 1
        private const val player_name = 2
        private const val player_position = 3
    }

    class PlayerUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                linearLayout {
                    lparams(matchParent, wrapContent)
                    orientation = LinearLayout.VERTICAL

                    imageView {
                        id = player_badge
                    }.lparams(dip(120), dip(120))
                    textView {
                        id = player_name
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams(wrapContent, wrapContent) {
                        topPadding = dip(8)
                    }
                    textView {
                        id = player_position
                    }.lparams(wrapContent, wrapContent) {
                        topPadding = dip(16)
                    }
                }
            }
        }

    }

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val playerBadge: ImageView = view.find(player_badge)
        private val playerName: TextView = view.find(player_name)
        private val playerPosition: TextView = view.find(player_position)

        fun bindItem(player: Player, listener: (Player) -> Unit) {
            if(player.strCutout != null) {
                Picasso.get().load(player.strCutout).into(playerBadge)
            } else {
                playerBadge.setImageResource(R.drawable.no_image)
            }
             playerName.text = player.strPlayer
            playerPosition.text = player.strPosition
            itemView.onClick { listener(player) }
        }
    }

}