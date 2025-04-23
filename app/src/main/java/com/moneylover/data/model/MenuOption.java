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

    public MenuOption(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public MenuOption(String title) {
        this.title = title;
    }
}
