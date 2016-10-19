package com.example.jpushdemo.bean;

/**
 * Created by turbo on 2016/10/17.
 */
public class User {


    /**
     * code : 200
     * message : 登录成功
     * data : {"username":"war3","password":"war3","uid":"85"}
     */

    private int code;
    private String message;
    /**
     * username : war3
     * password : war3
     * uid : 85
     */

    private DataEntity data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private String username;
        private String password;
        private String uid;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
