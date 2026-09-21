package se.liu.emilo739.tetris;

import java.awt.*;

public interface FallHandler
{
    public boolean hasCollision(Board board, Point originalPosition);
    public String getDescription();
}
