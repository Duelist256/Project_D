package com.dualism.proj1;

/**
 * Created by Duelist on 21.03.2017.
 */

public class WordTranslation {
    private String word;
    private String translation;

    public WordTranslation(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
