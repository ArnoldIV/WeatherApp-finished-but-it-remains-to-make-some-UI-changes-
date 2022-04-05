package com.arnold.weatherapp.view.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.arnold.weatherapp.R
import com.arnold.weatherapp.business.model.GeoCodeModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import java.util.*

class CityListAdapter : BaseAdapter<GeoCodeModel>() {

    lateinit var clickListener: SearchItemClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitySearchViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_city_list, parent, false)
        return CitySearchViewHolder(view)
    }

    interface SearchItemClickListener {

        fun addToFavorite(item: GeoCodeModel)

        fun removeFromFavorite(item: GeoCodeModel)

        fun showWeatherIn(item: GeoCodeModel)

    }

    @SuppressLint("NonConstantResourceId")
    inner class CitySearchViewHolder(view: View) : BaseViewHolder(view) {

        @BindView(R.id.search_city)
        lateinit var mCity: MaterialTextView

        @BindView(R.id.search_country)
        lateinit var mCountry: MaterialTextView

        @BindView(R.id.favorite)
        lateinit var mFavourite: MaterialButton

        @BindView(R.id.location)
        lateinit var mLocation: MaterialCardView

        @BindView(R.id.state)
        lateinit var mState: MaterialTextView

        init {
            ButterKnife.bind(this, view)
        }

        override fun bindView(position: Int) {
            mLocation.setOnClickListener {
                clickListener.showWeatherIn(mData[position])
            }

            mFavourite.setOnClickListener {
                val item = mData[position]
                when ((it as MaterialButton).isChecked) {
                    true -> {
                        item.isFavourite = true
                        clickListener.addToFavorite(item)
                    }
                    false -> {
                        item.isFavourite = false
                        clickListener.removeFromFavorite(item)
                    }
                }

            }

            mData[position].apply {
                mState.text = if (!state.isNullOrEmpty()) itemView.context.getString(
                    R.string.comma,
                    state
                ) else ""
                mCity.text = when (Locale.getDefault().displayLanguage){
                    "English" -> local_names.en?: name
                    "русский" -> local_names.ru?: name
                    else -> name
                }
                mCountry.text = country
                mFavourite.isChecked = isFavourite
                Log.d("ascii", "" + local_names.ascii)
            }
        }

    }
}