package com.noodle.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.noodle.MainGame;
import com.google.gwt.user.client.Window;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                // Resizable application, uses available space in browser
                //return new GwtApplicationConfiguration(true);
                // Fixed size application:
                return new GwtApplicationConfiguration(Window.getClientWidth()*97/100, Window.getClientWidth()*97*1080/1920/100);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new MainGame();
        }
}