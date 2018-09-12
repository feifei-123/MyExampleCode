package com.example.myroom;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.arch.lifecycle.ViewModelProviders;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private EditText mInput;
    private RecyclerView mRecyclerView;
    private UserAdapter mUserAdapter;

    private MyViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivty_main);

        mInput = findViewById(R.id.et);
        mRecyclerView = findViewById(R.id.recycler);
        mUserAdapter = new UserAdapter();
        mRecyclerView.setAdapter(mUserAdapter);

        mViewModel = ViewModelProviders.of(this).get(MyViewModel.class);

        //观察ViewModel中的liveData
        mViewModel.getmUsers().observe(this,
                new Observer<List<User>>() {
                    @Override
                    public void onChanged(@Nullable List<User> users) {
                        mUserAdapter.setList(users);
                        mUserAdapter.notifyDataSetChanged();
                        if(mUserAdapter.getItemCount()>1){
                            mRecyclerView.smoothScrollToPosition(mUserAdapter.getItemCount() - 1);
                        }
                    }
                });

    }


    public int age = 10;

    public User lastUser;
    public void add (View view){

        Disposable disposable = Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

                User user = new User();
                user.setFirstName("bai");
                user.setLastName(mInput.getText().toString());
                user.setAge(age++);
                user.setAddress("北京市五道口");
                lastUser = user;
                mViewModel.getUserDao().insert(user);
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }


    public void doDelete(View view){


        Disposable disposable =  Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                User user = new User();
                user.setLastName("feifei");
                user.setUid(2);
                user.setBirthday(new Date());
                int  count = mViewModel.getUserDao().delete(user);
//                int  count = mViewModel.getUserDao().deleteByQuery(6);
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }


    public class UserHolder extends RecyclerView.ViewHolder {

        public final TextView mText;

        public UserHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(android.R.id.text1);
        }
    }

    public class UserAdapter extends RecyclerView.Adapter<UserHolder> {

        private static final String TAG = "UserAdapter";
        private List<User> mUsers = new ArrayList<>();

        public void setList(List<User> users) {
            mUsers = users;
        }

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new UserHolder(inflater.inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, int position) {
            holder.mText.setText(mUsers.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }
    }

}
