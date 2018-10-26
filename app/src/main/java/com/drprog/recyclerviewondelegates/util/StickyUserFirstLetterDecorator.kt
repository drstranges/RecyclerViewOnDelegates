package com.drprog.recyclerviewondelegates.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.TextView

import com.drprog.recyclerviewondelegates.R
import com.drprog.recyclerviewondelegates.model.User

import java.util.ArrayList

/**
 * Created by roman.donchenko on 23.06.2016.
 */
class StickyUserFirstLetterDecorator(private val mLeftOffset: Int, dividerSize: Int, dividerColor: Int, private val mDrawTopDividerWide: Boolean) : RecyclerView.ItemDecoration() {

    private val mOffsetIndex = SparseBooleanArray()
    private val mIndex = ArrayList<StickyArea>()
    private var mStickyViewHolder: StickyViewHolder? = null
    private val mPaint: Paint = Paint()

    private class StickyArea {
        internal var letter: Char = 0.toChar()
        internal var startPos = 0
        internal var endPos = 0
    }

    init {
        mPaint.color = dividerColor
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = dividerSize.toFloat()
    }

    // Call it always
    fun notifyDataSetChanged(dataSet: List<*>?) {
        mOffsetIndex.clear()
        mIndex.clear()
        if (dataSet == null) {
            return
        }
        var stickyArea = StickyArea()
        var letter: Char
        for (i in dataSet.indices) {
            val item = dataSet[i]
            if (item is User) {
                mOffsetIndex.put(i, true)
                val (name) = item

                letter = if (TextUtils.isEmpty(name)) ' ' else Character.toUpperCase(name[0])
                if (stickyArea.letter == letter) {
                    stickyArea.endPos = i
                } else {
                    if (stickyArea.letter.toInt() != 0) stickyArea = StickyArea()
                    stickyArea.letter = letter
                    stickyArea.startPos = i
                    stickyArea.endPos = i
                    mIndex.add(stickyArea)
                }
            } else if (stickyArea.letter.toInt() != 0) {
                stickyArea = StickyArea()
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val context = parent.context
        val layoutManager = (if (parent.layoutManager is LinearLayoutManager) parent.layoutManager as LinearLayoutManager? else null)
                ?: return
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        var topOffset: Int
        var bottomOffset: Int
        for (stickyArea in mIndex) {
            bottomOffset = -1
            topOffset = bottomOffset
            if (stickyArea.startPos <= lastVisibleItemPosition && stickyArea.endPos >= firstVisibleItemPosition) {

                for (j in 0 until parent.childCount) {
                    val child = parent.getChildAt(j)
                    val adapterPosition = parent.getChildAdapterPosition(child)
                    if (stickyArea.startPos <= adapterPosition && stickyArea.endPos >= adapterPosition) {
                        if (child.alpha == 0f) break // not visible yet
                        if (topOffset < 0) {
                            if (adapterPosition == stickyArea.startPos && child.top > 0) {
                                topOffset = child.top + child.translationY.toInt()
                            } else {
                                topOffset = child.translationY.toInt()
                            }
                        }
                        bottomOffset = child.bottom + child.translationY.toInt()
                    } else if (topOffset >= 0 && bottomOffset >= 0) {
                        break
                    }
                }
                if (topOffset < 0) topOffset = 0
                if (bottomOffset > 0) {
                    drawStickyItem(c, context, parent, topOffset, bottomOffset, stickyArea)
                }
            }
        }
    }

    private fun drawStickyItem(c: Canvas, context: Context, parent: RecyclerView, topOffset: Int, bottomOffset: Int, stickyArea: StickyArea) {
        if (mStickyViewHolder == null) {
            mStickyViewHolder = StickyViewHolder(context, parent, mLeftOffset)
        }
        mStickyViewHolder!!.prepareLetter(stickyArea.letter)
        val measuredHeight = mStickyViewHolder!!.measuredHeight
        val top: Int
        if (bottomOffset - topOffset < measuredHeight) {
            top = bottomOffset - measuredHeight
        } else {
            top = topOffset
        }
        c.save()
        c.translate(0f, top.toFloat())
        mStickyViewHolder!!.draw(c)
        if (mDrawTopDividerWide && stickyArea === mIndex[0]) {
            // first user item. draw long line
            c.drawLine(0f, 0f, (parent.width - parent.paddingRight).toFloat(), 0f, mPaint)
        } else
            c.drawLine(mLeftOffset.toFloat(), 0f, (parent.width - parent.paddingRight).toFloat(), 0f, mPaint)
        c.restore()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (mOffsetIndex.get(parent.getChildAdapterPosition(view))) {
            outRect.set(mLeftOffset, 0, 0, 0)
        } else {
            outRect.set(0, 0, 0, 0)
        }
    }

    private class StickyViewHolder(context: Context, parent: ViewGroup, private val mWidth: Int) {
        internal var rootView: View
        internal var textView: TextView
        var measuredHeight: Int = 0
        private var mLetter = '\u0001'

        init {
            rootView = LayoutInflater.from(context).inflate(R.layout.sticky_item_user_first_letter, parent, false)
            textView = rootView.findViewById(R.id.textView)
        }

        private fun measure() {
            val widthSpec = MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.EXACTLY)
            val heightSpec = LayoutParams.WRAP_CONTENT
            rootView.measure(widthSpec, heightSpec)
            measuredHeight = rootView.measuredHeight
            val measuredWidth = rootView.measuredWidth
            rootView.layout(0, 0, measuredWidth, measuredHeight)
        }

        fun prepareLetter(letter: Char) {
            if (letter == mLetter) return
            mLetter = letter
            textView.text = letter.toString()
            textView.visibility = View.VISIBLE
            measure()
        }

        fun draw(c: Canvas) {
            rootView.draw(c)
        }
    }
}
