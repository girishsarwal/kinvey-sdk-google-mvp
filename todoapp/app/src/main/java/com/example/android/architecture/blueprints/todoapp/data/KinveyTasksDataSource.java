package com.example.android.architecture.blueprints.todoapp.data;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyClientBuilderCallback;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.android.callback.KinveyReadCallback;
import com.kinvey.android.model.User;
import com.kinvey.android.store.DataStore;
import com.kinvey.android.store.UserStore;
import com.kinvey.java.auth.Credential;
import com.kinvey.java.core.KinveyClientCallback;
import com.kinvey.java.model.KinveyReadResponse;
import com.kinvey.java.store.StoreType;

import java.io.IOException;
import java.util.List;

public class KinveyTasksDataSource implements TasksDataSource {

    private static String TAG = "TASKSACTIVITY";
    private static KinveyTasksDataSource INSTANCE;

    private final static String KINVEY_APP_KEY="kid_rytV_S_QE";
    private final static String KINVEY_APP_SECRET = "34ff93ca6e04405397941d1cdff3a72a";
    private final static String USERNAME = "girishsarwal";
    private final static String PASSWORD = "gs";
    private final static String COLLECTION = "tasks";

    private Client kinveyClient;
    private DataStore<Task> dataStore;


    public static KinveyTasksDataSource getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new KinveyTasksDataSource(context);
        }
        INSTANCE.ping();
        INSTANCE.dataStore = DataStore.collection(COLLECTION, Task.class, StoreType.NETWORK, INSTANCE.kinveyClient);
        INSTANCE.LoginUser();
        return INSTANCE;
    }


    private KinveyTasksDataSource(Context context){
        kinveyClient = new Client.Builder(KINVEY_APP_KEY, KINVEY_APP_SECRET, context).build();
    }

    public void ping() {
        kinveyClient.ping(new KinveyPingCallback() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                Log.d(TAG, "Connected to remote data source!");
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.w(TAG, "Problem connecting to remote data source!");
            }
        });
    }

    private void LoginUser(){
        UserStore.logout(kinveyClient, new KinveyClientCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "All active users logged out!");
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.w(TAG, "There was a problem logging out active users");
            }
        });
        try {

            UserStore.login(USERNAME, PASSWORD, kinveyClient, new KinveyClientCallback<User>() {
                @Override
                public void onSuccess(User user) {
                    Log.d(TAG, "User " + USERNAME + " logged in successfully");
                }

                @Override
                public void onFailure(Throwable throwable) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getTasks(@NonNull final LoadTasksCallback callback) {
        if(kinveyClient.getActiveUser() == null){
            Log.e(TAG, "There is no active user!");
            return;
        }
        dataStore.find(new KinveyReadCallback<Task>() {
            @Override
            public void onSuccess(KinveyReadResponse<Task> kinveyReadResponse) {
                if(kinveyReadResponse.getListOfExceptions().size() > 0){
                    /** handle errors **/
                }
                callback.onTasksLoaded(kinveyReadResponse.getResult());
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {

    }

    @Override
    public void saveTask(@NonNull Task task) {

    }

    @Override
    public void completeTask(@NonNull Task task) {

    }

    @Override
    public void completeTask(@NonNull String taskId) {

    }

    @Override
    public void activateTask(@NonNull Task task) {

    }

    @Override
    public void activateTask(@NonNull String taskId) {

    }

    @Override
    public void clearCompletedTasks() {

    }

    @Override
    public void refreshTasks() {

    }

    @Override
    public void deleteAllTasks() {

    }

    @Override
    public void deleteTask(@NonNull String taskId) {

    }
}
