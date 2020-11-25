package com.xmm.rxjavademo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action9;
import rx.schedulers.Schedulers;

/**
 * https://gank.io/post/560e15be2dca930e00da1083
 * https://www.cnblogs.com/liushilin/p/6164901.html
 */
public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tv);

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick();
//                observable2.subscribe(observer);
//                observable2.subscribe(subscriber);

//                observable2.subscribe(onNextAction);
                    //java.lang.String cannot be cast to java.lang.Throwable
//                observable2.subscribe(onErrorAction);
            }
        });
    }

    private void onclick(){
        //打印字符串
        Observable.from(s)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        textView.setText(s+","+textView.getText().toString());
                    }
                });

    }
    /* 3. 线程控制 —— Scheduler (一) */
    /* 3. 线程控制 —— Scheduler (一) */

    /* 4) 场景示例 */
    String[] s = new String[]{"zhangsan","lisi"};



    /* 4) 场景示例 */

    /**
     * 定义观察者1
     * 实则转化成 Subscriber
     */
    Observer<String> observer = new Observer<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {

        }
    };

    /**
     * 定义观察者 Observer 的抽象实现类
     * 区别：
     * 1:增加onstart方法。
     * 2：unsubscribe方法。取消订阅
     */
    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {
            Log.e(TAG, "onCompleted: " );
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "Throwable: " );
        }

        @Override
        public void onNext(String s) {
            Log.e(TAG, "onNext: "+s );

        }

        @Override
        public void onStart() {
            super.onStart();
            Log.e(TAG, "onStart: " );
            /**
             * 可做数据的重置  。不适合做 显示加载框。因为不能控制线程
             */
        }
    };
    /**
     * 定义观察者 方法2
     * 实则转化成 Subscriber
     */
    Action1<String> onNextAction = new Action1<String>() {
        // onNext()
        @Override
        public void call(String s) {
            Log.e(TAG, "call: " +s);
        }
    };
    Action1<Throwable> onErrorAction = new Action1<Throwable>() {
        // onError()
        @Override
        public void call(Throwable throwable) {
            Log.e(TAG, "call: " +throwable.getMessage());
        }
    };
    Action0 onCompletedAction = new Action0() {
        @Override
        public void call() {
            Log.e(TAG, "call0: " );
        }
    };


    /**
     * 创建 Observable 方法1
     * 每一次执行：
     * onStart -> subscriber 调用那些方法就执行哪些方法
     * 比如不调用  subscriber.onCompleted(); 就不会执行onCompleted
     *
     */
    Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Aloha");
            subscriber.onNext("Hi");
            //只有调用的onCompleted 观察者的才会调用onCompleted
            subscriber.onCompleted();
        }

    });
    /**
     * 创建 Observable 方法2  just 和 from
     * 第一次执行：
     * onStart -> onNext -> onCompleted
     * 第二次执行：
     * 只执行onStart
     */
    Observable observable1 = Observable.just("Hello","Aloha","Hi");
    String[] words = {"Hello", "Hi", "Aloha"};
    Observable observable2 = Observable.from(words);




}