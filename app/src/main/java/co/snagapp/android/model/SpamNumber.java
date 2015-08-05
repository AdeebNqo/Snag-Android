package co.snagapp.android.model;

import android.os.Bundle;
import android.os.Message;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by zola on 2015/07/21.
 */
public class SpamNumber {
    private Collection<Message> msgs;
    private String number;

    public Collection<Message> getMsgs() {
        return msgs;
    }

    public void addMsg(Message msg) {
        this.msgs.add(msg);
    }

    public Message getMsgByKey(String key){
        Message returnMsg = null;
        Iterator<Message> msgIt = msgs.iterator();
        while(msgIt.hasNext()){
            Message msg = msgIt.next();
            if (msg == null){
                msgIt.remove();
            }else{
                Bundle dataBundle = msg.getData();
                if (dataBundle.containsKey(key)){
                    if (dataBundle.get(key).equals(key)){
                        returnMsg = msg;
                        break;
                    }
                }
            }
        }
        return returnMsg;
    }

    public void clearMsgs(){
        this.msgs.clear();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
