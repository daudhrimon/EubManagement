package com.polok.eubmanagement.base.event;

import androidx.lifecycle.Observer;

public final class Event<T> {
    public interface Handler<T> {
        void handle(T content);
    }

    public static class EventObserver<T> implements Observer<Event<T>> {
        private final Event.Handler<T> handler;

        public EventObserver(Handler<T> handler) {
            this.handler = handler;
        }

        @Override
        public void onChanged(Event<T> event) {
            if (event != null) {
                event.handle(handler);
            }
        }
    }

    private boolean hasBeenHandled = false;
    private final T content;

    public Event(T content) {
        this.content = content;
    }

    private void handle(Event.Handler<T> handler) {
        if (!hasBeenHandled) {
            hasBeenHandled = true;
            handler.handle(content);
        }
    }
}
