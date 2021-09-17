package com.dscunikom.android.footballmatchschedule.activity.teamdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.dscunikom.android.footballmatchschedule.R
import com.dscunikom.android.footballmatchschedule.model.Player
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class PlayerDetailActivity : AppCompatActivity() {

    companion object {
        const val INTENT_DETAIL = "EXTRA_DETAIL"
    }

    private lateinit var player: Player
    private lateinit var tvHeight: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvWeight: TextView
    private lateinit var tvPosition: TextView
    private lateinit var ivFanart: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scrollView {
            linearLayout {
                orientation = LinearLayout.VERTICAL

                ivFanart = imageView {
                    id = Ids.ivFanart
                    scaleType = ImageView.ScaleType.CENTER
                }.lparams(width = matchParent, height = dip(240))
                linearLayout {
                    orientation = LinearLayout.HORIZONTAL

                    textView("Weight (Kg)") {
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }.lparams(width = dip(0), height = wrapContent) {
                        weight = 1f
                    }
                    textView("Height (m)") {
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }.lparams(width = dip(0), height = wrapContent) {
                        weight = 1f
                    }
                }.lparams(width = matchParent, height = wrapContent) {
                    topMargin = dip(8)
                }
                linearLayout {
                    orientation = LinearLayout.HORIZONTAL

                    tvWeight = textView {
                        id = Ids.tvWeight
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        textColor = R.color.colorPrimary
                    }.lparams(width = dip(0), height = wrapContent) {
                        weight = 1f
                    }
                    tvHeight = textView {
                        id = Ids.tvHeight
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        textColor = R.color.colorPrimary
                    }.lparams(width = dip(0), height = wrapContent) {
                        weight = 1f
                    }
                }.lparams(width = matchParent, height = wrapContent)
                tvPosition = textView {
                    id = Ids.tvPosition
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    textColor = R.color.colorPrimary
                }.lparams(width = matchParent, height = wrapContent) {
                    topMargin = dip(8)
                }
                view {
                    backgroundResource = android.R.color.darker_gray
                }.lparams(width = matchParent, height = dip(1)) {
                    topMargin = dip(8)
                }
                tvDescription = textView {
                    id = Ids.tvDescription
                }.lparams(width = matchParent, height = wrapContent)
            }.lparams(width = matchParent, height = wrapContent)
        }
        player = intent.getParcelableExtra(INTENT_DETAIL)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = player.strPlayer

        initData()
    }

    fun initData() {
        if(player.strFanart1.isNullOrEmpty()) {
            ivFanart.setImageResource(R.drawable.no_image)
        } else {
            Picasso.get().load(player.strFanart1).into(ivFanart)
        }
        tvWeight.text = player.strWeight
        tvHeight.text = player.strHeight
        tvPosition.text = player.strPosition
        tvDescription.text = player.strDescriptionEN
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private object Ids {
        val ivFanart = 1
        val tvDescription = 2
        val tvHeight = 3
        val tvPosition = 4
        val tvWeight = 5
    }
}
