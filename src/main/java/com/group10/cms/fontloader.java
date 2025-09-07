package com.group10.cms;

import javafx.scene.text.Font;

public class fontloader {
    public fontloader() {
        String[] fonts = {"Raleway-BoldItalic.ttf","StoryScript-Regular.ttf","Asimovian-Regular.ttf"};
        for (String f : fonts) {
            Font font = Font.loadFont(getClass().getResourceAsStream("fonts/"+f), 10);
            System.out.println(font.getName());
        }
    }
}
