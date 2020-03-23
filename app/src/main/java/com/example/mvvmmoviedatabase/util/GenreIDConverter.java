package com.example.mvvmmoviedatabase.util;

import android.util.Log;

import androidx.room.TypeConverter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenreIDConverter {


    @TypeConverter
    public List<Integer> stringToIntList(String value){
        //Log.d("TAG_H",value);

        List<String> preGenreID = Arrays.asList(value.split(","));
        List<Integer> genreID = new ArrayList<>();

        for(int i = 0; i<preGenreID.size(); i++){
           //Log.d("TAG_H",preGenreID.get(i));
            if(!preGenreID.get(i).equals(""))
                genreID.add(Integer.parseInt(preGenreID.get(i)));
        }
        return genreID;
    }

    @TypeConverter
    public String intListToString (List<Integer> genreID){
        String value = "";
        if(genreID.size()!=0) {
            for (int i = 0; i < genreID.size() - 1; i++) {
                value += genreID.get(i) + ",";
            }
            value += genreID.get(genreID.size() - 1);
        }
        return value;
    }

}
