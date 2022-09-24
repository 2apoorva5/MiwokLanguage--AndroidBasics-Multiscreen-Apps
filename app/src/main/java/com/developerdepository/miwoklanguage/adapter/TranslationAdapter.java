package com.developerdepository.miwoklanguage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.developerdepository.miwoklanguage.R;
import com.developerdepository.miwoklanguage.model.Translation;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class TranslationAdapter extends ArrayAdapter<Translation> {

    private final int colorResource;

    public TranslationAdapter(@NonNull Context context, @NonNull ArrayList<Translation> translations, int colorResource) {
        super(context, 0, translations);
        this.colorResource = colorResource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.translation_list_item, parent, false);
        }

        Translation translation = getItem(position);

        ConstraintLayout itemContainer = listItemView.findViewById(R.id.item_container);
        ShapeableImageView imgItem = listItemView.findViewById(R.id.img_item);
        MaterialTextView miwokTranslation = listItemView.findViewById(R.id.miwok_translation);
        MaterialTextView englishTranslation = listItemView.findViewById(R.id.english_translation);

        itemContainer.setBackgroundColor(ContextCompat.getColor(getContext(), colorResource));

        if (translation.hasImage()) {
            imgItem.setImageResource(translation.getImgResource());
            imgItem.setVisibility(View.VISIBLE);
        } else {
            imgItem.setVisibility(View.GONE);
        }
        miwokTranslation.setText(translation.getMiwokTranslation());
        englishTranslation.setText(translation.getEnglishTranslation());

        return listItemView;
    }
}
