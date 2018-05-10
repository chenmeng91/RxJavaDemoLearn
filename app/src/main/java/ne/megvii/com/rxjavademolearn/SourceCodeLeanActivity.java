package ne.megvii.com.rxjavademolearn;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chenmeng on 2018/4/16.
 *
 * 博客地址：https://blog.csdn.net/zxt0601/article/details/61614799
 */

public class SourceCodeLeanActivity extends Activity {
    Disposable disposable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("aa");
                emitter.onComplete();
            }
        }).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return s;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return null;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable =d;
            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }



        @SuppressWarnings("ResultOfMethodCallIgnored")
        public static void main(String[] args) {
            Observable.zip(first(), second(), zipper())
                    .subscribe(System.out::println);
        }

        private static ObservableSource<String> first() {
            return Observable.create(emitter -> {
                        Thread.sleep(1000);
                        emitter.onNext("11");
                        emitter.onNext("12");
                        emitter.onNext("13");
                        emitter.onNext("16");
                    }
            );
        }

        private static ObservableSource<String> second() {
            return Observable.create(emitter -> {
                        emitter.onNext("21");
                        Thread.sleep(2000);
                        emitter.onNext("22");
                        Thread.sleep(3000);
                        emitter.onNext("23");
                    }
            );
        }

        private static BiFunction<String, String, String> zipper() {
            return (s1, s2) -> s1 + "，" + s2;
        }



}
