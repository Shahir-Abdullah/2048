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
    private static final int QUALITY = 0; // Not used by the framework
    private static final float VOLUME = 1f;
    private static final int PRIORITY_SLIDE = 0;
    private static final int PRIORITY_SCORE_LOW = 1;
    private static final int PRIORITY_SCORE_HIGH = 2;
    private static final int LOOP = 0;
    private static final float RATE = 1f;

    private static SoundManager sInstance = null;

    private SoundPool mSoundPool = null;
    private HashMap<Sound, Integer> mSoundMap = null;

    private SoundManager(Context context) {

        if (mSoundPool == null) {

            // NOTE: If the device mismatches the audio sample rate of the provided sound
            // files, then "AUDIO_OUTPUT_FLAG_FAST denied by client" entries can be logged.
            // Just ignore them.

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

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
                mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, QUALITY);
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

        // Just make a call to #getInstance() in order to get this manager started
        getInstance(context);
    }

    public void play(Sound sound) {

        if (mSoundMap.containsKey(sound))
            mSoundPool.play(mSoundMap.get(sound), VOLUME, VOLUME, sound.getPriority(), LOOP, RATE);
    }

    public void onPause() {

        mSoundPool.release();
        mSoundPool = null;
        mSoundMap = null;
        sInstance = null; // Dirty, but working
    }

    private void loadSounds(Context context) {

        mSoundMap = new HashMap<>();
        mSoundMap.put(Sound.SLIDE,
                mSoundPool.load(context, R.raw.slide, PRIORITY_SLIDE));
        mSoundMap.put(Sound.SCORE_LOW,
                mSoundPool.load(context, R.raw.score_low, PRIORITY_SCORE_LOW));
        mSoundMap.put(Sound.SORE_HIGH,
                mSoundPool.load(context, R.raw.score_high, PRIORITY_SCORE_HIGH));
    }

    public enum Sound {

        SLIDE(PRIORITY_SLIDE),
        SORE_HIGH(PRIORITY_SCORE_HIGH),
        SCORE_LOW(PRIORITY_SCORE_LOW),;

        private final int mPriority;

        Sound(int priority) {
            mPriority = priority;
        }

        public int getPriority() {
            return mPriority;
        }
    }
}
