package me.welkinbai.crawleralthing.path;

import java.util.Objects;

public enum PathType {
    RAW_PATH(0, "未知类型的地址"),
    LIST_PATH(1, "列表式页面地址");
    private int code;
    private String msg;

    PathType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static PathType valueOf(Integer code) {
        for (PathType pathType : values()) {
            if (Objects.equals(pathType.code, code)) {
                return pathType;
            }
        }
        throw new IllegalArgumentException("unknown code:" + code);
    }

    public static PathType valueOfOrNull(Integer code) {
        for (PathType pathType : values()) {
            if (Objects.equals(pathType.code, code)) {
                return pathType;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
