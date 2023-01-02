package com.liordahan.justeattakeaway.extensions


/* I'm using this extension in order to update a certain
value in the list item with pre conditioned boolean term.
In my opinion it's more of a clean way for update boolean states for example without giving
the list adapter a way to change my object value*/

fun <T> List<T>.updateListItem(where: ((T) -> Boolean), transformation: ((T) -> T)): List<T> {
    return this.map {
        if (where(it)) {
            transformation.invoke(it)
        } else {
            it
        }
    }
}