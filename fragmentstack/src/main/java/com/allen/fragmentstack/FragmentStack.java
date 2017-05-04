package com.allen.fragmentstack;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Allen on 2016/12/5.
 * 管理fragment出入栈
 * 启动模式的策略:
 * Standed:标准模式，可以在当前任务栈中添加同一个类型的fragment.
 * SingleTop:顶部唯一，如果欲添加的fragment类型和当前任务栈的顶部fragment相同，则直接调用顶部fragment的onNewIntent()方法，
 * 否则，和Standed模式一样添加fragment到任务栈.
 * SingleTask:任务栈唯一，如果欲添加的fragment在当前任务栈中有同一类型，则删除这个任务栈中的fragment上面的所有frament,
 * 知道这个fragment在顶部,并调用fragment的onNewIntent()方法，否则创建新的任务栈，以FragmentIntent中的
 * taskAffinity(任务栈名)或者fragment作为任务栈的名字，FragmentIntent中的taskAffinity较优先.
 * SingleInstance:单例，一个任务栈中只存放一个fragment,如果欲添加的fragment在别的任务栈中已存在，则将那个任务栈移到顶部，
 * 并调用fragment的onNewIntent()方法.
 * 否则，新建一个任务栈，将这个fragment放入这个任务栈中.
 */
public class FragmentStack {
    private static final String TAG = "FragmentStack";

    private static final String LAUNCHED_FRAGMENT = "Launched_Fragment";
    private static final String TASKS = "Tasks";
    static final String ENTER_ANIM = "inner_enter_anim";
    static final String EXIT_ANIM = "inner_exit_anim";
    static final String POP_ENTER_ANIM = "inner_pop_enter_anim";
    static final String POP_EXIT_ANIM = "inner_pop_exit_anim";

    private FragmentManager fragmentManager;
    private int enterAnim; //新的fragment进场动画
    private int exitAnim;   //当前fragment出场动画
    private int popEnterAnim; //之前的fragment进场动画
    private int popExitAnim;  //新的fragment出场动画

    /**
     * key对应任务栈task
     * value对应这个task中所有的Fragment
     */
    private HashMap<TaskStackRecord, ArrayList<FragmentRecord>> launchedFragments;
    /**
     * 任务栈列表
     */
    private ArrayList<TaskStackRecord> taskStacks;
    private RootFragment currentFragment;
    private KeyGenerator keyGenerator;

    public FragmentStack(Builder builder) {
        this.fragmentManager = builder.fragmentManager;
        enterAnim = builder.enterAnim;
        exitAnim = builder.exitAnim;
        popEnterAnim = builder.popEnterAnim;
        popExitAnim = builder.popExitAnim;

        launchedFragments = new HashMap<>();
        taskStacks = new ArrayList<>();
        keyGenerator = new KeyGenerator();
    }

    private void printfStack() {
        Log.d(TAG, "************打印任务栈开始*************");
        final int N = taskStacks.size();
        for (int i = N - 1; i >= 0; i--) {
            TaskStackRecord task = taskStacks.get(i);
            Log.d(TAG, "任务栈-->>" + task.taskStackAffinity);
            ArrayList<FragmentRecord> records = launchedFragments.get(task);
            for (int j = records.size() - 1; j >= 0; j--) {
                FragmentRecord record = records.get(j);
                Log.d(TAG, "type-->>" + record.type + ", tag-->>" + record.tag);
            }
        }

        Log.d(TAG, "************打印任务栈结束*************");
    }

    /**
     * 将fragment压栈
     *
     * @param container
     * @param intent
     */
    public void push(int container, FragmentIntent intent) {
        push(container, intent, 0);
    }

    /**
     * 将fragment压栈
     *
     * @param container
     * @param intent
     */
    public void push(int container, FragmentIntent intent, int requestCode) {
        switch (intent.getLaunchFlag()) {
            case FragmentIntent.FLAG_STANDED:
                addFragmentStanded(container, intent, requestCode);
                break;
            case FragmentIntent.FLAG_SINGLE_TOP:
                addFragmentSingleTop(container, intent, requestCode);
                break;
            case FragmentIntent.FLAG_SINGLE_TASK:
                addFragmentSingleTask(container, intent, requestCode);
                break;
            case FragmentIntent.FLAG_SINGLE_INSTANCE:
                addFragmentSingleInstance(container, intent, requestCode);
                break;
        }

        printfStack();
    }

