package com.liordahan.justeattakeaway.extensions

import androidx.core.widget.ContentLoadingProgressBar

fun ContentLoadingProgressBar.handleProgressBar(inProgress: Boolean) {
    if (inProgress) {
        show()
    } else {
        hide()
    }
}