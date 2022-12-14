package piano;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

public class PianoView extends View {

    public static final int NB = 14;
    private Paint black, blue, white;
    private ArrayList<Key> whites = new ArrayList<>();
    private ArrayList<Key> blacks = new ArrayList<>();
    private int keyWidth, height;
    private AudioSoundPlayer soundPlayer;

    public PianoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        black = new Paint();
        black.setColor(Color.BLACK);
        white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);
        blue = new Paint();
        //blue.setColor(Color.BLUE);
        blue.setColor(Color.HSVToColor(new float[]{195, (float)60.1, (float)97.3}));
        blue.setStyle(Paint.Style.FILL);
        soundPlayer = new AudioSoundPlayer(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        keyWidth = w / NB;
        height = h;
        int count = 15;

        for (int i = 0; i < NB; i++) {
            int left = i * keyWidth;
            int right = left + keyWidth;

            if (i == NB - 1) {
                right = w;
            }

            RectF rect = new RectF(left, 0, right, h);
            whites.add(new Key(rect, i+1));

            if (i != 0 && i != 3 && i != 7 && i != 10) {
                rect = new RectF(
                        (float) (i-1) * keyWidth + 0.75f * keyWidth,
                        0,
                        (float) i * keyWidth + 0.25f * keyWidth,
                        0.67f * height);
                blacks.add(new Key(rect, count));
                count++;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Key k: whites) {
            canvas.drawRect(k.rectf, k.clicked ? blue : white);
        }
        for (int i = 1; i < NB; i++) {
            canvas.drawLine(i * keyWidth, 0, i * keyWidth, height, black);
        }
        for (Key k : blacks) {
            canvas.drawRect(k.rectf, k.clicked ? blue : black);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        boolean isDownAction = action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE;

        for (int touchIndex = 0; touchIndex < event.getPointerCount(); touchIndex++) {
            float x = event.getX(touchIndex);
            float y = event.getY(touchIndex);

            Key k = keyForCoords(x,y);

            if (k != null) {
                k.clicked = isDownAction;
            }
        }

        ArrayList<Key> tmp = new ArrayList<>(whites);
        tmp.addAll(blacks);

        for (Key k : tmp) {
            if (k.clicked) {
                soundPlayer.playNote(k.sound);
                invalidate();
                //if (!soundPlayer.isNotePlaying(k.sound)) {
                //    System.out.println(k.sound);
                //    soundPlayer.playNote(k.sound);
                //    invalidate();
                //} else {
                //    releaseKey(k);
                //}
            } else {
                //soundPlayer.stopNote(k.sound);
                releaseKey(k);
            }
        }

        return true;
    }

    public boolean playChord(int[] notes){

        List<Key> keys = new ArrayList<Key>();

        for (int note: notes) {
            Key k = getKey(note);
            keys.add(k);
            k.clicked = true;
            soundPlayer.playNote(k.sound);
            invalidate();
        }
//        try {
//            Thread.sleep(200);
//        }
//        catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }

        for (Key k: keys){
            releaseKey(k);
        }

        return true;
    }

    private Key getKey(int note) {
        if (note < 15) {
            return whites.get(note - 1);
        }
        else {
            return blacks.get(note - 15);
        }
    }

    private Key keyForCoords(float x, float y) {
        for (Key k : blacks) {
            if (k.rectf.contains(x,y)) {
                return k;
            }
        }

        for (Key k : whites) {
            if (k.rectf.contains(x,y)) {
                return k;
            }
        }

        return null;
    }

    private void releaseKey(final Key k) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                k.clicked = false;
                handler.sendEmptyMessage(0);
            }
        }, 100);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            invalidate();
        }
    };
}
