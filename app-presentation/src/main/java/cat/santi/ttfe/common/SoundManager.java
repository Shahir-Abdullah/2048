package cat.santi.ttfe.common;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.HashMap;

import cat.santi.ttfe.R;

public class SoundManager {

    private static final int MAX_STREAMS = 2;

    private static SoundManager sInstance = null;

    private SoundPool mSoundPool = null;
    private HashMap<Sound, Integer> mSoundMap = null;

    private SoundManager(Context context) {

        if (mSoundPool == null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                // NOTE: If the device mismatches the audio sample rate of the provided sound
                // files, then "AUDIO_OUTPUT_FLAG_FAST denied by client" entries can be logged.
                // Just ignore them.

                final AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build();
                mSoundPool = new SoundPool.Builder()
                        .setMaxStreams(MAX_STREAMS)
                        .setAudioAttributes(audioAttributes)
                        .build();
            } else {

                //noinspection deprecation
                mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
            }
            loadSounds(context);
        }
    }

    public static synchronized SoundManager getInstance(Context context) {

        if (sInstance == null)
            sInstance = new SoundManager(context);
        return sInstance;
    }

    public static synchronized void onResume(Context context) {

        getInstance(context);
    }

    public void play(Sound sound) {

        if (mSoundMap.containsKey(sound))
            mSoundPool.play(mSoundMap.get(sound), 1f, 1f, 0, 0, 1f);
    }

    public void onPause() {

        mSoundPool.release();
        mSoundPool = null;
        mSoundMap = null;
        sInstance = null; // Dirty, but working
    }

    private void loadSounds(Context context) {

        mSoundMap = new HashMap<>();
        mSoundMap.put(Sound.SLIDE, mSoundPool.load(context, R.raw.slide, 0));
    }

    public enum Sound {

        SLIDE,
    }
}
