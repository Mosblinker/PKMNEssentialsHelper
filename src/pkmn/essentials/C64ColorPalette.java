/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkmn.essentials;

import java.awt.Color;

/**
 *
 * @author mstei
 */
public interface C64ColorPalette {
    public static final Color C64_BLACK         = new Color(  0,  0,  0);
    public static final Color C64_WHITE         = new Color(255,255,255);
    public static final Color C64_RED           = new Color(146, 74, 64);
    public static final Color C64_CYAN          = new Color(132,197,204);
    public static final Color C64_PURPLE        = new Color(147, 81,182);
    public static final Color C64_GREEN         = new Color(114,177, 75);
    public static final Color C64_BLUE          = new Color( 72, 58,170);
    public static final Color C64_YELLOW        = new Color(213,223,124);
    public static final Color C64_ORANGE        = new Color(153,105, 45);
    public static final Color C64_BROWN         = new Color(103, 82,  0);
    public static final Color C64_LIGHT_RED     = new Color(193,129,120);
    public static final Color C64_GREY_1        = new Color( 96, 96, 96);
    public static final Color C64_GREY_2        = new Color(138,138,138);
    public static final Color C64_LIGHT_GREEN   = new Color(179,236,145);
    public static final Color C64_LIGHT_BLUE    = new Color(134,122,222);
    public static final Color C64_GREY_3        = new Color(179,179,179);
    
    public static final Color[] C64_COLOR_PALETTE = {
        C64_BLACK,C64_WHITE,C64_RED,C64_CYAN,C64_PURPLE,C64_GREEN,C64_BLUE,
        C64_YELLOW,C64_ORANGE,C64_BROWN,C64_LIGHT_RED,C64_GREY_1,C64_GREY_2,
        C64_LIGHT_GREEN,C64_LIGHT_BLUE,C64_GREY_3
    };
    
    public static final Color[][] C64_COLOR_PALETTE_TEST = {
        C64_COLOR_PALETTE,
        {
            new Color(0x000000),
            new Color(0xFFFFFF),
            new Color(0x68372B),
            new Color(0x70a4b2),
            new Color(0x6f3d86),
            new Color(0x588d43),
            new Color(0x352879),
            new Color(0xb8c76f),
            new Color(0x6f4f25),
            new Color(0x433900),
            new Color(0x9a6759),
            new Color(0x444444),
            new Color(0x6c6c6c),
            new Color(0x9ad284),
            new Color(0x6c5eb5),
            new Color(0x959595)
        },
        {
            new Color(0x000000),
            new Color(0xFFFFFF),
            new Color(0x9f4e44),
            new Color(0x6abfc6),
            new Color(0xa057a3),
            new Color(0x5cab5e),
            new Color(0x50459b),
            new Color(0xc9d487),
            new Color(0xa1683c),
            new Color(0x6d5412),
            new Color(0xcb7e75),
            new Color(0x626262),
            new Color(0x898989),
            new Color(0x9ae29b),
            new Color(0x887ecb),
            new Color(0xadadad)
        },
    };
    
    public static final String[] C64_COLOR_NAMES = {
        "Black", "White", "Red", "Cyan", "Purple", "Green", "Blue", "Yellow",
        "Orange", "Brown", "Light Red", "Grey 1", "Grey 2", "Light Green", 
        "Light Blue", "Grey 3"
    };
    
    // Map Generating functions
}
