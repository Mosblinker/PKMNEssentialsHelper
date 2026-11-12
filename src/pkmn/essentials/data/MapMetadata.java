/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data;

/**
 *
 * @author Mosblinker
 */
public class MapMetadata extends AbstractPBSElement {
    
    public static final String[] PROPERTY_NAMES = {
        "Name",
        "MapPosition",
        "MapSize",
        "Outdoor",
        "Weather",
        "ShowArea",
        "Bicycle",
        "BicycleAlways",
        "HealingSpot",
        "DiveMap",
        "DarkMap",
        "SafariMap",
        "Dungeon",
        "SnapEdges",
        "BattleBack",
        "Environment",
        "WildBattleBGM",
        "WildVictoryBGM",
        "TrainerBattleBGM",
        "TrainerVictoryBGM",
        "WildCaptureME",
        "Flags"
    };

    @Override
    protected void setProperty(int propertyIndex, String value) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String[] getPropertyNames() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
