package br.com.innovaro.gd.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import br.com.innovaro.gd.MyUI;


public class DashboardEventBus implements SubscriberExceptionHandler {

    private final EventBus eventBus = new EventBus(this);

    public static void post(final Object event) {
        MyUI.getDashboardEventbus().eventBus.post(event);
    }

    public static void register(final Object object) {
    	MyUI.getDashboardEventbus().eventBus.register(object);
    }

    public static void unregister(final Object object) {
    	MyUI.getDashboardEventbus().eventBus.unregister(object);
    }

    @Override
    public final void handleException(final Throwable exception,
            final SubscriberExceptionContext context) {
        exception.printStackTrace();
    }
}
