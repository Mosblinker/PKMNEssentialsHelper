/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkmn.essentials.data.pkmn;

import java.util.Objects;

/**
 *
 * @author Mosblinker
 */
public class PkmnLevelUp implements Comparable<PkmnLevelUp>{

    public int level;

    public Move move;

    public PkmnLevelUp(int level, Move move){
        this.level = level;
        this.move = move;
    }

    public PkmnLevelUp(PkmnLevelUp obj){
        this.level = obj.level;
        this.move = obj.move;
    }

    @Override
    public String toString(){
        return level + "," + move.getID();
    }
    @Override
    public boolean equals(Object obj){
        if (obj == this)
            return true;
        else if (obj instanceof PkmnLevelUp){
            PkmnLevelUp temp = (PkmnLevelUp) obj;
            return level == temp.level && move.equals(temp.move);
        }
        return false;
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.level;
        hash = 67 * hash + Objects.hashCode(this.move);
        return hash;
    }
    @Override
    public int compareTo(PkmnLevelUp o) {
        if (level == o.level){
            return move.compareTo(o.move);
        }
        return Integer.compare(level, o.level);
    }
}
