package piano;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
//import android.media.SoundPool;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.SparseArray;

import com.example.chordz.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class AudioSoundPlayer extends Activity implements SoundPool.OnLoadCompleteListener{

    private SoundPool soundPool;
    private Context context;
    private static final SparseArray<Integer> SOUND_MAP = new SparseArray<>();
    //private SparseArray<Integer> threadMap = null;
    private final Map<Integer, Integer> loadedSoundCache = new HashMap<>();
    private final int maxPlaying = 6;
    private final int volume = 1;
    private final int priority = 1;
    private final int no_loop = 0;
    private final float normal_playback_rate = 1f;

    static {
        // white keys sounds
        SOUND_MAP.put(1, R.raw.c3);
        SOUND_MAP.put(2, R.raw.d3);
        SOUND_MAP.put(3, R.raw.e3);
        SOUND_MAP.put(4, R.raw.f3);
        SOUND_MAP.put(5, R.raw.g3);
        SOUND_MAP.put(6, R.raw.a3);
        SOUND_MAP.put(7, R.raw.b3);
        SOUND_MAP.put(8, R.raw.c4);
        SOUND_MAP.put(9, R.raw.d4);
        SOUND_MAP.put(10, R.raw.e4);
        SOUND_MAP.put(11, R.raw.f4);
        SOUND_MAP.put(12, R.raw.g4);
        SOUND_MAP.put(13, R.raw.a4);
        SOUND_MAP.put(14, R.raw.b4);
        // black keys sounds
        SOUND_MAP.put(15, R.raw.db3);
        SOUND_MAP.put(16, R.raw.eb3);
        SOUND_MAP.put(17, R.raw.gb3);
        SOUND_MAP.put(18, R.raw.ab3);
        SOUND_MAP.put(19, R.raw.bb3);
        SOUND_MAP.put(20, R.raw.db4);
        SOUND_MAP.put(21, R.raw.eb4);
        SOUND_MAP.put(22, R.raw.gb4);
        SOUND_MAP.put(23, R.raw.ab4);
        SOUND_MAP.put(24, R.raw.bb4);
    }

    public AudioSoundPlayer(Context context) {
        this.context = context;
        //this.threadMap = new SparseArray<Integer>();
        this.soundPool = new SoundPool(maxPlaying, AudioManager.STREAM_MUSIC, 0);
        this.soundPool.setOnLoadCompleteListener(this);


    }

    public void playNote(int note) {
        Integer sampleId = loadedSoundCache.get(note);
        if (sampleId == null) {
            try {
                //String path = SOUND_MAP.get(note) + ".mp3";
                //AssetManager assetManager = context.getAssets();
                //AssetFileDescriptor ad = assetManager.openFd(path);
                int sound = this.soundPool.load(this.context, SOUND_MAP.get(note), 1);
                //this.soundPool.setOnLoadCompleteListener();
                this.soundPool.play(sound, volume, volume, priority, no_loop, normal_playback_rate);
                loadedSoundCache.put(note, sound);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            this.soundPool.play(sampleId, volume, volume, priority, no_loop, normal_playback_rate);
        }
    }

    //public boolean isNotePlaying(int note) {
    //    return threadMap.get(note) != null;
    //}

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        if (status == 0) {
            soundPool.play(sampleId, volume, volume, priority, no_loop, normal_playback_rate);
        }
    }

    @Override
    protected void onDestroy() {
        soundPool.release();
        super.onDestroy();
    }
}

