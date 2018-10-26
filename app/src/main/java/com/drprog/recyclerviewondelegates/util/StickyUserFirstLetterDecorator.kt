package com.drprog.recyclerviewondelegates.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.widget.TextView
import com.drprog.recyclerviewondelegates.R
import com.drprog.recyclerviewondelegates.model.User
import java.util.*

/**
 * Created by roman.donchenko on 23.06.2016.
 */
class StickyUserFirstLetterDecorator(private val mStickyAreaWidth: Int) : RecyclerView.ItemDecoration() {

    private val offsetIndex = SparseBooleanArray()
    private val areaIndex = ArrayList<StickyArea>()
    private var viewHolders: HashMap<Char, StickyViewHolder> = hashMapOf()

    private class StickyArea(val letter: Char, val startPos: Int) {
        var endPos = startPos
    }

    // Call it always
    fun notifyDataSetChanged(dataSet: List<*>?) {
        offsetIndex.clear()
        areaIndex.clear()
        if (dataSet == null) return
        var stickyArea: StickyArea? = null
        var letter: Char
        for (i in dataSet.indices) {
            val item = dataSet[i]
            if (item is User) {
                offsetIndex.put(i, true)
                letter = Character.toUpperCase(item.name[0])
                if (stickyArea == null || stickyArea.letter != letter) {
                    stickyArea?.endPos = i - 1
                    stickyArea = StickyArea(letter, i)
                    areaIndex.add(stickyArea)
                }
            } else {
                stickyArea = null
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val context = parent.context
        var j = 0
        while (j < parent.childCount) {
            val child = parent.getChildAt(j)
            if (child.alpha == 0f) break // not visible yet
            val adapterPosition = parent.getChildAdapterPosition(child)
            val stickyArea = findStickyAreaForPosition(adapterPosition)
            if (stickyArea != null) {
                val topOffset = Math.max(0, child.top + child.translationY.toInt())
                val endPosDiff = stickyArea.endPos - adapterPosition
                j += endPosDiff
                val endChildIndex = Math.min(j, parent.childCount - 1)
                val viewForEnd = parent.getChildAt(endChildIndex)
                val bottomOffset = viewForEnd.bottom + viewForEnd.translationY.toInt()
                drawStickyItem(c, context, parent, topOffset, bottomOffset, stickyArea)
            }
            j++
        }
    }

    private fun findStickyAreaForPosition(adapterPosition: Int): StickyArea? {
        return areaIndex.firstOrNull{ it.startPos <= adapterPosition && it.endPos >= adapterPosition }
    }

    private fun drawStickyItem(c: Canvas, context: Context, parent: RecyclerView, topOffset: Int, bottomOffset: Int, stickyArea: StickyArea) {
        val holder = provideHolder(context, parent, stickyArea)
        val measuredHeight = holder.measuredHeight
        val top = Math.min(bottomOffset - measuredHeight, topOffset).toFloat()
        c.save()
        c.translate(0f, top)
        holder.draw(c)
        c.restore()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (offsetIndex.get(parent.getChildAdapterPosition(view))) {
            outRect.set(mStickyAreaWidth, 0, 0, 0)
        } else {
            outRect.set(0, 0, 0, 0)
        }
    }

    private fun provideHolder(context: Context, parent: RecyclerView, stickyArea: StickyArea): StickyViewHolder {
        var holder = viewHolders[stickyArea.letter]
        if (holder == null) {
            holder = StickyViewHolder(context, parent, mStickyAreaWidth, stickyArea.letter)
            viewHolders[stickyArea.letter] = holder
        }
        return holder
    }

    private class StickyViewHolder(context: Context, parent: ViewGroup, areaWidth: Int, letter: Char) {
        internal var rootView: View
        internal var textView: TextView
        var measuredHeight: Int = 0

        init {
            rootView = LayoutInflater.from(context).inflate(R.layout.sticky_item_user_first_letter, parent, false)
            textView = rootView.findViewById(R.id.textView)
            textView.text = letter.toString()
            measureAndLayout(areaWidth)
        }

        private fun measureAndLayout(areaWidth: Int) {
            val widthSpec = MeasureSpec.makeMeasureSpec(areaWidth, MeasureSpec.EXACTLY)
            val heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            rootView.measure(widthSpec, heightSpec)
            measuredHeight = rootView.measuredHeight
            rootView.layout(0, 0, rootView.measuredWidth, measuredHeight)
        }

        fun draw(c: Canvas) {
            rootView.draw(c)
        }
    }
}
