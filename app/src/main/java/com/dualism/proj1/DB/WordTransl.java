package com.dualism.proj1.DB;

/**
 * Created by Duelist on 26.03.2017.
 */

public class WordTransl {
    int id;
    String word;
    String translation_value;

    public WordTransl(){
    }

    public WordTransl(int id, String word, String translation_value){
        this.id = id;
        this.word = word;
        this.translation_value = translation_value;
    }

    public WordTransl(String word, String translation_value){
        this.word = word;
        this.translation_value = translation_value;
    }

    public int getID(){
        return this.id;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getWord(){
        return this.word;
    }

    public void setWord(String word){
        this.word = word;
    }

    public String getTranslation_value(){
        return this.translation_value;
    }

    public void setTranslation_value(String translation_value){
        this.translation_value = translation_value;
    }
}
