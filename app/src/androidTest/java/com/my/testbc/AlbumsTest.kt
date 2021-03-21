package com.my.testbc

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.my.domain.Album
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Update via remote update
 */
@RunWith(AndroidJUnit4::class)
class AlbumsTest {

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun before() {
        activityRule.launchActivity(Intent())
    }

    @Test
    fun testList() {
        val titleAlbum1 = "Album 1"
        val titleAlbum2 = "Album 2"
        val albums = listOf(
            Album(
                albumId = 1,
                id = 1,
                title = titleAlbum1,
                url = "https://via.placeholder.com/600/92c952"
            )
            ,
            Album(
                albumId = 1,
                id = 2,
                title = titleAlbum2,
                url = "https://via.placeholder.com/600/771796"
            )
            ,
            Album(
                albumId = 1,
                id = 3,
                title = "Album 3",
                url = "https://via.placeholder.com/600/771796"
            )
        )
        activityRule.activity.mViewModel.repository.onAlbumsChange(albums)
        onView(withId(R.id.rv_albums)).check(matches(hasDescendant(withText(titleAlbum1))))
        val recyclerView =
            activityRule.activity.findViewById(R.id.rv_albums) as RecyclerView
        assertNotNull(recyclerView.adapter)
        assertTrue(recyclerView.adapter?.itemCount == 3)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        assertEquals("com.my.testbc", appContext.packageName)
    }

}
