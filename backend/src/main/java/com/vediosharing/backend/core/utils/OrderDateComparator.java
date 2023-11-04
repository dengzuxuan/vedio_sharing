package com.vediosharing.backend.core.utils;
import com.vediosharing.backend.dao.entity.Comment;
import org.springframework.core.annotation.Order;

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
