package com.example.akafist.models;

public class AudioModel {
    private final String audioName;
    private final String audioLink;

    public AudioModel(String audioName, String audioLink) {
        this.audioName = audioName;
        this.audioLink = audioLink;
    }

    public String getAudioName() {
        return audioName;
    }

    public String getAudioLink() {
        return audioLink;
    }
}
