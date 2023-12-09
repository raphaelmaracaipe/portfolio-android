package br.com.raphaelmaracaipe.uiprofile.ui

import android.content.Context
import android.os.Build
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.raphaelmaracaipe.uiprofile.TestApplication
import br.com.raphaelmaracaipe.uiprofile.ui.ProfileImageOptionsBottomSheetAdapter.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import br.com.raphaelmaracaipe.uiprofile.R
import org.junit.Assert

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M], application = TestApplication::class)
class ProfileImageOptionsBottomSheetAdapterTest {

    private val mContext: Context = RuntimeEnvironment.getApplication().applicationContext

    @Test
    fun `when init recycler and click in the item`() {
        val profileAdapter = ProfileImageOptionsBottomSheetAdapter(
            mContext,
            arrayOf("a", "b")
        ) {
            Assert.assertEquals(0, it)
        }

        val myRecyclerView = RecyclerView(mContext).apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = profileAdapter
        }
        myRecyclerView.measure(
            View.MeasureSpec.UNSPECIFIED,
            View.MeasureSpec.UNSPECIFIED
        )
        myRecyclerView.layout(0, 0, 1000, 1000)

        val viewHolder = myRecyclerView.findViewHolderForAdapterPosition(0)
        val itemViewHolder = (viewHolder as ViewHolder).itemView.findViewById<ConstraintLayout>(
            R.id.cltContainer
        )
        itemViewHolder.performClick()
    }

}