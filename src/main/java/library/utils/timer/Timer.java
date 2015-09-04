package library.utils.timer;

import android.os.CountDownTimer;
import android.util.Log;

/**
 * Created by BABEL Sistemas de Información.
 */
public class Timer {

    CountDownTimer mTimer;

    public void startTimer(final int time, final OnFinishedTimer listener) {
        mTimer = new CountDownTimer(time, 1 * 1000) {
            @Override
            public void onTick(long l) {
                Log.d("TIMER", String.valueOf(l));
            }

            @Override
            public void onFinish() {
                listener.timerFinished();
            }
        };

        mTimer.start();
    }

    public void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }


}
