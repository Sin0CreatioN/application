package tsmcomp.question.common.util;

import java.util.ArrayList;

/**
 * 登録したタスクを順に実行する
 */
public class FallExecutor{

    ArrayList<Task> mTasks;
    Task mCurrentTask;
    Thread mObserveThread;
    boolean mIsRunning;

    public FallExecutor(){
        mTasks = new ArrayList<>();
        mIsRunning = true;
        mObserveThread = new Thread(new Runnable(){
            @Override
            public void run() {
                headTaskStart();    //  初期タスクを取り出す
                observe();
            }
        });
    }


    /**
     * 監視用スレッドが動きます
     */
    private void observe() {
        //  中止されるかタスクがなくなるまで動く
        while(mIsRunning && mTasks.size() > 0){
            //  タスクが完了したら次のタスクを取り出す
            if(mCurrentTask.isFinished())
                headTaskStart();
        }
    }

    /**
     * 次のタスクを取り出して開始する
     */
    private void headTaskStart(){
        mCurrentTask = mTasks.remove(0);
        mCurrentTask.onStart();
    }


    public void registerTask(Task task){
        mTasks.add(task);
    }


    public void forceStop(){
        mIsRunning = false; // 中止させる
    }




    public interface Task{
        boolean isFinished();
        void onStart();
        void onCanceled();
    }

}
