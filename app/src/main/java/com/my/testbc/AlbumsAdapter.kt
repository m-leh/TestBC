package com.my.testbc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.my.domain.Album

class AlbumsAdapter(diffCallback: DiffUtil.ItemCallback<Album>) :
    ListAdapter<Album, AlbumsAdapter.AlbumHolder>(diffCallback) {

    class AlbumHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(album: Album) {
            with(itemView) {
                findViewById<TextView>(R.id.tv_title_album).text = album.title
                val url = GlideUrl(
                    album.url, LazyHeaders.Builder()
                        .build()
                )

                Glide.with(context).load(url).error(R.mipmap.ic_launcher)
                    .into(findViewById(R.id.iv_album)!!)
            }
        }

        companion object {
            fun from(parent: ViewGroup): AlbumHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_album, parent, false)
                return AlbumHolder(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumHolder {
        return AlbumHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AlbumHolder, position: Int) {
        holder.bind(getItem(position))
    }

}