package com.example.mybreakpointdownload.db;

import com.example.mybreakpointdownload.bean.ThreadInfo;

import java.util.List;

public interface ThreadDAO {

    public void insertThead(ThreadInfo info);
    public void deleteThead(String url,int thread_id);
    public void updateThread(String url,int thead_id,long finishe);
    public List<ThreadInfo> queryTheads(String url);
    public boolean isExists(String url,int threadId);

}
