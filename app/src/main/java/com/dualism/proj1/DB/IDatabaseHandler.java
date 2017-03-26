package com.dualism.proj1.DB;


import java.util.List;

/**
 * Created by Duelist on 26.03.2017.
 */

public interface IDatabaseHandler {
    void addWordTransl(WordTransl wordTransl);
    WordTransl getWordTransl(int id);
    List<WordTransl> getAllWordTransls();
    int getWordTranslsCount();
    int updateWordTransl(WordTransl wordTransl);
    void deleteWordTransl(WordTransl wordTransl);
    void deleteAll();
}
