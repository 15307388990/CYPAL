package com.cypal.ming.cypal.bean;

public class LoginEntity {


    /**
     * code : 1
     * msg : success
     * data : {"loginToken":"TOKEN-75B3F49A-F24E-475D-B3DC-4951E712E67E-DAMO","webSocketToken":"MTUzMDczODg5OTEsY2E4OGQ4OGY4NjUyMGVjOTY5ZDE5NzU5MjRmYzZjOTI4MDcwYTNlYjkwM2FhNGQwZDNkNzg0MjYwZjdhMTc1ZjgwYmVkNjY3NTQxYzRiZWYyZDNjZmM4MGFlMzZlZDdkMWYwNjQzZDE2ODY4YzE2NjVlNzc2NzgyYmEyY2U4ZTE="}
     * serverTime : 1553399155222
     */

    public int code;
    public String msg;
    public DataBean data;
    public long serverTime;

    public static class DataBean {
        /**
         * loginToken : TOKEN-75B3F49A-F24E-475D-B3DC-4951E712E67E-DAMO
         * webSocketToken : MTUzMDczODg5OTEsY2E4OGQ4OGY4NjUyMGVjOTY5ZDE5NzU5MjRmYzZjOTI4MDcwYTNlYjkwM2FhNGQwZDNkNzg0MjYwZjdhMTc1ZjgwYmVkNjY3NTQxYzRiZWYyZDNjZmM4MGFlMzZlZDdkMWYwNjQzZDE2ODY4YzE2NjVlNzc2NzgyYmEyY2U4ZTE=
         */

        public String loginToken;
        public String webSocketToken;
    }
}
