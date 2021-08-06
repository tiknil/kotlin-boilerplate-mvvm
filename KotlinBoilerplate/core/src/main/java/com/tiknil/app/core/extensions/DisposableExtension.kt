package com.tiknil.app.core.extensions

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.compositeDisposable(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}