package com.djavid.schoolapp.rest;

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.model.schedule.Schedule;
import com.djavid.schoolapp.model.schedule.ScheduleLesson;
import com.djavid.schoolapp.model.schedule.ScheduleSubject;
import com.djavid.schoolapp.util.RxUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;


public class RestDataRepository {

    private Api apiInterface;


    public RestDataRepository(Api apiInterface) {
        this.apiInterface = apiInterface;
    }

    public RestDataRepository() {
        this(App.getAppInstance().getApi());
    }


    public Single<List<ScheduleSubject>> getAllSubjects() {

        String auth = App.getAppInstance().getPreferences().getToken();

        return apiInterface.getAllSubjects(auth)
                .doOnError(Throwable::printStackTrace)
                .compose(RxUtils.applySingleSchedulers())
                .retry(2L);
    }

    public Single<ScheduleSubject> createSubject(String title) {

        String auth = App.getAppInstance().getPreferences().getToken();

        return apiInterface.createSubject(auth, title)
                .doOnError(Throwable::printStackTrace)
                .compose(RxUtils.applySingleSchedulers())
                .retry(2L);
    }

    public Single<ScheduleSubject> updateSubject(long subjectId, String title) {

        String auth = App.getAppInstance().getPreferences().getToken();

        return apiInterface.updateSubject(auth, subjectId, title)
                .doOnError(Throwable::printStackTrace)
                .compose(RxUtils.applySingleSchedulers())
                .retry(2L);
    }

    public Single<Map<String, String>> deleteSubject(long subjectId) {

        String auth = App.getAppInstance().getPreferences().getToken();

        return apiInterface.deleteSubject(auth, subjectId)
                .doOnError(Throwable::printStackTrace)
                .compose(RxUtils.applySingleSchedulers())
                .retry(2L);
    }

    public Single<Schedule> getAllLessons() {

        String auth = App.getAppInstance().getPreferences().getToken();

        return apiInterface.getAllLessons(auth)
                .doOnError(Throwable::printStackTrace)
                .compose(RxUtils.applySingleSchedulers())
                .retry(2L);
    }

    public Single<ScheduleLesson> createLesson(String start_time, int weekday, int[] groups,
                                               int subject, String place) {

        String auth = App.getAppInstance().getPreferences().getToken();

        return apiInterface.createLesson(auth, start_time, weekday, groups, subject, place)
                .doOnError(Throwable::printStackTrace)
                .compose(RxUtils.applySingleSchedulers())
                .retry(2L);
    }

    public Single<ScheduleLesson> updateLesson(long lessonId,
                                               String start_time, int weekday, int[] groups,
                                               int subject, String place) {

        String auth = App.getAppInstance().getPreferences().getToken();

        return apiInterface.updateLesson(auth, lessonId, start_time, weekday, groups, subject, place)
                .doOnError(Throwable::printStackTrace)
                .compose(RxUtils.applySingleSchedulers())
                .retry(2L);
    }

    public Single<Map<String, String>> deleteLesson(long lessonId) {

        String auth = App.getAppInstance().getPreferences().getToken();

        return apiInterface.deleteLesson(auth, lessonId)
                .doOnError(Throwable::printStackTrace)
                .compose(RxUtils.applySingleSchedulers())
                .retry(2L);
    }

}
