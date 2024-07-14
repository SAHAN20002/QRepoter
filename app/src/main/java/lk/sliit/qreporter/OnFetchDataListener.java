package lk.sliit.qreporter;

import java.util.List;

import lk.sliit.qreporter.Models.NewsHeadlines;

public interface OnFetchDataListener<NewsApiResponse> {
    void onFetchData(List<NewsHeadlines> list,String message);
    void  onError(String message);
}
