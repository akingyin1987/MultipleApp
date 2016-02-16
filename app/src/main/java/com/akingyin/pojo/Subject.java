package com.akingyin.pojo;

import java.util.List;

/**
 * Created by zlcd on 2016/2/16.
 */
public class Subject {


    /**
     * max : 10
     * average : 7.3
     * stars : 40
     * min : 0
     */

    private RatingEntity rating;
    /**
     * rating : {"max":10,"average":7.3,"stars":"40","min":0}
     * genres : ["剧情","爱情","科幻"]
     * collect_count : 157197
     * casts : [{"avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/805.jpg","large":"https://img3.doubanio.com/img/celebrity/large/805.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/805.jpg"},"alt":"http://movie.douban.com/celebrity/1274235/","id":"1274235","name":"邓超"},{"avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/3083.jpg","large":"https://img1.doubanio.com/img/celebrity/large/3083.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/3083.jpg"},"alt":"http://movie.douban.com/celebrity/1274317/","id":"1274317","name":"罗志祥"},{"avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/47020.jpg","large":"https://img3.doubanio.com/img/celebrity/large/47020.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/47020.jpg"},"alt":"http://movie.douban.com/celebrity/1274494/","id":"1274494","name":"张雨绮"}]
     * title : 美人鱼
     * original_title : 美人鱼
     * subtype : movie
     * directors : [{"avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/47421.jpg","large":"https://img3.doubanio.com/img/celebrity/large/47421.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/47421.jpg"},"alt":"http://movie.douban.com/celebrity/1048026/","id":"1048026","name":"周星驰"}]
     * year : 2016
     * images : {"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p2318777840.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2318777840.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p2318777840.jpg"}
     * alt : http://movie.douban.com/subject/19944106/
     * id : 19944106
     */

    private int collect_count;
    private String title;
    private String original_title;
    private String subtype;
    private String year;
    /**
     * small : https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p2318777840.jpg
     * large : https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p2318777840.jpg
     * medium : https://img3.doubanio.com/view/movie_poster_cover/spst/public/p2318777840.jpg
     */

    private ImagesEntity images;
    private String alt;
    private String id;
    private List<String> genres;
    /**
     * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/805.jpg","large":"https://img3.doubanio.com/img/celebrity/large/805.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/805.jpg"}
     * alt : http://movie.douban.com/celebrity/1274235/
     * id : 1274235
     * name : 邓超
     */

    private List<CastsEntity> casts;
    /**
     * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/47421.jpg","large":"https://img3.doubanio.com/img/celebrity/large/47421.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/47421.jpg"}
     * alt : http://movie.douban.com/celebrity/1048026/
     * id : 1048026
     * name : 周星驰
     */

    private List<DirectorsEntity> directors;

    public void setRating(RatingEntity rating) {
        this.rating = rating;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setImages(ImagesEntity images) {
        this.images = images;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setCasts(List<CastsEntity> casts) {
        this.casts = casts;
    }

    public void setDirectors(List<DirectorsEntity> directors) {
        this.directors = directors;
    }

    public RatingEntity getRating() {
        return rating;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getYear() {
        return year;
    }

    public ImagesEntity getImages() {
        return images;
    }

    public String getAlt() {
        return alt;
    }

    public String getId() {
        return id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<CastsEntity> getCasts() {
        return casts;
    }

    public List<DirectorsEntity> getDirectors() {
        return directors;
    }

    public static class RatingEntity {
        private int max;
        private double average;
        private String stars;
        private int min;

        public void setMax(int max) {
            this.max = max;
        }

        public void setAverage(double average) {
            this.average = average;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }

        public double getAverage() {
            return average;
        }

        public String getStars() {
            return stars;
        }

        public int getMin() {
            return min;
        }
    }

    public static class ImagesEntity {
        private String small;
        private String large;
        private String medium;

        public void setSmall(String small) {
            this.small = small;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getSmall() {
            return small;
        }

        public String getLarge() {
            return large;
        }

        public String getMedium() {
            return medium;
        }
    }

    public static class CastsEntity {
        /**
         * small : https://img3.doubanio.com/img/celebrity/small/805.jpg
         * large : https://img3.doubanio.com/img/celebrity/large/805.jpg
         * medium : https://img3.doubanio.com/img/celebrity/medium/805.jpg
         */

        private AvatarsEntity avatars;
        private String alt;
        private String id;
        private String name;

        public void setAvatars(AvatarsEntity avatars) {
            this.avatars = avatars;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public AvatarsEntity getAvatars() {
            return avatars;
        }

        public String getAlt() {
            return alt;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public static class AvatarsEntity {
            private String small;
            private String large;
            private String medium;

            public void setSmall(String small) {
                this.small = small;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            public String getSmall() {
                return small;
            }

            public String getLarge() {
                return large;
            }

            public String getMedium() {
                return medium;
            }
        }
    }

    public static class DirectorsEntity {
        /**
         * small : https://img3.doubanio.com/img/celebrity/small/47421.jpg
         * large : https://img3.doubanio.com/img/celebrity/large/47421.jpg
         * medium : https://img3.doubanio.com/img/celebrity/medium/47421.jpg
         */

        private AvatarsEntity avatars;
        private String alt;
        private String id;
        private String name;

        public void setAvatars(AvatarsEntity avatars) {
            this.avatars = avatars;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public AvatarsEntity getAvatars() {
            return avatars;
        }

        public String getAlt() {
            return alt;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public static class AvatarsEntity {
            private String small;
            private String large;
            private String medium;

            public void setSmall(String small) {
                this.small = small;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            public String getSmall() {
                return small;
            }

            public String getLarge() {
                return large;
            }

            public String getMedium() {
                return medium;
            }
        }
    }
}
