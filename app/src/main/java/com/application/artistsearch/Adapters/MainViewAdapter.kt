package com.application.artistsearch.Adapters

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.application.artistsearch.MainActivity
import com.application.artistsearch.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import org.jetbrains.annotations.Nullable


class MainViewAdapter(mainActivity: MainActivity) : RecyclerView.Adapter<MainViewAdapter.MainViewHolder>(){

    private var data : ArrayList<com.example.sanathitunes.models.Result>?=null
    fun setData(list: ArrayList<com.example.sanathitunes.models.Result>){
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.home_rv_item_view,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item)
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val song = itemView.findViewById(R.id.song) as TextView
        private val artist = itemView.findViewById(R.id.artist) as TextView
        private val image = itemView.findViewById(R.id.image) as ImageView
        private val progressBar = itemView.findViewById(R.id.progressBar) as ProgressBar

        fun bindView(item: com.example.sanathitunes.models.Result?) {

            song.text = item?.trackName
            artist.text = item?.artistName
            Glide.with(itemView.context).load(item?.artworkUrl100)
                .addListener(object : RequestListener<Drawable?> {
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false;
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                }).into(image)
//            itemView.tv_home_item_title.text = item?.title
//            itemView.tv_home_item_body.text = item?.body
        }

    }
}