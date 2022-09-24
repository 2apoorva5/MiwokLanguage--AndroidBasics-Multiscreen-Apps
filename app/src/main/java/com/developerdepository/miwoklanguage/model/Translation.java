package com.developerdepository.miwoklanguage.model;

public class Translation {
    private int imgResource = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

    private final int miwokAudio;
    private final String miwokTranslation;
    private final String englishTranslation;

    public Translation(int miwokAudio, String miwokTranslation, String englishTranslation) {
        this.miwokAudio = miwokAudio;
        this.miwokTranslation = miwokTranslation;
        this.englishTranslation = englishTranslation;
    }

    public Translation(int imgResource, int miwokAudio, String miwokTranslation, String englishTranslation) {
        this.imgResource = imgResource;
        this.miwokAudio = miwokAudio;
        this.miwokTranslation = miwokTranslation;
        this.englishTranslation = englishTranslation;
    }

    public int getImgResource() {
        return imgResource;
    }

    public int getMiwokAudio() {
        return miwokAudio;
    }

    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    public String getEnglishTranslation() {
        return englishTranslation;
    }

    public boolean hasImage() {
        return imgResource != NO_IMAGE_PROVIDED;
    }
}
