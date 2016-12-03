package com.iqgames.sudoku.input;

import com.iqgames.sudoku.common.Loggable;
import com.iqgames.sudoku.data.Square;
import com.iqgames.sudoku.image.ImageBlob;
import com.iqgames.sudoku.image.ImageReader;
import com.iqgames.sudoku.image.RGBAValues;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by qili on 29/11/2016.
 */
public class ImageReaderTest implements Loggable {

    @Test
    public void testAColor() throws IOException {
        ImageReader image = new ImageReader("sudoku-1.jpg");
        System.out.println(image.getAValue());
        System.out.println(image.toString());

        final OutputStream inb = new FileOutputStream("bw.jpg");
        final ImageWriter wrt = ImageIO.getImageWritersByFormatName("jpg").next();
        final ImageInputStream imageInput = ImageIO.createImageOutputStream(inb);
        wrt.setOutput(imageInput);
        BufferedImage newImage = image.removeColor();


        Map<Integer, Integer> colors = new HashMap<>();
        for(int i=0;i<newImage.getWidth();i++)
            for(int j=0;j<newImage.getHeight();j++) {
                int color = newImage.getRGB(i,j);
                if(colors.containsKey(color)) colors.put(color, colors.get(color)+1);
                else colors.put(color, 1);
            }
        for(Integer i : colors.keySet())
            getLog().info("color - " + Integer.toHexString(i) + " : " + colors.get(i));


        ImageBlob imageBlob = new ImageBlob(newImage);
        Pair<ImageBlob.Blob, Double> res = imageBlob.getMaxBlob();
        getLog().info("The max blob ratio " + res.getRight());

        Square square = res.getLeft().getBoundary();
        square.draw(newImage, new RGBAValues(255, 0, 0));


        wrt.write(newImage);


        inb.close();
    }
}
