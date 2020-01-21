package com.xingguang.sinanya.system;

import com.forte.qqrobot.listener.result.ListenResult;
import com.forte.qqrobot.listener.result.ListenResultImpl;

public interface MessagesListenResult {
    ListenResult MSG_IGNORE = ListenResultImpl.result(1, null, true, false, false, null);
    ListenResult MSG_INTERCEPT = ListenResultImpl.result(1, null, true, true, true, null);
}
