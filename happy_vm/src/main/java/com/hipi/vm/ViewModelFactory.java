package com.hipi.vm;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    private Application application;
    private Bundle data = null;

    public ViewModelFactory(@NonNull Application application, @Nullable Bundle data) {
        super(application);
        this.application = application;
        this.data = data;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            Constructor<T> constructor1 = modelClass.getConstructor(Application.class, Bundle.class);
            if (constructor1 == null) {
                Constructor<T> constructor2 = modelClass.getConstructor(Application.class);
                return constructor2.newInstance(application);
            } else {
                return constructor1.newInstance(application, data);
            }
        } catch (NoSuchMethodException e) {
            return super.create(modelClass);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }
}
