package com.drprog.recyclerviewondelegates.util

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * ItemDecoration for spacing (divider) between items in RecyclerView
 */
class DividerItemDecoration
/**
 * Creates ItemDecoration for colored spacing (divider) between items in RecyclerView
 *
 * @param spacing       the spacing size in pixels
 * @param spacingConfig the spacing config. Can be set as bit mask like `SPACE_LEFT|SPACE_TOP`.
 * Available values:
 * [DividerItemDecoration.SPACE_LEFT], [DividerItemDecoration.SPACE_TOP],
 * [DividerItemDecoration.SPACE_RIGHT], [DividerItemDecoration.SPACE_BOTTOM].
 * @param disableFirst  true for not drawing divider for first item.
 * @param disableLast   true for not drawing divider for last item.
 */
@JvmOverloads constructor(protected var mSpacing: Int,
                          protected val mSpacingConfig: Int = SPACE_TOP, private val mDisableFirst: Boolean = false, private val mDisableLast: Boolean = false) : RecyclerView.ItemDecoration() {
    protected val mDrawLeft: Boolean
    protected val mDrawTop: Boolean
    protected val mDrawRight: Boolean
    protected val mDrawBottom: Boolean

    init {

        mDrawLeft = check(SPACE_LEFT)
        mDrawTop = check(SPACE_TOP)
        mDrawRight = check(SPACE_RIGHT)
        mDrawBottom = check(SPACE_BOTTOM)
    }

    override fun getItemOffsets(outRect: Rect, child: View, parent: RecyclerView, state: RecyclerView.State) {
        if (mDrawLeft && needDrawIfFirst(child, parent, SPACE_LEFT)) outRect.left = mSpacing
        if (mDrawTop && needDrawIfFirst(child, parent, SPACE_TOP)) outRect.top = mSpacing
        if (mDrawRight && needDrawIfLast(child, parent, SPACE_RIGHT)) outRect.right = mSpacing
        if (mDrawBottom && needDrawIfLast(child, parent, SPACE_BOTTOM)) outRect.bottom = mSpacing
    }

    protected fun check(value: Int): Boolean {
        return mSpacingConfig and value == value
    }

    protected fun isFirstItem(child: View, parent: RecyclerView, spacingConfig: Int): Boolean {
        return parent.getChildAdapterPosition(child) == 0
    }

    protected fun isLastItem(child: View, parent: RecyclerView): Boolean {
        val adapter = parent.adapter
        return adapter != null && parent.getChildAdapterPosition(child) == adapter.itemCount - 1
    }

    protected fun needDrawIfFirst(child: View, parent: RecyclerView, spacingConfig: Int): Boolean {
        return !(mDisableFirst && isFirstItem(child, parent, spacingConfig))
    }

    protected fun needDrawIfLast(child: View, parent: RecyclerView, spacingConfig: Int): Boolean {
        return !(mDisableLast && isLastItem(child, parent))
    }

    companion object {

        val SPACE_LEFT = 1
        val SPACE_RIGHT = 2
        val SPACE_TOP = 4
        val SPACE_BOTTOM = 8
    }

}
/**
 * Creates ItemDecoration for colored spacing (divider) between items in RecyclerView
 * Make spacing on top of each item
 *
 * @param spacing the spacing size in pixels
 */
/**
 * Creates ItemDecoration for colored spacing (divider) between items in RecyclerView
 *
 * @param spacing       the spacing size in pixels
 * @param spacingConfig the spacing config. Can be set as bit mask like `SPACE_LEFT|SPACE_TOP`.
 * Available values:
 * [DividerItemDecoration.SPACE_LEFT], [DividerItemDecoration.SPACE_TOP],
 * [DividerItemDecoration.SPACE_RIGHT], [DividerItemDecoration.SPACE_BOTTOM].
 */
