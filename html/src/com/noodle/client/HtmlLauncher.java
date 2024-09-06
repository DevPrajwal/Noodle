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
                GwtApplicationConfiguration config = new GwtApplicationConfiguration(true);
                config.padHorizontal = 0;
                config.padVertical = 0;
                return config;
                // Fixed size application:
                //return new GwtApplicationConfiguration(Window.getClientWidth(), Window.getClientWidth()*1080/1920);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new MainGame();
        }
}