    private RootFragment newFragment(Class<? extends RootFragment> cls, int requestCode, FragmentIntent intent) {
        try {
            Constructor<? extends RootFragment> constructor = cls.getConstructor();
            RootFragment fragment = constructor.newInstance();
            fragment.requestCode = requestCode;
            fragment.intent = intent;

            return fragment;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Log.e(TAG, "must provide a constructor without any params");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "build fragment:" + cls.getName() + " failed");
        }


        return null;
    }

    private RootFragment getFragment(String tag, int requestCode, FragmentIntent intent) {
        RootFragment target = (RootFragment) fragmentManager.findFragmentByTag(tag);
        target.requestCode = requestCode;
        target.intent = intent;
        return target;
    }

    /**
     * 添加fragment进任务栈
     *
     * @param container
     * @param task
     */
    private void addFragment(int container, RootFragment fragment, TaskStackRecord task) {
        if (fragment == null) {
            return;
        }
        ArrayList<FragmentRecord> fragments = launchedFragments.get(task);
        if (fragments == null) {
            fragments = new ArrayList<>();
            launchedFragments.put(task, fragments);
        }
        String tag = keyGenerator.getTag(fragment);
        String type = keyGenerator.getType(fragment);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim).add(container, fragment, tag);
        if (currentFragment != null) {
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim).hide(currentFragment);
        }
        ft.commitAllowingStateLoss();
        if (currentFragment != null) {
            currentFragment.exit();
        }
        fragment.enter();
        currentFragment = fragment;

        fragments.add(new FragmentRecord(tag, type));
        if (taskStacks.contains(task)) {
            taskStacks.remove(task);
        }
        taskStacks.add(task);
    }

    /**
     * Standed启动模式
     *
     * @param container
     * @param intent
     */
    private void addFragmentStanded(int container, FragmentIntent intent, int requestCode) {
        RootFragment fragment = newFragment(intent.getComponet(), requestCode, intent);
        if (TextUtils.isEmpty(intent.getTaskAffinity())) {
            TaskStackRecord recentTask = getRecentTask();
            if (recentTask == null || (recentTask.stackMode & FragmentIntent.FLAG_SINGLE_INSTANCE) != 0) {
                /**
                 * 问题
                 */
                recentTask = new TaskStackRecord(FragmentIntent.FLAG_STANDED, keyGenerator.getTag(fragment));
            }
            addFragment(container, fragment, recentTask);
        } else {
            addFragment(container, fragment, new TaskStackRecord(FragmentIntent.FLAG_STANDED, keyGenerator.getTag(fragment)));
        }
    }

    /**
     * SingleTop启动模式
     *
     * @param container
     * @param intent
     */
    private void addFragmentSingleTop(int container, FragmentIntent intent, int requestCode) {
        TaskStackRecord task;
        if (taskStacks != null && !taskStacks.isEmpty()) {
            task = taskStacks.get(taskStacks.size() - 1);
            ArrayList<FragmentRecord> fragments = launchedFragments.get(task);
            if (!fragments.isEmpty()) {
                FragmentRecord record = fragments.get(fragments.size() - 1);
                if (record.type.equals(keyGenerator.getType(intent.getComponet()))) {
                    /**
                     * 将fragment携带的参数传递给任务栈中顶部的fragment
                     */
                    RootFragment target = getFragment(record.tag, requestCode, intent);
                    target.activity.fragmentAnimRunnging = false;
                    target.onNewIntent(intent);
                    return;
                }
            }
        }
        RootFragment fragment = newFragment(intent.getComponet(), requestCode, intent);
        task = new TaskStackRecord(FragmentIntent.FLAG_SINGLE_TOP, keyGenerator.getTag(fragment));
        addFragment(container, fragment, task);
    }

    /**
     * SingleTask启动模式
     *
     * @param container
     */
    private void addFragmentSingleTask(int container, FragmentIntent intent, int requestCode) {
        TaskStackRecord task;
        if (taskStacks != null && !taskStacks.isEmpty()) {
            task = taskStacks.get(taskStacks.size() - 1);
            ArrayList<FragmentRecord> fragments = launchedFragments.get(task);
            if (!fragments.isEmpty()) {
                String type = keyGenerator.getType(intent.getComponet());
                FragmentRecord found = null;
                for (FragmentRecord record : fragments) {
                    if (record.type.equals(type)) {
                        /**
                         * 在当前任务栈找到了同类型的fragment
                         */
                        found = record;

                        break;
                    }
                }
                if (found != null) {
                    /**
                     * 删除found上面的fragment
                     */
                    int index = fragments.indexOf(found);
                    for (int i = fragments.size() - 1; i > index; i--) {
                        fragments.remove(i);
                    }

                    RootFragment target = getFragment(found.tag, requestCode, intent);
                    target.onNewIntent(intent);
                    showFragment(target);

                    return;
                }
            }
        }
        RootFragment fragment = newFragment(intent.getComponet(), requestCode, intent);
        task = new TaskStackRecord(FragmentIntent.FLAG_SINGLE_TASK, keyGenerator.getTag(fragment));

        addFragment(container, fragment, task);
    }

    private TaskStackRecord getTaskRecord(String type) {
        for (TaskStackRecord record : taskStacks) {
            if (record.taskStackAffinity.equals(type)) {
                return record;
            }
        }
        return null;
    }

    /**
     * SingleInstance启动模式
     *
     * @param container
     * @param intent
     */
    private void addFragmentSingleInstance(int container, FragmentIntent intent, int requestCode) {
        TaskStackRecord task;
        if (taskStacks != null && !taskStacks.isEmpty()) {
            String type = keyGenerator.getType(intent.getComponet());
            task = getTaskRecord(type);
            if (task != null) {
                /**
                 * 移到任务栈顶
                 */
                taskStacks.remove(task);
                task.stackMode = FragmentIntent.FLAG_SINGLE_INSTANCE;
                taskStacks.add(task);

                ArrayList<FragmentRecord> fragmentRecords = launchedFragments.get(task);
                /**
                 * SingleInstance模式，任务栈中只有一个任务
                 */
                RootFragment target = getFragment(fragmentRecords.get(0).tag, requestCode, intent);
                target.onNewIntent(intent);
                showFragment(target);
                return;
            }
        }
        RootFragment fragment = newFragment(intent.getComponet(), requestCode, intent);
        task = new TaskStackRecord(FragmentIntent.FLAG_SINGLE_INSTANCE, keyGenerator.getType(intent.getComponet()));
        addFragment(container, fragment, task);
    }

    public RootFragment pop() {
        return pop(currentFragment);
    }

    /**
     * fragment出栈
     *
     * @return
     */
    public RootFragment pop(RootFragment fragment) {
        if (!taskStacks.isEmpty()) {
            FragmentRecord found = null;
            for (int i = taskStacks.size() - 1; i >= 0; i--) {
                TaskStackRecord task = taskStacks.get(i); //取得任务栈
                ArrayList<FragmentRecord> fragmentRecords = launchedFragments.get(task); //任务栈中的任务列表
                /**
                 * 讲道理，fragmentRecords不可能为空的，因为在为空的那一刻就会被移除
                 */
                if (fragmentRecords != null && !fragmentRecords.isEmpty()) {
                    for (int j = fragmentRecords.size() - 1; j >= 0; j--) {
                        if (fragmentRecords.get(j).tag.equals(keyGenerator.getTag(fragment))) {
                            found = fragmentRecords.remove(j);
                            break;
                        }
                    }

                    if (found != null) {
                        if (fragmentRecords.isEmpty()) { //如果任务栈中没有没有记录了，则清除map中的项
                            launchedFragments.remove(task);
                            taskStacks.remove(task);
                            /**
                             * 如果移除了顶层的任务栈，则要取得之顶层之前的任务栈来作为顶层的任务栈
                             */
                            if (!taskStacks.isEmpty() && currentFragment == fragment) {
                                task = taskStacks.get(taskStacks.size() - 1);
                                fragmentRecords = launchedFragments.get(task);
                            } else if (taskStacks.isEmpty()) {
                                /**
                                 *执行到这里，说明取得的任务(fragment)，是最后一个任务栈中的最后一个任务了，此时直接返回null，
                                 * 让上层作退出应用的处理，或者其他处理逻辑
                                 */

                                return null;
                            }
                        }
                        if (fragment != null && currentFragment == fragment) {
                            hideFragmentAndShowTaskTopFragment(fragment, fragmentRecords);
                        } else {
                            RootFragment f = (RootFragment) fragmentManager.findFragmentByTag(found.tag);
                            fragmentManager.beginTransaction()
                                    .remove(f).commitAllowingStateLoss();
                        }
                        printfStack();
                        return fragment;
                    }
                }
            }
        }
        return null;
    }


    public void onSaveInstance(Bundle outState) {
        Log.d(TAG, "onSaveInstance");
        outState.putSerializable(LAUNCHED_FRAGMENT, launchedFragments);
        outState.putSerializable(TASKS, taskStacks);
        outState.putInt(ENTER_ANIM, enterAnim);
        outState.putInt(EXIT_ANIM, exitAnim);
        outState.putInt(POP_ENTER_ANIM, popEnterAnim);
        outState.putInt(POP_EXIT_ANIM, popExitAnim);
    }

    public void onRestoreInstance(Bundle savedInstance) {
        Log.d(TAG, "onRestoreInstance");
        taskStacks = (ArrayList<TaskStackRecord>) savedInstance.getSerializable(TASKS);
        launchedFragments = (HashMap<TaskStackRecord, ArrayList<FragmentRecord>>) savedInstance.getSerializable(LAUNCHED_FRAGMENT);
        enterAnim = savedInstance.getInt(ENTER_ANIM);
        exitAnim = savedInstance.getInt(EXIT_ANIM);
        popEnterAnim = savedInstance.getInt(POP_ENTER_ANIM);
        popExitAnim = savedInstance.getInt(POP_EXIT_ANIM);

        resumeLastFragment();
    }

    /**
     * 获取最近的任务栈
     *
     * @return
     */
    private TaskStackRecord getRecentTask() {
        if (taskStacks == null || taskStacks.isEmpty()) {
            return null;
        }
        return taskStacks.get(taskStacks.size() - 1);
    }

    /**
     * 恢复最近的fragment
     */
    private void resumeLastFragment() {
        if (taskStacks != null && !taskStacks.isEmpty()) {
            TaskStackRecord lastTask = taskStacks.get(taskStacks.size() - 1);
            ArrayList<FragmentRecord> fragments = launchedFragments.get(lastTask);
            FragmentRecord lastFragmentRecord = fragments.get(fragments.size() - 1);
            RootFragment fragment = (RootFragment) fragmentManager.findFragmentByTag(lastFragmentRecord.tag);
            if (fragment != null) {
                showFragment(fragment);
            }
        }
    }

    /**
     * 显示fragment，并隐藏currentFragment
     *
     * @param fragment 要显示的fragment
     */
    private void showFragment(RootFragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (currentFragment != null && currentFragment != fragment) {
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim).hide(currentFragment);
        }
        ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim).show(fragment);

        ft.commitAllowingStateLoss();
        if (currentFragment != null && currentFragment != fragment) {
            currentFragment.exit();
        }
        fragment.enter();
        currentFragment = fragment;
    }

    /**
     * 隐藏fragment，并显示任务栈中上一个fragment
     *
     * @param fragment
     * @param fragments
     */
    private void hideFragmentAndShowTaskTopFragment(RootFragment fragment, ArrayList<FragmentRecord> fragments) {
        FragmentRecord record = fragments.get(fragments.size() - 1);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        RootFragment lastFragment = null;
        if (!TextUtils.isEmpty(record.tag)) {
            lastFragment = (RootFragment) fragmentManager.findFragmentByTag(record.tag);
            if (lastFragment != null) {
                ft.setCustomAnimations(popEnterAnim, popExitAnim, enterAnim, exitAnim).show(lastFragment);
                if (fragment.requestCode != -1) {
                    lastFragment.onFragmentResult(fragment.requestCode, fragment.resultCode, fragment.resultIntent);
                }
            }
        }
        ft.setCustomAnimations(popEnterAnim, popExitAnim, enterAnim, exitAnim).remove(fragment);
        ft.commitAllowingStateLoss();
        fragment.popExit();
        if (lastFragment != null) {
            lastFragment.popEnter();
        }
        currentFragment = lastFragment;
    }

    public static class Builder {
        private FragmentManager fragmentManager;
        private int enterAnim; //新的fragment进场动画
        private int exitAnim;   //当前fragment出场动画
        private int popEnterAnim; //之前的fragment进场动画
        private int popExitAnim;  //新的fragment出场动画

        public Builder(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
            enterAnim = R.anim.default_fragment_right_in;
            exitAnim = R.anim.default_fragment_left_out;
            popEnterAnim = R.anim.default_fragment_left_in;
            popExitAnim = R.anim.default_fragment_right_out;
        }

        public Builder newEnterAnim(int enterAnim) {
            this.enterAnim = enterAnim;
            return this;
        }

        public Builder newExitAnim(int exitAnim) {
            this.exitAnim = exitAnim;
            return this;
        }

        public Builder oldEnterAnim(int enterAnim) {
            this.popEnterAnim = enterAnim;
            return this;
        }

        public Builder oldExitAnim(int exitAnim) {
            this.popExitAnim = exitAnim;
            return this;
        }
    }
}
