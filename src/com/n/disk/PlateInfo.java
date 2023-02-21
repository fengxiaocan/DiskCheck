package com.n.disk;

import com.n.disk.common.FileUtils;
import com.n.disk.common.StringUtil;

import java.io.File;

public class PlateInfo {
    private String path;
    private String name;
    private long totalSpace;
    private String totalSpaceStr;
    private String msg;

    public PlateInfo(String path) {
        this.path = path;
        File file = new File(path);
        name = file.getName();
        totalSpace = file.getTotalSpace();
        totalSpaceStr = StringUtil.formatFileSize(totalSpace);
        msg="U盘:"+name+" ("+totalSpaceStr+")";
    }

    public String getPath() {
        return path;
    }

    public PlateInfo setPath(String path) {
        this.path = path;
        return this;
    }

    public String getName() {
        return name;
    }

    public PlateInfo setName(String name) {
        this.name = name;
        return this;
    }

    public long getTotalSpace() {
        return totalSpace;
    }

    public PlateInfo setTotalSpace(long totalSpace) {
        this.totalSpace = totalSpace;
        return this;
    }

    public String getTotalSpaceStr() {
        return totalSpaceStr;
    }

    public PlateInfo setTotalSpaceStr(String totalSpaceStr) {
        this.totalSpaceStr = totalSpaceStr;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public PlateInfo setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public String toString() {
        return msg;
    }
}
