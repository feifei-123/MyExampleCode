package com.example.mybreakpointdownload.bean;

public class ThreadInfo {



    private int id;
    private String url;
    private long start;
    private long end;
    private long finished;

    public ThreadInfo() {
        super();
    }

    /**
     * @param id       綫程的ID
     * @param url      下載文件的網絡地址
     * @param start    綫程下載的開始位置
     * @param end      綫程下載的結束位置
     * @param finished 綫程已經下載到哪個位置
     */
    public ThreadInfo(int id, String url, long start, long end, long finished) {
        super();
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        String result = "id:"+id+",url:"+url+",start:"+start+",end:"+end+",finished:"+finished;
        return result;
    }
}
