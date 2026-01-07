package Fillers;

import models.Point;
import rasters.Raster;

import java.awt.*;

public class BasicFiller implements Filler {

    private Raster raster;

    public BasicFiller(Raster raster) {
        this.raster = raster;
    }

    @Override
    public void fill(Point click, Color fillColor) {
        int baseColor = raster.getPixel(click.getX(), click.getY());

        recursiveFill(click, fillColor.getRGB(), baseColor);
    }


    private void recursiveFill(Point click, int fillColor, int baseColor) {
        if (baseColor != raster.getPixel(click.getX(), click.getY())) {
            return;
        }

        raster.setPixel(click.getX(), click.getY(), fillColor);

        Point newPoint = new Point(click.getX() + 1, click.getY());
        recursiveFill(newPoint, fillColor, baseColor);
        newPoint = new Point(click.getX() - 1, click.getY());
        recursiveFill(newPoint, fillColor, baseColor);
        newPoint = new Point(click.getX(), click.getY() + 1);
        recursiveFill(newPoint, fillColor, baseColor);
        newPoint = new Point(click.getX(), click.getY() - 1);
        recursiveFill(newPoint, fillColor, baseColor);
    }

}
