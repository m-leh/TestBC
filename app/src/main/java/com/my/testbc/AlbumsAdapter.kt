package com.my.testbc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.my.domain.Album

class AlbumsAdapter(
    private val mContext: Context,
    private var mAlbums: List<Album>
) : RecyclerView.Adapter<AlbumsAdapter.AlbumHolder>() {

    class AlbumHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView? = null
        var imageView: ImageView? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_album, parent, false)
        val holder = AlbumHolder(view)
        holder.titleTextView = view.findViewById(R.id.tv_title_album)
        holder.imageView = view.findViewById(R.id.iv_album)
        return holder
    }

    override fun getItemCount() = mAlbums.size

    override fun onBindViewHolder(holder: AlbumHolder, position: Int) {
        val album = mAlbums[position]
        holder.titleTextView?.text = album.title
        val url = GlideUrl(
            album.url, LazyHeaders.Builder()
                .addHeader("User-Agent", "your-user-agent")
                .build()
        )

        Glide.with(mContext).load(url).error(R.mipmap.ic_launcher).into(holder.imageView!!)
    }

}