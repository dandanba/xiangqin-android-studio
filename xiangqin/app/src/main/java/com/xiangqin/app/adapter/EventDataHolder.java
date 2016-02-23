package com.xiangqin.app.adapter;

import com.xiangqin.app.model.Event;

/**
 * Created by dandanba on 11/16/15.
 */
public class EventDataHolder extends BaseDataHolder {
    public EventDataHolder(int type) {
        super(type);
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    private Event event;

}