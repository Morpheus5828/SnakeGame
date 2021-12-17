package com.example.snakegame;

import javafx.scene.paint.Color;

public interface Square {
    public void changeColor(Color color);
    public void setNeighbor(Square neighbor);
}
