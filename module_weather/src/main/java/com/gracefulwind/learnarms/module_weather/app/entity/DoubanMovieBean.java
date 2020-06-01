package com.gracefulwind.learnarms.module_weather.app.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoubanMovieBean {

    @SerializedName("count")
    public int count;
    @SerializedName("start")
    public int start;
    @SerializedName("total")
    public int total;
    @SerializedName("subjects")
    public List<Subject> subjects;
    @SerializedName("title")
    public String title;


    public class Subject{

        @SerializedName("rating")
        public Rating rating;
        @SerializedName("genres")
        public List<String> genres;
        @SerializedName("title")
        public String title;
        @SerializedName("durations")
        public List<String> durations;
        @SerializedName("collect_count")
        public long collectCount;

        public class Rating{

            @SerializedName("max")
            public int max;
            @SerializedName("average")
            public float average;
            @SerializedName("details")
            public Details details;
            @SerializedName("stars")
            public String stars;
            @SerializedName("min")
            public int min;


            public class Details{
                @SerializedName("1")
                public float data1;
                @SerializedName("2")
                public float data2;
                @SerializedName("3")
                public float data3;
                @SerializedName("4")
                public float data4;
                @SerializedName("5")
                public float data5;
            }
        }
    }

}
