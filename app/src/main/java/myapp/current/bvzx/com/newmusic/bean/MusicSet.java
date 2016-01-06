package myapp.current.bvzx.com.newmusic.bean;

/**
 * Created by wuhao on 2016/1/4.
 */
public class MusicSet {
    public String status;
    public String code;
    public MusicOutter data;

    public class MusicOutter{
        public MusicWrapper data;
    }
    public class MusicWrapper{
         public String count;
         public String totalPages;
         public String s;
         public String nowPage;
         public MusicData[] list;
    }

    public class MusicData{
        public String songId;
        public String songName;
        public String userName;
        public String albumPic;
        public String albumName;
        public String songUrl;

    }



















}
