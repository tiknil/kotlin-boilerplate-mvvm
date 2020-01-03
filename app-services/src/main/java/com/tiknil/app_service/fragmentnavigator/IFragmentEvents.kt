package com.tiknil.app_service.fragmentnavigator

interface IFragmentEvents {

    var params: Any?

    fun onViewAppear()

    fun onViewDisappear()
}