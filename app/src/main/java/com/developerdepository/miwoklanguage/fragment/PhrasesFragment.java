package com.developerdepository.miwoklanguage.fragment;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.developerdepository.miwoklanguage.R;
import com.developerdepository.miwoklanguage.adapter.TranslationAdapter;
import com.developerdepository.miwoklanguage.model.Translation;

import java.util.ArrayList;

public class PhrasesFragment extends Fragment {

    private ListView listView;

    /**
     * Handles playback of all the sound files
     */
    private MediaPlayer mediaPlayer;

    /**
     * Handles audio focus when playing a sound file
     */
    private AudioManager audioManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_phrases, container, false);

        initViews(layout);

        /* Create and setup the AudioManager to request audio focus */
        audioManager = (AudioManager) requireActivity().getSystemService(Context.AUDIO_SERVICE);

        /* Create an ArrayList of Phrase Translation - Translation is a model class */
        ArrayList<Translation> phraseTranslations = new ArrayList<>();
        phraseTranslations.add(new Translation(
                R.raw.phrase_where_are_you_going, "minto wuksus", "Where are you going?"));
        phraseTranslations.add(new Translation(
                R.raw.phrase_what_is_your_name, "tinnә oyaase'nә", "What is your name?"));
        phraseTranslations.add(new Translation(
                R.raw.phrase_my_name_is, "oyaaset...", "My name is..."));
        phraseTranslations.add(new Translation(
                R.raw.phrase_how_are_you_feeling, "michәksәs?", "How are you feeling?"));
        phraseTranslations.add(new Translation(
                R.raw.phrase_im_feeling_good, "kuchi achit", "I’m feeling good."));
        phraseTranslations.add(new Translation(
                R.raw.phrase_are_you_coming, "әәnәs'aa?", "Are you coming?"));
        phraseTranslations.add(new Translation(
                R.raw.phrase_yes_im_coming, "hәә’ әәnәm", "Yes, I’m coming."));
        phraseTranslations.add(new Translation(
                R.raw.phrase_im_coming, "әәnәm", "I’m coming."));
        phraseTranslations.add(new Translation(
                R.raw.phrase_lets_go, "yoowutis", "Let’s go."));
        phraseTranslations.add(new Translation(
                R.raw.phrase_come_here, "әnni'nem", "Come here."));

        /* Custom Adapter Class to populate the ListView */
        TranslationAdapter translationAdapter = new TranslationAdapter(requireActivity(), phraseTranslations, R.color.colorCategoryPhrases);

        /* Set the adapter to the ListView */
        listView.setAdapter(translationAdapter);

        /* Play miwok translation sound on Click of ListView item */
        listView.setOnItemClickListener((parent, view, position, id) -> {
            /* Release the media player if it currently exists because we are about to play a different sound file */
            releaseMediaPlayer();

            Translation translation = translationAdapter.getItem(position);

            /* Request audio focus in order to play the audio file. The app needs to play a
               short audio file, so we will request audio focus with a short amount of time
               with AUDIOFOCUS_GAIN_TRANSIENT.
            */
            int audioFocusRequest = audioManager.requestAudioFocus(
                    audioFocusChangeListener,
                    AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

            if (audioFocusRequest == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                /* We have audio focus now. */

                /* Create and setup the MediaPlayer for the audio resource associated with the current word */
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(getActivity(), translation.getMiwokAudio());
                }

                /* Start the audio file */
                mediaPlayer.start();

                /* Setup a listener on the media player, so that we can stop and release the
                   media player once the sound has finished playing.
                */
                mediaPlayer.setOnCompletionListener(mediaPlayerCompletionListener);
            }
        });

        return layout;
    }

    /**
     * Views' hook to the layout
     */
    private void initViews(View layout) {
        listView = layout.findViewById(R.id.listview);
    }

    /**
     * This listener gets triggered when the MediaPlayer has completed playing the audio file.
     */
    private final MediaPlayer.OnCompletionListener mediaPlayerCompletionListener = mediaPlayer -> releaseMediaPlayer();

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private final AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = focusChange -> {
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
            // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
            // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
            // our app is allowed to continue playing sound but at a lower volume. We'll treat
            // both cases the same way because our app is playing short sound files.

            // Pause playback and reset player to the start of the file. That way, we can
            // play the word from the beginning when we resume playback.
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
            mediaPlayer.start();
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            // The AUDIOFOCUS_LOSS case means we've lost audio focus and
            // Stop playback and clean up resources
            releaseMediaPlayer();
        }
    };

    /**
     * Release the media player
     */
    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;

            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}