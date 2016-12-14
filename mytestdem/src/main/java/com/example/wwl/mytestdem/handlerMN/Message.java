package com.example.wwl.mytestdem.handlerMN;


/**
 * Created by wwl on 2016/12/13.
 */

public class Message {

    Handler target;
    public Object obj;
    public int what;

    @Override
    public String toString() {
        return "what:" + what + " obj:" + obj.toString();
    }
}