//public class AudioSoundPlayer {
//
//    //private SoundPool mySoundPool;
//    private SparseArray<PlayThread> threadMap = null;
//    private Context context;
//    private static final SparseArray<String> SOUND_MAP = new SparseArray<>();
//    public static final int MAX_VOLUME = 100, CURRENT_VOLUME = 90;
//
//    static {
//        // white keys sounds
//        SOUND_MAP.put(1, "c");
//        SOUND_MAP.put(2, "d");
//        SOUND_MAP.put(3, "e");
//        SOUND_MAP.put(4, "note_fa");
//        SOUND_MAP.put(5, "note_sol");
//        SOUND_MAP.put(6, "note_la");
//        SOUND_MAP.put(7, "note_si");
//        SOUND_MAP.put(8, "second_do");
//        SOUND_MAP.put(9, "second_re");
//        SOUND_MAP.put(10, "second_mi");
//        SOUND_MAP.put(11, "second_fa");
//        SOUND_MAP.put(12, "second_sol");
//        SOUND_MAP.put(13, "second_la");
//        SOUND_MAP.put(14, "second_si");
//        // black keys sounds
//        SOUND_MAP.put(15, "do_dies");
//        SOUND_MAP.put(16, "re_dies");
//        SOUND_MAP.put(17, "fa_dies");
//        SOUND_MAP.put(18, "sol_dies");
//        SOUND_MAP.put(19, "la_dies");
//        SOUND_MAP.put(20, "second_dies_do");
//        SOUND_MAP.put(21, "second_dies_re");
//        SOUND_MAP.put(22, "second_dies_fa");
//        SOUND_MAP.put(23, "second_dies_sol");
//        SOUND_MAP.put(24, "second_dies_la");
//    }
//
//    public AudioSoundPlayer(Context context) {
//        this.context = context;
//        threadMap = new SparseArray<>();
//        //this.mySoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
//
//        //SOUND_MAP.put(1, mySoundPool.load(getApplicationContext(),R.raw.c,1))
//    }
//
//    public void playNote(int note) {
//        if (!isNotePlaying(note)) {
//            PlayThread thread = new PlayThread(note);
//            thread.start();
//            threadMap.put(note, thread);
//        }
//    }
//
//    public void stopNote(int note) {
//        PlayThread thread = threadMap.get(note);
//
//        if (thread != null) {
//            threadMap.remove(note);
//        }
//    }
//
//    public boolean isNotePlaying(int note) {
//        return threadMap.get(note) != null;
//    }
//
//    private class PlayThread extends Thread {
//        int note;
//        AudioTrack audioTrack;
//
//        public PlayThread(int note) {
//            this.note = note;
//        }
//
//        @Override
//        public void run() {
//            final MediaPlayer mp;
//
//            try {
//                String path = SOUND_MAP.get(this.note) + ".mp3";
//                AssetManager assetManager = context.getAssets();
//                AssetFileDescriptor ad = assetManager.openFd(path);
//                long fileSize = ad.getLength();
//                int bufferSize = 4096;
//                byte[] buffer = new byte[bufferSize];
//
//                mp = new MediaPlayer();
//                mp.setDataSource(ad.getFileDescriptor(), ad.getStartOffset(), fileSize);
//                ad.close();
//                mp.prepare();
//                mp.start();
//                System.out.print("Run this.");
//            } catch (IllegalArgumentException e) {
//                e.printStackTrace();
//                } catch (IllegalStateException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//            }
////                audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_CONFIGURATION_MONO,
////                        AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);
////
////                float logVolume = (float) (1 - (Math.log(MAX_VOLUME - CURRENT_VOLUME) / Math.log(MAX_VOLUME)));
////                audioTrack.setStereoVolume(logVolume, logVolume);
////
////                audioTrack.play();
////                InputStream audioStream = null;
////                int headerOffset = 0x2C; long bytesWritten = 0; int bytesRead = 0;
////
////                audioStream = assetManager.open(path);
////                audioStream.read(buffer, 0, headerOffset);
////
////                while (bytesWritten < fileSize - headerOffset) {
////                    bytesRead = audioStream.read(buffer, 0, bufferSize);
////                    bytesWritten += audioTrack.write(buffer, 0, bytesRead);
////                }
////
////                audioTrack.stop();
////                audioTrack.release();
////
////            } catch (Exception e) {
////                e.printStackTrace();
////            } finally {
////                if (audioTrack != null) {
////                    audioTrack.release();
////                }
////            }
//        }
//    }
//}
