package io.finefabric.ninebanana.base

/**
 * Created by laszlo on 2017-09-01.
 */
abstract class BasePresenter<in V : BaseView> {

    private var view: V? = null

    protected abstract fun onViewAttached()

    protected abstract fun onViewDetached()

    fun attachView(view: V) {
        this.view = view
        onViewAttached()
    }

    fun detachView() {
        this.view = null
        onViewDetached()
    }

}

