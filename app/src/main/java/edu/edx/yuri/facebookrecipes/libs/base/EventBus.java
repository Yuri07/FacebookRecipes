package edu.edx.yuri.facebookrecipes.libs.base;

/**
 * Created by yuri_ on 23/11/2017.
 */

public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
