package rasterizers;

import models.Line;
import rasters.Raster;

import java.awt.*;

public class LineRasterizerTrivial implements Rasterizer {

    private Color color;
    private Raster raster;

    public LineRasterizerTrivial(Color color, Raster raster) {
        this.color = color;
        this.raster = raster;
    }

    @Override
    public void setColor(Color color) {
        color = color;
    }

    @Override
    public void rasterize(Line line) {
        int x1 = line.getPoint1().getX();
        int y1 = line.getPoint1().getY();
        int x2 = line.getPoint2().getX();
        int y2 = line.getPoint2().getY();

        float k = (float) (y2 - y1) / (x2 - x1);
        float q = y1 - (k * x1);

        // TODO rozdělit podle K
        // TODO ošetřit mimo hranice rastru

        for (int x = x1; x <= x2; x++) {
            int y = Math.round(k * x + q);

            raster.setPixel(x, y, line.getColor().getRGB());
        }
    }
}
