package com.tiknil.app.core.extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.compositeDisposable(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}