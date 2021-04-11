package com.github.bassaer.example.matcher

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView

import androidx.core.content.res.ResourcesCompat

import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher


/**
 * Custom matcher to test drawable resource
 * Created by nakayama on 2017/07/29.
 */
class DrawableMatcher(private val mExpectedId: Int) : TypeSafeMatcher<View>(View::class.java) {
    private var mResourceName: String? = null

    override fun matchesSafely(target: View): Boolean {
        if (target !is ImageView) {
            return false
        }
        if (mExpectedId < 0) {
            return target.drawable == null
        }
        val resources = target.getContext().resources
        val expectedDrawable = ResourcesCompat.getDrawable(resources, mExpectedId, null)
        mResourceName = resources.getResourceEntryName(mExpectedId)
        if (expectedDrawable == null) {
            return false
        }
        val bitmap = getBitmap(target.drawable)
        val otherBitmap = getBitmap(expectedDrawable)

        return bitmap.sameAs(otherBitmap)
    }

    private fun getBitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    override fun describeTo(description: Description) {
        description.appendText("with drawable from resource id: ")
        description.appendValue(mExpectedId)
        if (mResourceName != null) {
            description.appendText("[")
            description.appendText(mResourceName)
            description.appendText("]")
        }
    }
}
