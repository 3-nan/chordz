package piano;

import android.graphics.RectF;

public class Key {

    public int sound;
    public RectF rectf;
    public boolean clicked;

    public Key(RectF rectf, int sound) {
        this.rectf = rectf;
        this.sound = sound;
    }
}
