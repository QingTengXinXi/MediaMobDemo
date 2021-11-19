package com.media.mob.demo.widget

interface ItemTouchHelperInterface {

    fun itemMove(fromPosition: Int, position: Int)

    fun itemDelete(position: Int)
}