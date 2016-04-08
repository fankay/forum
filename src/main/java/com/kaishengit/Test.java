package com.kaishengit;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.kaishengit.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        User user1 = new User();
        user1.setId(1);

        User user2 = new User();
        user1.setId(2);

        User user3 = new User();
        user1.setId(3);

        List<User> list = Lists.newArrayList(user1,user2,user3);

        List<Integer> idList = Lists.newArrayList(Collections2.transform(list, new Function<User, Integer>() {
            @Override
            public Integer apply(User user) {
                return user.getId();
            }
        }));

        List<User> userList = Lists.newArrayList(Collections2.filter(list, new Predicate<User>() {
            @Override
            public boolean apply(User user) {
                return user.getId() > 1;
            }
        }));


        /*List<Integer> idList = Lists.newArrayList();

        for(User user : list) {
            idList.add(user.getId())
        }*/


    }

}
