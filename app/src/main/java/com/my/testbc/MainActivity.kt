package com.my.testbc

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.my.domain.Album
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    companion object {
        const val RECYCLER_VIEW_POSITION = "RECYCLER_VIEW_POSITION"
        const val ALBUM_ID_POSITION = "ALBUM_ID_POSITION"
        const val ALL_ALBUMS_POSITION = 0
    }

    val mViewModel: AlbumsViewModel by viewModel()

    private lateinit var mAlbumsRecyclerView: RecyclerView

    private lateinit var mAlbumIdsSpinner: Spinner

    private var mSpinnerPosition = 0

    private var mPositionInRecyclerView = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAlbumsRecyclerView = findViewById(R.id.rv_albums)

        mAlbumIdsSpinner = findViewById(R.id.spinner_ids)
        mAlbumIdsSpinner.onItemSelectedListener = this
        if (savedInstanceState != null) {
            mSpinnerPosition = savedInstanceState.getInt(ALBUM_ID_POSITION)
            mPositionInRecyclerView = savedInstanceState.getInt(RECYCLER_VIEW_POSITION)
        }

        mViewModel.open()
        mViewModel.loadAlbumIds()?.observe(this,
            Observer {
                val list = mutableListOf<String>()
                list.add("--")
                for (id: Long in it) {
                    list.add("$id")
                }
                val adapter =
                    ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
                mAlbumIdsSpinner.adapter = adapter
                mAlbumIdsSpinner.setSelection(mSpinnerPosition)
            })
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the current recyclerview position
        val completelyVisibleItemPosition =
            (mAlbumsRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        outState.putInt(RECYCLER_VIEW_POSITION, completelyVisibleItemPosition)
        outState.putInt(ALBUM_ID_POSITION, mSpinnerPosition)
    }


    private fun updateAlbums(albums: List<Album>?) {
        if (albums != null) {
            if (mAlbumsRecyclerView.adapter == null) {
                val diffCallBack = object : ItemCallback<Album>() {
                    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
                        return oldItem.id == newItem.id
                    }

                    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
                        return oldItem == newItem
                    }
                }
                val adapter = AlbumsAdapter(diffCallBack)
                mAlbumsRecyclerView.adapter = adapter
            }
            (mAlbumsRecyclerView.adapter as AlbumsAdapter).submitList(albums)
            if (mPositionInRecyclerView != -1 && mPositionInRecyclerView < albums.size) {
                mAlbumsRecyclerView.scrollToPosition(mPositionInRecyclerView)
            }
        }
    }

    private fun loadAllAlbums() {
        mViewModel.loadAlbums()?.observe(this,
            Observer {
                updateAlbums(it)
            })
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        loadAllAlbums()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mSpinnerPosition = position
        if (position == ALL_ALBUMS_POSITION) {
            loadAllAlbums()
        } else {
            mViewModel.loadAlbums(position.toLong())?.observe(this,
                Observer {
                    updateAlbums(it)
                })
        }
    }
}
