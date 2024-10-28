package engine;

import java.awt.image.DataBufferInt;

public class Renderer {

    private int pixelW, pixelH;
    private int[] pixels;

    public Renderer(Engine engine) {

        pixelH = engine.getHeight();
        pixelW = engine.getWidth();

        //TODO: Allows for pixel manipulation in the image
        pixels = ((DataBufferInt)engine.getWindow().getImage().getRaster().getDataBuffer()).getData();

    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0xff000000;
        }
    }

    public void setPixel(int x, int y, int color) {
        if(((x < 0 || x >= pixelW) || (y < 0 || y >= pixelH)) || color == 0x00000000) {
            return;
        }
        pixels[y * pixelW + x] = color;
    }

}
