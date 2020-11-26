package com.xmm.rxjavademo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action9;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * https://gank.io/post/560e15be2dca930e00da1083
 * https://www.cnblogs.com/liushilin/p/6164901.html
 * 常用操作符:https://www.jianshu.com/p/3fdd9ddb534b
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
//                observable
//                        .throttleWithTimeout(2000,TimeUnit.MILLISECONDS)
//                        .subscribe(subscriber);

//                observable2.subscribe(onNextAction);
                    //java.lang.String cannot be cast to java.lang.Throwable
//                observable2.subscribe(onErrorAction);
            }
        });

    }



    private void onclick(){
        //打印字符串
        Subscription subscribe = Observable.from(s)

                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        textView.setText(s + "," + textView.getText().toString());
                    }
                });

        /* 变换 —— map */
        /**
         * map 是 一对一 转换
         * 将输入的字符串转化为bitmap
         * FuncX对应又返回的ActionX
         */
//        Observable.just("")
//                .throttleFirst(1000, TimeUnit.SECONDS)
//                .map(new Func1<String, Bitmap>() {
//                    @Override
//                    public Bitmap call(String s) {
//                        return null;
//                    }
//                }).subscribe(new Action1<Bitmap>() {
//            @Override
//            public void call(Bitmap bitmap) {
//
//            }
//        });
        //转换的原理是内部实现lift方法。通过新建observable 关联原有的 去了
//        observable.lift(new Observable.Operator<String, Integer>() {
//            @Override
//            public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
//                // 将事件序列中的 Integer 对象转换为 String 对象
//                return new Subscriber<Integer>() {
//                    @Override
//                    public void onNext(Integer integer) {
//                        subscriber.onNext("" + integer);
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        subscriber.onCompleted();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        subscriber.onError(e);
//                    }
//                };
//            }
//        });

        /**
         * flatMap 一对多转换
         */


        /* 变换 —— map  */

    }
    /* 线程控制 —— Scheduler (一) */

    /* 线程控制 —— Scheduler (一) */

    /* 场景示例 */
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