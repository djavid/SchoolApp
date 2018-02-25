package com.djavid.schoolapp.viewmodel.group_details;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.djavid.schoolapp.model.users.User;

/**
 * Created by Andrei on 25-Feb-18.
 */

public class UserItem extends BaseObservable {
    public final User user;

    public UserItem() {
        this(new User());
    }

    @Bindable
    public String getNick() {
        return user.username;
    }

    public UserItem(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserItem that = (UserItem) o;

        return user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }
}
