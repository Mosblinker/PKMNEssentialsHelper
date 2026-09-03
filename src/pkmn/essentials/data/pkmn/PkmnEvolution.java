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
public class PkmnEvolution implements Comparable<PkmnEvolution>{

    public Species species;

    public EvolutionMethod method;

    public Object parameter;

    public PkmnEvolution(Species species, EvolutionMethod method, Object parameter){
        this.species = species;
        this.method = method;
        this.parameter = parameter;
    }
    
    public PkmnEvolution(Species species, EvolutionMethod method){
        this(species,method,null);
    }
    
    public PkmnEvolution(PkmnEvolution evo){
        this(evo.species,evo.method,evo.parameter);
    }
    @Override
    public String toString(){
        return species.getID()+","+method.getValue()+","+Objects.toString(parameter, "");
    }
    @Override
    public boolean equals(Object obj){
        if (obj == this)
            return true;
        else if (obj instanceof PkmnEvolution){
            PkmnEvolution temp = (PkmnEvolution) obj;
            return species.equals(temp.species) && method.equals(temp.method) && 
                    Objects.equals(parameter, temp.parameter);
        }
        return false;
    }
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.species);
        hash = 53 * hash + Objects.hashCode(this.method);
        hash = 53 * hash + Objects.hashCode(this.parameter);
        return hash;
    }
    @Override
    public int compareTo(PkmnEvolution o) {
        return species.compareTo(o.species);
    }
}
