package com.djavid.schoolapp.view.fragment.event_details;

import android.databinding.Observable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.djavid.schoolapp.databinding.FragmentEventgroupitemBinding;
import com.djavid.schoolapp.databinding.FragmentEventgroupitemParticipantBinding;
import com.djavid.schoolapp.model.users.User;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Andrei Kolomiets
 */
public class ParticipantsRecyclerViewAdapter extends RecyclerView.Adapter<ParticipantsRecyclerViewAdapter.ViewHolder> {

    private Consumer<List<User>> createOnParticipantsArrivedHandler(List<User> participants) {
        return users -> {
            participants.addAll(users);
            notifyDataSetChanged();
        };
    }

    private Observable.OnPropertyChangedCallback onGroupChanged = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            participants = new LinkedList<>();
            binding.getGroup().participants
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(createOnParticipantsArrivedHandler(participants));
        }
    };

    private final FragmentEventgroupitemBinding binding;
    private List<User> participants = new LinkedList<>();

    public ParticipantsRecyclerViewAdapter(FragmentEventgroupitemBinding binding) {
        this.binding = binding;
        binding.addOnPropertyChangedCallback(onGroupChanged);
        binding.getGroup().participants
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createOnParticipantsArrivedHandler(participants));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                FragmentEventgroupitemParticipantBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setParticipant(participants.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final FragmentEventgroupitemParticipantBinding binding;

        public ViewHolder(FragmentEventgroupitemParticipantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
