package com.djavid.schoolapp.model.notifications;

import android.annotation.SuppressLint;

import com.annimon.stream.Stream;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andrei on 25-Feb-18.
 */

public class Notification implements Serializable {
    @SerializedName("id")
    @Expose
    public long id;

    @SerializedName("text")
    @Expose
    public String text = "";

    @SerializedName("frequency")
    @Expose
    public String frequency = frequency_never;
    private final static String frequency_never = "0 12 31 */12 *";

    @SerializedName("send_once")
    @Expose
    public boolean noRepeat = true;

    @SerializedName("groups")
    @Expose
    public List<Long> groups = new LinkedList<>();

    @SerializedName("created_by")
    @Expose
    public String author = "";

    public RepeatInfo getRepeatInfo() {
        RepeatInfo info = new RepeatInfo();
        String[] freqs = frequency.split(" ");
        if (noRepeat) {
            info.mode = RepeatInfo.Mode.Never;
        } else if ("*".equals(freqs[2])) {
            info.mode = RepeatInfo.Mode.DailyMonth;
            info.daysOfMonth = new HashSet<>(Stream.of(freqs[4].split(","))
                    .map(Integer::parseInt).toList());
        } else if ("*".equals(freqs[4])) {
            info.mode = RepeatInfo.Mode.DailyWeek;
            info.daysOfWeek = new HashSet<>(Stream.of(freqs[2].split(","))
                    .map(Integer::parseInt).toList());
        } else {
            info.mode = RepeatInfo.Mode.Never;
        }
        return info;
    }

    @SuppressLint("DefaultLocale")
    public void setRepeatInfo(RepeatInfo info) {
        switch (info.mode) {
            case Never:
                frequency = frequency_never;
                noRepeat = true;
                return;
            case DailyWeek:
                frequency = String.format("%d %d * * %s", info.minute, info.hour,
                        Stream.of(info.daysOfWeek)
                                .reduce("", (a, b) -> a + "," + b.toString())
                                .replaceFirst(",", ""));
                noRepeat = false;
                return;
            case DailyMonth:
                if (info.daysOfMonth.isEmpty()) {
                    frequency = frequency_never;
                }
                frequency = String.format("%d %d %s * *", info.minute, info.hour,
                        Stream.of(info.daysOfMonth)
                                .reduce("", (a, b) -> a + "," + b.toString())
                                .replaceFirst(",", ""));
                noRepeat = false;
                return;
        }
    }
}
