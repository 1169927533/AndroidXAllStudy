package com.example.a11699.lib_network.vm

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.KClass

/**
 *Create time 2020/7/22
 *Create Yu
 *description:
 */
inline fun <reified VM : BaseViewModel> FragmentActivity.lazyVm(noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        ActivityVmFac(application, intent.extras, this);
    }
    val vm = ViewModelLazy(VM::class, { viewModelStore }, factoryPromise)
    return vm
}

class ViewModelLazy<VM : ViewModel>(
        private val viewModelClass: KClass<VM>,
        private val viewModelStoreOwner: ViewModelStoreOwner
) : Lazy<VM> {
    private var cached: VM? = null

    override val value: VM
        get() {
            val viewModel = cached
            return if (viewModel == null) {
                ViewModelProvider(viewModelStoreOwner).get(viewModelClass.java).also {
                    cached = it
                }
            } else {
                viewModel
            }
        }

    override fun isInitialized() = cached != null
}


class ActivityVmFac(private val application: Application, private val bundle: Bundle?, private val act: FragmentActivity) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return try {
            val constructor1 = modelClass.getConstructor(
                    Application::class.java,
                    Bundle::class.java
            )
            val vm: T =
                    if (constructor1 == null) {
                        val constructor2 =
                                modelClass.getConstructor(Application::class.java)
                        constructor2.newInstance(application)
                    } else {
                        constructor1.newInstance(application, bundle)
                    }

            vm

        } catch (e: NoSuchMethodException) {
            super.create(modelClass)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: InstantiationException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        }
    }

    class Dsa(dds: String) {

    }

}

