/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.swing.ImageIcon;

/**
 *
 * @author Mosblinker
 */
public final class PkmnHelperUtilities {
    /**
     * This is the directory that contains this program. This is initially null 
     * and is initialized the first time it is requested and successfully 
     * loaded.
     */
    private static String programDir = null;
    
    private PkmnHelperUtilities(){ }
    /**
     * 
     * @param color
     * @return 
     */
    public static boolean hasNoColor(Color color){
        return color == null || color.getAlpha() == 0;
    }
    
    public static boolean hasNoColor(Integer rgb){
        return rgb == null || (rgb & 0xFF000000) == 0;
    }
    /**
     * 
     * @param color
     * @param alpha
     * @return 
     */
    public static Color getTranslucentColor(Color color, double alpha){
            // If the alpha is greater than or equal to 1
        if (alpha >= 1.0)
            return color;
            // Get the RGB value of the color without the alpha component
        int rgb = color.getRGB() & 0x00FFFFFF;
            // If the alpha is greater than zero
        if (alpha > 0.0)
                // Multiply the color's alpha component by the alpha and shift 
                // it into the last 8 bits to use the result as the alpha 
                // component
            rgb |= ((int)Math.floor(color.getAlpha()*alpha)) << 24;
        return new Color(rgb, true);
    }
    /**
     * 
     * @param ratio
     * @param color1
     * @param color2
     * @return 
     */
    private static double blendColorValue(double ratio, int color1, int color2){
        double c = (color1 / 255.0);
        return c + ratio * ((color2 / 255.0) - c);
    }
    /**
     * 
     * @param color1
     * @param color2
     * @param t
     * @return 
     */
    public static Color blendColor(Color color1, Color color2, double t){
        if (Objects.equals(color1, color2) || t <= 0.0)
            return color1;
        if (t >= 1.0)
            return color2;
        double alpha = blendColorValue(t,color1.getAlpha(),color2.getAlpha());
        if (alpha <= 0.0)
            return getTranslucentColor(color1, alpha);
        double ratio = t * ((color2.getAlpha() / 255.0) / alpha);
        return new Color(
                (float)blendColorValue(ratio,color1.getRed(),color2.getRed()),
                (float)blendColorValue(ratio,color1.getGreen(),color2.getGreen()),
                (float)blendColorValue(ratio,color1.getBlue(),color2.getBlue()),
                (float)alpha);
    }
    /**
     * 
     * @param color1 The first source color
     * @param color2 The second source color
     * @param color3 The blended color
     * @return 
     */
    private static double getColorRatio(int color1, int color2, int color3){
        double c = (color1 / 255.0);
        return ((color3/255.0)-c) / ((color2 / 255.0) - c);
    }
    /**
     * 
     * @param color1 The first source color
     * @param color2 The second source color
     * @param color3 The blended color
     * @return 
     */
    public static double getColorRatio(Color color1, Color color2, Color color3){
        double r = getColorRatio(color1.getRed(),color2.getRed(),color3.getRed());
        double g = getColorRatio(color1.getGreen(),color2.getGreen(),color3.getGreen());
        double b = getColorRatio(color1.getBlue(),color2.getBlue(),color3.getBlue());
        double[] values = {r,g,b};
        int count = 0;
        double total = 0;
        for (double value : values){
            if (Double.isFinite(value)){
                total += value;
                count++;
            }
        }
        return total / count;
    }
    /**
     * 
     * @param image
     * @return 
     */
    public static BufferedImage getBufferedImage(Image image){
        Objects.requireNonNull(image);
        if (image instanceof BufferedImage)
            return (BufferedImage) image;
        BufferedImage img = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return img;
    }
    
    public static List<Color> getColors(BufferedImage image, List<Color> colors, boolean includeTransparent){
        if (image == null)
            return Collections.emptyList();
        if (colors == null)
            colors = new ArrayList<>();
        else
            colors.clear();
        Set<Integer> rgb = new LinkedHashSet<>();
        int[] rgbImg = new int[image.getWidth()];
        for (int y = 0; y < image.getHeight(); y++){
            rgbImg = image.getRGB(0, y, rgbImg.length, 1, rgbImg, 0, 1);
            for (int c : rgbImg)
                rgb.add(c);
        }
        for (Integer i : rgb){
            if (includeTransparent || !PkmnHelperUtilities.hasNoColor(i))
                colors.add(new Color(i,true));
        }
        return colors;
    }
    
    public static List<Color> getColors(BufferedImage image, List<Color> colors){
        return getColors(image,colors,true);
    }
    
    public static BufferedImage createCopy(Image image){
        BufferedImage img = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return img;
    }
    
    public static void changeColors(BufferedImage image, Map<Color,Color> colorExchangeMap){
        HashMap<Integer,Color> colorRGB = new HashMap<>();
        for (Color color : colorExchangeMap.keySet())
            colorRGB.put(color.getRGB(), color);
        int[] rgbImg = new int[image.getWidth()];
        for (int y = 0; y < image.getHeight(); y++){
            rgbImg = image.getRGB(0, y, rgbImg.length, 1, rgbImg, 0, 1);
            for (int x = 0; x < rgbImg.length; x++){
                Color color = colorRGB.get(rgbImg[x]);
                if (color != null){
                    rgbImg[x] = colorExchangeMap.get(color).getRGB();
                }
            }
            image.setRGB(0, y, rgbImg.length, 1, rgbImg, 0, 1);
        }
    }
    /**
     * This returns the working directory for this program.
     * @return The working directory for the program.
     */
    public static String getWorkingDirectory(){
        return System.getProperty("user.dir");
    }
    /**
     * This returns the directory of this program.
     * @return The directory containing this program.
     */
    public static String getProgramDirectory(){
            // If the program directory has been previously retrieved
        if (programDir != null)
            return programDir;
            // Get the location of this program, as a URL
        URL url = PkmnHelper.class.getProtectionDomain().getCodeSource().getLocation();
            // If a URL was found
        if (url != null)
            try {   // Get the parent of this program
                programDir = new File(url.toURI()).getParent();
                return programDir;
            } catch (URISyntaxException ex) {
                PkmnHelper.getLogger().log(java.util.logging.Level.WARNING,
                        "Failed to retrieve program directory.", ex);
            }
        return getWorkingDirectory();
    }
    
    public static List<BufferedImage> splitImage(BufferedImage image, int rows, int columns){
        List<BufferedImage> list = new ArrayList<>();
        int w = Math.floorDiv(image.getWidth(), columns);
        int h = Math.floorDiv(image.getHeight(), rows);
        for (int y = 0; y < rows; y++){
            for (int x = 0; x < columns; x++){
                list.add(image.getSubimage(x*w, y*h, w, h));
            }
        }
        return list;
    }
    
    public static List<ImageIcon> splitIcons(BufferedImage image, int rows, int columns){
        List<BufferedImage> images = splitImage(image,rows,columns);
        List<ImageIcon> icons = new ArrayList<>();
        for (BufferedImage temp : images)
            icons.add(new ImageIcon(temp));
        return icons;
    }
}
