package com.djavid.schoolapp.viewmodel.event_details;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.djavid.schoolapp.BR;
import com.djavid.schoolapp.model.event_comments.EventComment;
import com.djavid.schoolapp.rest.Api;

import java.util.Calendar;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Andrei on 25-Feb-18.
 */

public class EventCommentItem extends BaseObservable {
    public final EventComment comment;
    public final boolean _isMy;

    public EventCommentItem() {
        this(new EventComment());
    }

    public EventCommentItem(EventComment comment) {
        this(comment, false);
    }

    public EventCommentItem(EventComment comment, boolean isMy) {
        this.comment = comment;
        this._isMy = isMy;
    }

    @Bindable
    public boolean isMy() {
        return _isMy;
    }

    @Bindable
    public int getMyCommentVisibility() {
        return isMy()
                ? VISIBLE
                : GONE;
    }

    @Bindable
    public int getOtherCommentVisibility() {
        return isMy()
                ? GONE
                : VISIBLE;
    }

    @Bindable
    public String getTimeStamp() {
        return Api.LocalizedDateTime(getTimeStampCalendar());
    }

    public Calendar getTimeStampCalendar() {
        return comment.getTimeStamp();
    }

    @Bindable
    public String getText() {
        return comment.text;
    }

    public void setText(String text) {
        comment.text = text;
        notifyPropertyChanged(BR.text);
    }

    @Bindable
    public String getAuthor() {
        return comment.author;
    }

    public void setTimeStamp(String timeStamp) {
        comment.timeStamp = timeStamp;
        notifyPropertyChanged(BR.timeStamp);
    }
}
