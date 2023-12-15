package com.videocomment.backend.core.utils;
import com.videocomment.backend.dao.entity.Comment;

import java.util.Comparator;
/**
 * @ClassName OrderDateComparator
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/4 15:56
 * @Version 1.0
 */
public class OrderDateComparator implements Comparator<Comment> {

    @Override
    public int compare(Comment o1, Comment o2) {
        return o1.getCreateTime().compareTo(o2.getCreateTime());
    }
}
