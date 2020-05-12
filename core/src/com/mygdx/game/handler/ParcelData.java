package com.mygdx.game.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * handler 之间 传递的参数
 */
public class ParcelData {
    public static final String TARGET_ANY = "any";


    // 上一個handler的名称
    private String prevHandlerName = "";
    private String mTargetHandlerName = "";
    // 传递到此handler的原因，上一个handler为什么指向自己，常量指定
    private int mJumpReason = -1;
    // 携带参数
    private Map<String, Object> map = new HashMap<>();

    private ParcelData() {
    }

    public String getPrevHandlerName() {
        return prevHandlerName;
    }

    public String getTargetHandlerName() {
        return mTargetHandlerName;
    }

    public int getJumpReason() {
        return mJumpReason;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public static class Builder {
        public static ParcelData DEFAULT = new Builder().build();
        private String prevHandlerName = "";
        private String mTargetHandlerName = TARGET_ANY;
        private int mJumpReason = -1;
        private Map<String, Object> map = new HashMap<>();

        public Builder setPrevHandlerName(String prevHandlerName) {
            this.prevHandlerName = prevHandlerName;
            return this;
        }

        public Builder setTargetHandlerName(String mTargetHandlerName) {
            this.mTargetHandlerName = mTargetHandlerName;
            return this;
        }

        public Builder setJumpReason(int mJumpReason) {
            this.mJumpReason = mJumpReason;
            return this;
        }

        public Builder add(String key, Object value) {
            map.put(key, value);
            return this;
        }

        public ParcelData build() {
            ParcelData parcelData = new ParcelData();
            parcelData.prevHandlerName = prevHandlerName;
            parcelData.mTargetHandlerName = mTargetHandlerName;
            parcelData.mJumpReason = mJumpReason;
            parcelData.map = map;
            return parcelData;
        }
    }
}
