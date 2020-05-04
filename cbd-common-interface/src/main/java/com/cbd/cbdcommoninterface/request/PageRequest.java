package com.cbd.cbdcommoninterface.request;

<<<<<<< HEAD

=======
>>>>>>> zhq
import java.io.Serializable;

/**
 * 分页请求
 */
public class PageRequest implements Serializable {
    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
