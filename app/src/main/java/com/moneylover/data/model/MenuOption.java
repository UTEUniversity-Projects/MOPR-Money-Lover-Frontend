package com.moneylover.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MenuOption {
    private int icon;
    private String title;
    private boolean selected;
    private int type;

    public MenuOption(int icon, String title, int type) {
        this.icon = icon;
        this.title = title;
        this.type = type;
    }

    public MenuOption(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public MenuOption(String title) {
        this.title = title;
    }
}
